#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/epoll.h>
#include <unistd.h>
#include <sys/types.h>
#include "communication.h"

#include "udp.h"

struct Sockets *init_udp(struct Args args)
{
    struct Sockets *sockets = malloc(sizeof(struct Sockets));
    bzero(sockets, sizeof(struct Sockets));

    int res;

    // INIT RECEIVING

    // Initializing socket for receiving
    printf("Creating in socket...\n");
    sockets->socket_in = socket(AF_INET, SOCK_DGRAM, 0);
    if(sockets->socket_in < 0) end_with_error_errno("Problem in IN socket");

    // set REUSEADDR
    printf("Setting socket properties...\n");
    res = setsockopt(sockets->socket_in, SOL_SOCKET, SO_REUSEADDR, &(int){ 1 }, sizeof(int));
    if (res != 0) end_with_error_errno("Problem setting reuseaddr - socket IN");

    // Bind socket to address
    printf("Binding socket...");
    struct sockaddr_in address;
    bzero(&address, sizeof(address));
    address.sin_family = AF_INET;
    address.sin_port = htons((uint16_t) args.this_port);
    address.sin_addr.s_addr = INADDR_ANY;
    res = bind(sockets->socket_in, (struct sockaddr *) &address, sizeof(address));
    if (res != 0) end_with_error_errno("Problem binding socket IN");


    // INIT SENDING

    // Initializing socket for sending
    printf("Creating out socket...\n");
    sockets->socket_out = socket(AF_INET, SOCK_DGRAM, 0);
    if(sockets->socket_listen < 0) end_with_error_errno("Problem in OUT socket");

    // fill address struct for sending
    bzero(&address, sizeof(address));
    address.sin_family = AF_INET;
    address.sin_port = htons((uint16_t) args.neighbour_port);
    address.sin_addr.s_addr = inet_addr(args.neighbour_ip);

    switch (args.has_token)
    {
        // Client has token at the start - creating new ring (using given ip and port)
        case 1:
            printf("Setting new ring...\n");

            printf("Connecting to self...\n");
            connect(sockets->socket_out, (struct sockaddr *) &address, sizeof(address));

            // Now since we have token we have to send it - so it can start moving
            send_udp(sockets, new_empty_token());

            break;

        // Client doesn't has a token - connecting to ring
        case 0:
            // Connect to neighbour
            printf("Connecting to neighbour...\n");
            res = connect(sockets->socket_out, (struct sockaddr *) &address, sizeof(address));
            if (res != 0) end_with_error_errno("Problem connecting to neighbour");

            // Now send him a message - ip port ip port - to enter the ring
            printf("Sending him a message...\n");
            struct ClientInfo client_info = new_client_info(args.this_ip, args.this_port, args.neighbour_ip, args.neighbour_port);
            char info_buf[BUF_SIZE];
            client_info_to_string(info_buf, client_info);

            struct Token token = new_token(NOT_A_REAL_TOKEN, ANY, ANY, info_buf);
            char token_buf[BUF_SIZE];
            token_to_string(token_buf, token);

            ssize_t size_sent = send(sockets->socket_out, token_buf, strlen(token_buf) + 1, 0);
            if (size_sent < 0) end_with_error_errno("Problem sending message to enter the ring");

            // Now we have to wait for token with confirmation
            printf("Waiting confirmation token...\n");
            socklen_t addr_len;
            ssize_t size_recv = recvfrom(sockets->socket_in, token_buf, BUF_SIZE, 0, (struct sockaddr *)&address, &addr_len);
            if (size_recv < 0) end_with_error_errno("Problem receiving confirmation token to enter the ring");

            // And now since we have token - just pass it forward
            printf("Sending token to new neighbour...\n");
            size_sent = send(sockets->socket_out, token_buf, strlen(token_buf) + 1, 0);
            if (size_sent < 0) end_with_error_errno("Problem sending message to enter the ring");

            break;
    }

    // Now create epoll
    printf("Setting epoll...\n");
    sockets->epoll_fd = epoll_create1(0);
    if (sockets->epoll_fd < 0) end_with_error_errno("Problem creating epoll");

    struct epoll_event event;
    event.events = EPOLLIN;
    event.data.fd = sockets->socket_in;
    res = epoll_ctl(sockets->epoll_fd, EPOLL_CTL_ADD, sockets->socket_in, &event);
    if (res < 0) end_with_error_errno("Problem adding socket in to epoll");

    printf("DONE! \n\n");
    return sockets;
}

void send_udp(struct Sockets *sockets, struct Token token)
{
    char msg[BUF_SIZE];
    token_to_string(msg, token);

    ssize_t size = send(sockets->socket_out, msg, strlen(msg) + 1, 0);
    if (size < 0) end_with_error_errno("Problem sending message");
}

void delete_socket_udp(struct Sockets *sockets, int socket_fd)
{
    int res;

    res = epoll_ctl(sockets->epoll_fd, EPOLL_CTL_DEL, socket_fd, NULL);
    if (res != 0) end_with_error_errno("Problem removing socket from epoll - delete_socket_tcp");

    res = shutdown(socket_fd, SHUT_RDWR);
    if (res != 0) end_with_error_errno("Problem shutting down socket - delete_socket_tcp");

    res = close(socket_fd);
    if (res != 0) end_with_error_errno("Problem closing socket - delete_socket_tcp");
}

struct Token receive_udp(struct Sockets *sockets)
{
    struct epoll_event event;
    int res = epoll_wait(sockets->epoll_fd, &event, 1, -1);
    if (res < 0) end_with_error_errno("Error waiting for epoll");

    char msg[BUF_SIZE];
    ssize_t size_recv = recvfrom(event.data.fd, msg, BUF_SIZE, 0, NULL, NULL);      // we don't need that address
    if (size_recv  < 0) print_error_errno("Problem receiving message");

    struct Token token = token_from_string(msg);

    return token;
}

void change_neighbour_udp(struct Sockets *sockets, const char *ip, int port)
{
    int res;

    res = shutdown(sockets->socket_out, SHUT_RDWR);
    if (res != 0) end_with_error_errno("Problem shutting down socket during changing neighbour");

    close(sockets->socket_out);
    if (res != 0) end_with_error_errno("Problem closing socket during changing neighbour");

    sockets->socket_out = socket(AF_INET, SOCK_DGRAM, 0);
    if (sockets->socket_out < 0) end_with_error_errno("Problem creating socket during changing neighbour");

    struct sockaddr_in address;
    address.sin_family = AF_INET;
    address.sin_port = htons((uint16_t) port);
    address.sin_addr.s_addr = inet_addr(ip);
    res = connect(sockets->socket_out, (struct sockaddr *) &address, sizeof(address));
    if (res != 0) end_with_error_errno("Problem connecting during changing neighbour");
}

void confirm_change_of_input_udp(struct Sockets *sockets, int useful_int)
{
    // nothing here I guess
}

void clean_udp(struct Sockets *sockets)
{
    if (sockets == NULL) return;

    int res;
    delete_socket_udp(sockets, sockets->socket_in);

    res = shutdown(sockets->socket_out, SHUT_RDWR);
    if (res != 0) end_with_error_errno("Problem shutting down socket out");

    res = close(sockets->socket_out);
    if (res != 0) end_with_error_errno("Problem closing socket out");

    res = close(sockets->epoll_fd);
    if (res != 0) end_with_error_errno("Problem closing socket epoll fd");

    free(sockets);
}
