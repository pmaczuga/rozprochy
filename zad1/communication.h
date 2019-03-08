#ifndef ZAD1_COMMUNICATION_H
#define ZAD1_COMMUNICATION_H

#include "info.h"
#include "useful.h"
#include "token.h"

void init_communication(enum Protocol protocol_type, const char *my_id, int has_token, const char *ip, int port);

int send_message(const char *to, const char *msg);

char *receive_message();

#endif //ZAD1_COMMUNICATION_H
