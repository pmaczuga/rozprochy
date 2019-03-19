#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/epoll.h>
#include <unistd.h>
#include "tcp.h"
#include "communication.h"

struct Sockets *init_tcp(struct Args args)
{
    struct Sockets *sockets = malloc(sizeof(struct Sockets));
    bzero(sockets, sizeof(struct Sockets));

    int res;

    // INIT LISTENING

    // Initializing socket for listening
    printf("Creating listen socket...\n");
    sockets->socket_listen = socket(AF_INET, SOCK_STREAM, 0);
    if(sockets->socket_listen < 0) end_with_error_errno("Problem in LISTEN socket");

    // fill address struct for listening
    struct sockaddr_in address;
    bzero(&address, sizeof(address));
    address.sin_family = AF_INET;
    address.sin_port = htons((uint16_t) args.this_port);
    address.sin_addr.s_addr = INADDR_ANY;
    printf("Binding listen socket...\n");
    res = bind(sockets->socket_listen, (struct sockaddr *) &address, sizeof(address));
    if (res != 0) end_with_error_errno("Binding LISTEN socket");

    // Listen
    printf("Listening...\n");
    res = listen(sockets->socket_listen, 5);
    if (res !=0) end_with_error_errno("Problem listening");

    // INIT SENDING

    // Initializing socket for sending
    printf("Creating out socket...\n");
    sockets->socket_out = socket(AF_INET, SOCK_STREAM, 0);
    if(sockets->socket_out < 0) end_with_error_errno("Problem in OUT socket");

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
            set_socket_non_blocking(sockets->socket_out);

            printf("Connecting to self...\n");
            connect(sockets->socket_out, (struct sockaddr *) &address, sizeof(address));
            set_socket_blocking(sockets->socket_out);

            printf("Accepting self...\n");
            sockets->socket_in = accept(sockets->socket_listen, NULL, NULL);
            if(sockets->socket_in < 0) end_with_error_errno("Problem accepting self");

            // Now since we have token we have to send it - so it can start moving
            send_tcp(sockets, new_empty_token());

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

            // Now wait for acceptance
            printf("Accepting connection...\n");
            sockets->socket_in = accept(sockets->socket_listen, NULL, NULL);
            if (sockets->socket_in < 0) end_with_error_errno("Problem accepting connection to ring");

            // Now we have to wait for token with confirmation
            printf("Waiting confirmation token...\n");
            ssize_t size_recv = recv(sockets->socket_in, token_buf, BUF_SIZE, 0);
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
    event.data.fd = sockets->socket_listen;
    res = epoll_ctl(sockets->epoll_fd, EPOLL_CTL_ADD, sockets->socket_listen, &event);
    if (res < 0) end_with_error_errno("Problem adding socket listen to epoll");

    bzero(&event, sizeof(event));
    event.events = EPOLLIN;
    event.data.fd = sockets->socket_in;
    res = epoll_ctl(sockets->epoll_fd, EPOLL_CTL_ADD, sockets->socket_in, &event);
    if (res < 0) end_with_error_errno("Problem adding socket in to epoll");

    printf("DONE! \n\n");
    return sockets;
}

void send_tcp(struct Sockets *sockets, struct Token token)
{
    char msg[BUF_SIZE];
    token_to_string(msg, token);

    ssize_t size = send(sockets->socket_out, msg, strlen(msg) + 1, 0);
    if (size < 0) end_with_error_errno("Problem sending message");
}

void delete_socket_tcp(struct Sockets *sockets, int socket_fd)
{
    int res;

    res = epoll_ctl(sockets->epoll_fd, EPOLL_CTL_DEL, socket_fd, NULL);
    if (res != 0) end_with_error_errno("Problem removing socket from epoll - delete_socket_tcp");

    res = shutdown(socket_fd, SHUT_RDWR);
    if (res != 0) end_with_error_errno("Problem shutting down socket - delete_socket_tcp");

    res = close(socket_fd);
    if (res != 0) end_with_error_errno("Problem closing socket - delete_socket_tcp");
}

struct Token receive_tcp(struct Sockets *sockets)
{
    struct epoll_event event;
    int res = epoll_wait(sockets->epoll_fd, &event, 1, -1);
    if (res < 0) end_with_error_errno("Error waiting for epoll");

    if (event.data.fd == sockets->socket_listen)
    {
        // Event on listen socket - accepting new connection
        int new_socket = accept(sockets->socket_listen, NULL, NULL);
        if (new_socket < 0) print_error_errno("Problem accepting new connection");

        // Now add new socket to epoll - we are expecting message from there
        struct epoll_event event;
        event.events = EPOLLIN;
        event.data.fd = new_socket;
        res = epoll_ctl(sockets->epoll_fd, EPOLL_CTL_ADD, new_socket, &event);
        if (res != 0) print_error_errno("Problem adding new socket to epoll");

        // I have to return token - thus recursion
        return receive_tcp(sockets);
    }
    else
    {
        char msg[BUF_SIZE];
        ssize_t size_recv = recv(event.data.fd, msg, BUF_SIZE, 0);
        if (size_recv  < 0) print_error_errno("Problem receiving message");
        if (size_recv == 0)
        {
            delete_socket_tcp(sockets, event.data.fd);
            return receive_tcp(sockets); // Socket closed - EOF - we need to return token - thus recursion
        }

        struct Token token = token_from_string(msg);

        // if it's not from normal receiver - add it's file descriptor to token - in order to change sockets correctly
        if (event.data.fd != sockets->socket_in) token.useful_int = event.data.fd;

        return token;
    }
}

void change_neighbour_tcp(struct Sockets *sockets, const char *ip, int port)
{
    int res;

    res = shutdown(sockets->socket_out, SHUT_RDWR);
    if (res != 0) end_with_error_errno("Problem shutting down socket during changing neighbour");

    close(sockets->socket_out);
    if (res != 0) end_with_error_errno("Problem closing socket during changing neighbour");

    sockets->socket_out = socket(AF_INET, SOCK_STREAM, 0);
    if (sockets->socket_out < 0) end_with_error_errno("Problem creating socket during changing neighbour");

    struct sockaddr_in address;
    address.sin_family = AF_INET;
    address.sin_port = htons((uint16_t) port);
    address.sin_addr.s_addr = inet_addr(ip);
    res = connect(sockets->socket_out, (struct sockaddr *) &address, sizeof(address));
    if (res != 0) end_with_error_errno("Problem connecting during changing neighbour");
}

void confirm_change_of_input_tcp(struct Sockets *sockets, int useful_int)
{
//    delete_socket_tcp(sockets, sockets->socket_in);
    sockets->socket_in = useful_int;
}

void clean_tcp(struct Sockets *sockets)
{
    if (sockets == NULL) return;

    int res;
    delete_socket_tcp(sockets, sockets->socket_in);
    delete_socket_tcp(sockets, sockets->socket_listen);

    res = shutdown(sockets->socket_out, SHUT_RDWR);
    if (res != 0) end_with_error_errno("Problem shutting down socket out");

    res = close(sockets->socket_out);
    if (res != 0) end_with_error_errno("Problem closing socket out");

    res = close(sockets->epoll_fd);
    if (res != 0) end_with_error_errno("Problem closing socket epoll fd");

    free(sockets);
}
