#include <stdio.h>
#include <printf.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <errno.h>
#include <zconf.h>
#include "tcp.h"
#include "useful.h"
#include "info.h"

int socket_listen;
int socket_in;
int socket_out;
struct sockaddr *address_in;
struct sockaddr *address_out;

void init_TCP_for_receiving()
{
    int res;

    //create socket for receiving
    printf("Preparing socket for receiving... \n" );
    socket_listen = socket(AF_INET, SOCK_STREAM | SOCK_NONBLOCK, 0);
    if (socket_listen == -1) print_error_with_errno("Creating socket for receiving");

    // prepare struct sockaddr - address, port, protocol
    struct sockaddr_in *local_address_in = malloc(sizeof(struct sockaddr_in));
    memset(local_address_in, '\0', sizeof(*local_address_in));
    local_address_in->sin_family = AF_INET;
    local_address_in->sin_addr.s_addr = inet_addr(get_this_IP());
    local_address_in->sin_port = 0;
    // set this as global variable
    address_in = (struct sockaddr *) local_address_in;

    // bind socket to address
    printf("Binding... \n");
    res = bind(socket_listen, address_in, sizeof(*address_in));
    if (res != 0) print_error_with_errno("Binding socket");

    // get information about address - ip and port
    socklen_t len = sizeof(struct sockaddr);
    getsockname(socket_listen, address_in, &len);

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
    struct sockaddr_in *local_address_out = malloc(sizeof(struct sockaddr_in));
    memset(local_address_out, '\0', sizeof(*local_address_out));
    local_address_out->sin_family = AF_INET;
    local_address_out->sin_addr.s_addr = inet_addr(neighbour_IP);
    local_address_out->sin_port = htons((uint16_t)neighbour_port);
    // global variable
    address_out = (struct sockaddr *)local_address_out;

    // connect to address - non-blocking
    printf("Connecting... \n");
    set_socket_non_blocking(socket_out);
    res = connect(socket_out, address_out, sizeof(*address_out));
    if(res != 0 && errno != EINPROGRESS) print_error_with_errno("Connecting");

    // select - wait for socket_out - ready when connected
    // with timeout
    fd_set set;
    FD_ZERO(&set);
    FD_SET(socket_out, &set);
    struct timeval tv;
    tv.tv_sec = CONNECTION_TIMEOUT;
    tv.tv_usec = 0;
    res = select(socket_out + 1, NULL, &set, NULL, &tv);
    if(res == 0)
    {
        // timeout - can't connect, starting new ring - connect to self
        printf("TIMEOUT!\n");
        printf("Setting new ring... \n");

        close(socket_out);
        socket_out = socket(AF_INET, SOCK_STREAM, 0);
        if (socket_out == -1) print_error_with_errno("Creating socket for sending during setting new ring");

        res = connect(socket_out, address_in, sizeof(*address_in));
        if (res !=0 && errno != EINPROGRESS) print_error_with_errno("Connecting to self");
        socket_in = accept(socket_listen, NULL, NULL);
        if (socket_in < 0) print_error_with_errno("Accepting self");
        address_out = address_in;

    }
    else if (res > 0)
    {
        // everything went fine - sending message to enter ring
        char msg[SIZE];
        perror("Error after select > 0");
        sprintf(msg, "%s %d %s %d", get_IP_from_sockaddr(address_in), get_port_from_sockaddr(address_in), get_IP_from_sockaddr(address_out), get_port_from_sockaddr(address_out));
        send_message_TCP(msg);
    }
    else print_error_with_errno("In select after connecting");

//    set_socket_blocking(socket_out);

    printf("DONE!\n");
}

void init_TCP(const char *neighbour_IP, int neighbour_port)
{
    init_TCP_for_receiving();
    init_TCP_for_sending(neighbour_IP, neighbour_port);
}

void send_message_TCP(const char *msg)
{
    ssize_t size = send(socket_out, msg, strlen(msg + 1), 0);
    if (size < 0) perror("Problem sending message");
}

char *receive_message_TCP()
{
    char *buffer = malloc(SIZE);
    memset(buffer, '\0', SIZE);
    ssize_t size = recv(socket_in, buffer, SIZE, 0);
    if(size < 0) perror("Problem receiving message");
    return buffer;
}

char *check_connection_request_TCP()
{
    int socket_tmp;
    socket_tmp = accept(socket_listen, NULL, NULL);
    if (socket_tmp < 0 && errno != EWOULDBLOCK)
    {
        fprintf(stderr, "Problem accepting connection\n");
        return NULL;
    }
    printf("INSIDE check connection request!\n");
    char *buffer = malloc(SIZE);
    memset(buffer, '\0', SIZE);
    ssize_t size = recv(socket_tmp, buffer, SIZE, 0);
    if (size < 0)
    {
        fprintf(stderr, "Problem receiving new connection message\n");
        return NULL;
    }
    socket_in = socket_tmp;
    return buffer;
}

void change_neighbour_TCP(const char *neighbour_IP, int neighbour_port)
{
    printf("Changing neighbour...\n");

    int res;

    struct sockaddr_in *local_address_out = malloc(sizeof(struct sockaddr_in));
    memset(local_address_out, '\0', sizeof(*local_address_out));
    local_address_out->sin_family = AF_INET;
    local_address_out->sin_addr.s_addr = inet_addr(neighbour_IP);
    local_address_out->sin_port = htons((uint16_t)neighbour_port);
    free(address_out);
    address_out = (struct sockaddr *)local_address_out;

    res = connect(socket_out, address_out, sizeof(*address_out));
    if(res != 0) print_error_with_errno("Connecting");

    printf("DONE!\n");
}

int is_neighbour_equal_to(const char *ip, int port)
{
    if (strcmp(ip, get_IP_from_sockaddr(address_out)) != 0) return FALSE;
    if (port != get_port_from_sockaddr(address_out)) return FALSE;
    return TRUE;
}

void close_connection_TCP()
{

}
