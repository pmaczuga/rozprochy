#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "info.h"

#include "token.h"

struct Token* string_to_token(const char* string)
{
    struct Token *token = malloc(sizeof(struct Token));
    memset(token, '\0', sizeof(*token));
    sscanf(string, "%d %d %s %s %[^\n]", &token->is_busy, &token->type, token->sender, token->receiver, token->message);
    return token;
}

char* token_to_string(struct Token* token)
{
    char *string = malloc(SIZE * sizeof(char));
    memset(string, '\0', SIZE * sizeof(char));
    sprintf(string, "%d %d %s %s %s", token->is_busy, token->type, token->sender, token->receiver, token->message);
    return string;

}

void print_token(struct Token* token)
{
    printf("TOKEN   ");
    if (token->is_busy == 0) printf("free ");
    else printf("busy ");
    if(token->type == NORMAL) printf("NORMAL ");
    else if(token->type == CHANGE_NEIGHBOUR) printf("SYSTEM ");
    printf("-sender: %s ", token->sender);
    printf("-receiver: %s ", token->receiver);
    printf("-message: \"%s\"\n", token->message);
}
