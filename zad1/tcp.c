#include <stdio.h>
#include <printf.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <errno.h>
#include <zconf.h>
#include <stdint.h>
#include "tcp.h"
#include "useful.h"
#include "info.h"

int socket_listen;
int socket_tmp;
int socket_in;
int socket_out;
struct sockaddr_in *address_in;
struct sockaddr_in *address_out;

void init_TCP_for_receiving()
{
    int res;

    //create socket for receiving
    printf("Preparing socket for receiving... \n" );
    socket_listen = socket(AF_INET, SOCK_STREAM | SOCK_NONBLOCK, 0);
    if (socket_listen == -1) print_error_with_errno("Creating socket for receiving");

    // prepare struct sockaddr - address, port, protocol
    struct sockaddr_in *address_in = malloc(sizeof(struct sockaddr_in));
    memset(address_in, '\0', sizeof(*address_in));
    address_in->sin_family = AF_INET;
    address_in->sin_addr.s_addr = inet_addr(get_this_IP());
    address_in->sin_port = 0;

    // bind socket to address
    printf("Binding... \n");
    res = bind(socket_listen, (struct sockaddr *)address_in, sizeof(*address_in));
    if (res != 0) print_error_with_errno("Binding socket");

    // get information about address - ip and port
    socklen_t len = sizeof(struct sockaddr);
    getsockname(socket_listen, (struct sockaddr *) address_in, &len);

    // listen to socket
    printf("Listening... \n");
    res = listen(socket_listen, 3);
    if (res != 0) print_error_with_errno("Listening");
}

int init_TCP_for_sending(const char *neighbour_IP, int neighbour_port)
{
    int res;

    // create socket for sending
    printf("Preparing socket for sending... \n");
    socket_out = socket(AF_INET, SOCK_STREAM, 0);
    if (socket_out == -1) print_error_with_errno("Creating socket for sending");

    // struct sockaddr
    struct sockaddr_in *address_out = malloc(sizeof(struct sockaddr_in));
    memset(address_out, '\0', sizeof(*address_out));
    address_out->sin_family = AF_INET;
    address_out->sin_addr.s_addr = inet_addr(neighbour_IP);
    address_out->sin_port = htons((uint16_t) neighbour_port);


    printf("Connecting... \n");
    char mark;
    printf("Set new ring? [y/n]: ");
    scanf("%c", &mark);

    if (mark == 'n')
    {
        res = connect(socket_out, (struct sockaddr *)address_out, sizeof(*address_out));
        if(res != 0) print_error_with_errno("Connecting");

        // everything went fine - sending message to enter ring
        char msg[SIZE];
        sprintf(msg, "%s %d %s %d", get_IP_from_sockaddr(address_in), get_port_from_sockaddr(address_in), get_IP_from_sockaddr(address_out), get_port_from_sockaddr(address_out));
        send_message_TCP(msg);

        set_socket_blocking(socket_listen);
        socket_in = accept(socket_listen, NULL, NULL);
        if (socket_in < 0) print_error_with_errno("Accepting");
        set_socket_non_blocking(socket_listen);
    }
    else
    {
        printf("Setting new ring... \n");

        close(socket_out);
        socket_out = socket(AF_INET, SOCK_STREAM, 0);
        if (socket_out == -1) print_error_with_errno("Creating socket for sending during setting new ring");

        res = connect(socket_out, (struct sockaddr *)address_in, sizeof(*address_in));
        if (res !=0 && errno != EINPROGRESS) print_error_with_errno("Connecting to self");
        socket_in = accept(socket_listen, NULL, NULL);
        if (socket_in < 0) print_error_with_errno("Accepting self");
        address_out = address_in;
    }

    set_socket_non_blocking(socket_out);

    printf("DONE!\n");
}

void init_TCP(const char *neighbour_IP, int neighbour_port)
{
    init_TCP_for_receiving();
    init_TCP_for_sending(neighbour_IP, neighbour_port);
}

void send_message_TCP(const char *msg)
{
    ssize_t size = send(socket_out, msg, strlen(msg) + 1, 0);
    if (size < 0) perror("Problem sending message");
}

char *receive_message_TCP()
{
    char *buffer = malloc(SIZE * sizeof(char));
    memset(buffer, '\0', SIZE * sizeof(char));
    ssize_t size = recv(socket_in, buffer, SIZE * sizeof(char), 0);
    if(size < 0) perror("Problem receiving message");
    printf("RECEIVED: %s\n", buffer);
    return buffer;
}

char *check_connection_request_TCP()
{
    socket_tmp = accept(socket_listen, NULL, NULL);
    if (socket_tmp < 0)
    {
        if (errno != EWOULDBLOCK)
        {
            perror("Problem accepting connection\n");
        }
        return NULL;
    }
    char *buffer = malloc(SIZE * sizeof(char));
    memset(buffer, '\0', SIZE * sizeof(char));
    ssize_t size = recv(socket_tmp, buffer, SIZE * sizeof(char), 0);
    if (size < 0)
    {
        perror("Problem receiving new connection message\n");
        return NULL;
    }
    return buffer;
}

void change_neighbour_TCP(const char *neighbour_IP, int neighbour_port)
{
    printf("Changing neighbour...\n");

    int res;

    struct sockaddr_in *local_address_out = (struct sockaddr_in *) address_out;
    memset(local_address_out, '\0', sizeof(*local_address_out));
    local_address_out->sin_family = AF_INET;
    local_address_out->sin_addr.s_addr = inet_addr(neighbour_IP);
    local_address_out->sin_port = htons((uint16_t)neighbour_port);

    shutdown(socket_out, SHUT_RDWR);
    close(socket_out);
    socket_out = socket(AF_INET, SOCK_STREAM, 0);
    if (socket_out != 0) print_error_with_errno("Changing neighbour (socket)");



    res = connect(socket_out, (struct sockaddr *)address_out, sizeof(*address_out));
    if(res != 0) print_error_with_errno("Connecting");

    set_socket_non_blocking(socket_out);

    printf("DONE!\n");
}

int is_neighbour_equal_to_TCP(const char *ip, int port)
{
    if (strcmp(ip, get_IP_from_sockaddr(address_out)) != 0) return FALSE;
    if (port != get_port_from_sockaddr(address_out)) return FALSE;
    return TRUE;
}

int is_myself_equal_to_TCP(const char *ip, int port)
{
    if (strcmp(ip, get_IP_from_sockaddr(address_in)) != 0) return FALSE;
    if (port != get_port_from_sockaddr(address_in)) return FALSE;
    return TRUE;
}

void confirm_in_connection_change_TCP()
{
    socket_in = socket_tmp;
}

void close_connection_TCP()
{
    // TODO
}
