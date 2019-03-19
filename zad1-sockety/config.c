#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <zconf.h>
#include <fcntl.h>
#include "config.h"

char *get_this_IP()
{
    char host_buffer[BUF_SIZE];
    struct hostent *host_entry;
    int res;

    res = gethostname(host_buffer, sizeof(host_buffer));
    if (res != 0) end_with_error_errno("Getting host name");

    host_entry = gethostbyname(host_buffer);
    if(host_entry == NULL) end_with_error_errno("Getting host BY name");

    return inet_ntoa(*((struct in_addr*)host_entry->h_addr_list[0]));
}

struct Args init_args(int argc, char *argv[])
{
    /*
     * Arguments:
     * ID       - client id
     * port     - port where client listens
     * ip:port  - ip and port of neighbour
     * token    - [1/0] does user has token at the beginning
     * protocol - [TCP/UDP]
     */

    struct Args args;
    bzero(&args, sizeof(args));

    if (argc < 6) end_with_error("Not enough arguments, 5 required");

    // FIRST
    strcpy(args.id, argv[1]);

    // SECOND
    args.this_port = atoi(argv[2]);

    // THIRD
    int i = 0;
    while(argv[3][i] != ':')
    {
        if (argv[3][i] == '\0') end_with_error("Wrong ip:port argument");
        args.neighbour_ip[i] = argv[3][i];
        i++;
    }
    args.neighbour_ip[i] = '\0';

    char port_as_string[BUF_SIZE];
    int j = 0;
    i++;
    while(argv[3][i] != '\0')
    {
        port_as_string[j] = argv[3][i];
        i++;
        j++;
    }
    port_as_string[j] = '\0';
    args.neighbour_port = atoi(port_as_string);

    // FOURTH
    int token = atoi(argv[4]);
    if (token == 1) args.has_token = TRUE;
    else if (token == 0) args.has_token = FALSE;
    else end_with_error("Fourth argument has to be 0 or 1");

    // FIFTH
    if (strcmp(argv[5], "TCP") == 0) args.protocol = TCP;
    else if (strcmp(argv[5], "UDP") == 0) args.protocol = UDP;
    else end_with_error("Last argument has to be TCP or UDP");

    // and IP
    char *ip = get_this_IP();
    strcpy(args.this_ip,ip);

    return args;
}

void print_args(struct Args args)
{
    printf("\n---------ARGUMENTS: ----------\n");
    printf("ID:                %s\n", args.id);
    printf("Address:           %s:%d\n", args.this_ip, args.this_port);
    printf("Neighbour address: %s:%d\n", args.neighbour_ip, args.neighbour_port);
    printf("Has token:         %d\n", args.has_token);
    printf("Protocol:          %d\n", args.protocol);
    printf("--------------------------------\n\n");
}

void end_with_error(const char *msg)
{
    print_error(msg);
    exit(EXIT_FAILURE);
}

void set_socket_blocking(int socket_fd)
{
    int flags = fcntl(socket_fd, F_GETFL);
    flags = flags & (~O_NONBLOCK);
    fcntl(socket_fd, F_SETFL, flags);
}

void set_socket_non_blocking(int socket_fd)
{
    int flags = fcntl(socket_fd, F_GETFL);
    flags = flags | O_NONBLOCK;
    fcntl(socket_fd, F_SETFL, flags);
}

void end_with_error_errno(const char *msg)
{
    print_error_errno(msg);
    exit(EXIT_FAILURE);
}

void print_error(const char *msg)
{
    fprintf(stderr, "\nERROR: %s\n", msg);
}

void print_error_errno(const char *msg)
{
    char new_msg[BUF_SIZE];
    sprintf(new_msg, "\nERROR: %s\n", msg);
    perror(new_msg);
}
