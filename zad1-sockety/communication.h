#include "config.h"

#ifndef ZAD1_NEW_COMMUNICATION_H
#define ZAD1_NEW_COMMUNICATION_H

#define MULTICAST_IP "224.0.0.1"
#define MULTICAST_PORT 1997

struct Sockets
{
    int socket_in;
    int socket_out;
    int socket_listen;
    int epoll_fd;
};

void init_communication(struct Args args);

void send_message(const char *receiver, const char *message);

void clean_comunication();

#endif //ZAD1_NEW_COMMUNICATION_H
