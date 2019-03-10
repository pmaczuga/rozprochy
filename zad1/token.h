//
// Created by pawel on 3/7/19.
//

#ifndef ZAD1_TOKEN_H
#define ZAD1_TOKEN_H

#include "info.h"

enum MessageType
{
    NORMAL = 0,
    CHANGE_NEIGHBOUR = 1,
    CONFIRMATION = 2
};

struct Token
{
    int is_busy;
    enum MessageType type;
    char sender[SIZE];
    char receiver[SIZE];
    char message[SIZE];
};

struct Token* string_to_token(const char* string);

char* token_to_string(struct Token* token);

void print_token(struct Token* token);


#endif //ZAD1_TOKEN_H
