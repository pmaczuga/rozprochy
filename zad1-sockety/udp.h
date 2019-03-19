#ifndef ZAD1_NEW_UCP_H
#define ZAD1_NEW_UCP_H

#include "token.h"


struct Sockets *init_udp(struct Args args);

void send_udp(struct Sockets *sockets, struct Token token);

struct Token receive_udp(struct Sockets *sockets);

void change_neighbour_udp(struct Sockets *sockets, const char *ip, int port);

void confirm_change_of_input_udp(struct Sockets *sockets, int useful_int);

void clean_udp(struct Sockets *sockets);


#endif //ZAD1_NEW_UCP_H
