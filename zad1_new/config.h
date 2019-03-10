#include <stdint.h>

#ifndef ZAD1_NEW_CONFIG_H
#define ZAD1_NEW_CONFIG_H


#define BUF_SIZE 1024

#define TRUE 1
#define FALSE 0

enum Protocol
{
    TCP = 0,
    UDP
};

struct Args
{
    char id[BUF_SIZE];
    char this_ip[BUF_SIZE];
    char neighbour_ip[BUF_SIZE];
    int this_port;
    int neighbour_port;
    enum Protocol protocol;
    int has_token;
};


struct Args init_args(int argc, char *argv[]);

void print_args(struct Args args);

void clean_up();

void set_socket_blocking(int socket_fd);

void set_socket_non_blocking(int socket_fd);

void end_with_error(const char *msg);

void end_with_error_errno(const char *msg);

void print_error(const char *msg);

void print_error_errno(const char *msg);


#endif //ZAD1_NEW_CONFIG_H
