#ifndef ZAD1_NEW_TOKEN_H
#define ZAD1_NEW_TOKEN_H

#include "config.h"

#define ANY "ANY"
#define TTL 2

enum MessageType
{
    EMPTY = 0,
    MESSAGE,
    CHANGE_NEIGHBOUR,
    CONFIRM_CHANGE,
    NOT_A_REAL_TOKEN
};

struct Token
{
    enum MessageType type;
    char sender[BUF_SIZE];
    char receiver[BUF_SIZE];
    int useful_int;
    int ttl;
    char message[BUF_SIZE];
};

struct ClientInfo
{
    char ip[BUF_SIZE];
    int port;
    char neighbour_ip[BUF_SIZE];
    int neighbour_port;
};

struct Token new_empty_token();

struct Token new_token(enum MessageType type, const char* sender, const char *receiver, const char *message);

void copy_token(struct Token *dest, struct Token *source);

void clear_token(struct Token *token);

struct Token token_from_string(const char *string);

void token_to_string(char *dest, struct Token token);

void print_token(struct Token token);

struct ClientInfo new_client_info(const char*ip, int port, const char *neighbour_ip, int neighbour_port);

struct ClientInfo client_info_from_string(const char *string);

void client_info_to_string(char *dest, struct ClientInfo client_info);


#endif //ZAD1_NEW_TOKEN_H
