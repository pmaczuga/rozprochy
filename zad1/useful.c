#include <errno.h>
#include <stdlib.h>
#include <zconf.h>
#include <stdio.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <fcntl.h>
#include <string.h>

#include "useful.h"
#include "info.h"

void print_error(const char *msg)
{
    fprintf(stderr ,"ERROR: %s", msg);
    exit(EXIT_FAILURE);
}

void print_error_with_errno(const char *msg)
{
    char *new_msg = malloc(sizeof(*msg) + 8);
    memset(new_msg, '\0', sizeof(*new_msg));
    sprintf(new_msg, "ERROR: %s", msg);
    perror(new_msg);
    exit(EXIT_FAILURE);
}

char *get_IP_from_ip_colon_port(const char *str)
{
    if (str == NULL) return NULL;

    int size = 30;
    char* res = malloc((size_t) size);
    for (int i = 0; i < size - 1; i++)
    {
        if (str[i] == '\0') return NULL;
        if (str[i] == ':')
        {
            res[i + 1] = '\0';
            return res;
        }
        res[i] = str[i];
    }
    return NULL;
}

int get_port_from_ip_colon_port(const char *str)
{
    int max_port_size = 10;
    char *res = malloc((size_t) max_port_size);
    int i = 0;
    while(str[i] != ':')
    {
        if (str[i] == '\0') return -1;
        i++;
    }
    i++;
    int j = 0;
    while(str[i] != '\0')
    {
        if (str[i] < '0' || str[i] > '9') return -1;
        res[j] = str[i];
        j++;
        i++;
    }
    return atoi(res);
}

char *get_this_IP()
{
    char host_buffer[SIZE];
    struct hostent *host_entry;
    int res;

    res = gethostname(host_buffer, sizeof(host_buffer));
    if (res != 0) print_error_with_errno("Getting host name");

    host_entry = gethostbyname(host_buffer);
    if(host_entry == NULL) print_error_with_errno("Getting host BY name");

    return inet_ntoa(*((struct in_addr*)host_entry->h_addr_list[0]));
}

int set_socket_blocking(int socket_fd)
{
    int flags = fcntl(socket_fd, F_GETFL);
    flags = flags & (~O_NONBLOCK);
    fcntl(socket_fd, F_SETFL, flags);
}

int set_socket_non_blocking(int socket_fd)
{
    int flags = fcntl(socket_fd, F_GETFL);
    flags = flags | O_NONBLOCK;
    fcntl(socket_fd, F_SETFL, flags);
}

char *get_IP_from_sockaddr(struct sockaddr_in* address)
{
    return inet_ntoa(address->sin_addr);
}

int get_port_from_sockaddr(struct sockaddr_in* address)
{
    return ntohs(address->sin_port);
}
