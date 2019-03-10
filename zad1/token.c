#include <string.h>
#include <stdio.h>
#include "token.h"

struct Token new_empty_token()
{
    struct Token token;
    clear_token(&token);

    return token;
}

struct Token new_token(enum MessageType type, const char* sender, const char *receiver, const char *message)
{
    struct Token token;
    bzero(&token, sizeof(token));
    token.type = type;
    strcpy(token.sender, sender);
    strcpy(token.receiver, receiver);
    token.useful_int = 0;
    token.ttl = TTL;
    strcpy(token.message, message);

    return token;
}

struct Token token_from_string(const char *string)
{
    struct Token token;
    bzero(&token, sizeof(token));
    sscanf(string, "%d %s %s %d %d %[^\n]", &token.type, token.sender, token.receiver, &token.useful_int, &token.ttl, token.message);
    return token;
}

void token_to_string(char *dest, struct Token token)
{
    sprintf(dest, "%d %s %s %d %d %s", token.type, token.sender, token.receiver, token.useful_int, token.ttl, token.message);
}

void copy_token(struct Token *dest, struct Token *source)
{
    dest->type = source->type;
    strcpy(dest->sender, source->sender);
    strcpy(dest->receiver, source->receiver);
    strcpy(dest->message, source->message);
    dest->useful_int = source->useful_int;
    dest->ttl = source->ttl;
}

void clear_token(struct Token *token)
{
    bzero(token, sizeof(*token));
    token->type = EMPTY;
    strcpy(token->sender, ANY);
    strcpy(token->receiver, ANY);
    token->useful_int = 0;
    token->ttl = TTL;
}

void print_token(struct Token token)
{
    printf("\n-------TOKEN------------\n");
    printf("Type:      %d\n", token.type);
    printf("Sender:    %s\n", token.sender);
    printf("Receiver   %s\n", token.receiver);
    printf("Useful int %d\n", token.useful_int);
    printf("TTL:       %d\n", token.ttl);
    printf("Message:   \"%s\"\n", token.message);
    printf("--------------------------\n\n");
}

struct ClientInfo new_client_info(const char*ip, int port, const char *neighbour_ip, int neighbour_port)
{
    struct ClientInfo client_info;
    bzero(&client_info, sizeof(client_info));
    strcpy(client_info.ip, ip);
    client_info.port = port;
    strcpy(client_info.neighbour_ip, neighbour_ip);
    client_info.neighbour_port = neighbour_port;

    return client_info;
}

struct ClientInfo client_info_from_string(const char *string)
{
    struct ClientInfo client_info;
    bzero(&client_info, sizeof(client_info));
    sscanf(string, "%s %d %s %d", client_info.ip, &client_info.port, client_info.neighbour_ip, &client_info.neighbour_port);
    return client_info;
}

void client_info_to_string(char *dest, struct ClientInfo client_info)
{
    sprintf(dest, "%s %d %s %d", client_info.ip, client_info.port, client_info.neighbour_ip, client_info.neighbour_port);
}
