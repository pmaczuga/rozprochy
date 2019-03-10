#ifndef ZAD1_NEW_TCP_H
#define ZAD1_NEW_TCP_H

#include "token.h"


struct Sockets *init_tcp(struct Args args);

void send_tcp(struct Sockets *sockets, struct Token token);

struct Token receive_tcp(struct Sockets *sockets);

void change_neighbour_tcp(struct Sockets *sockets, const char *ip, int port);

void confirm_change_of_input_tcp(struct Sockets *sockets, int useful_int);

void clean_tcp(struct Sockets *sockets);


#endif //ZAD1_NEW_TCP_H
