#include "token.h"

#ifndef ZAD1_NEW_QUEUE_H
#define ZAD1_NEW_QUEUE_H

#define QUEUE_SIZE 1024

struct Queue
{
    struct Token elems[QUEUE_SIZE];
    int first;
    int last;
};

struct Queue *new_queue();


int is_empty_queue(struct Queue *queue);

int is_full_queue(struct Queue *queue);

void put_queue(struct Queue *queue ,struct Token token);

struct Token get_queue(struct Queue *queue);

void delete_queue(struct Queue *queue);

#endif //ZAD1_NEW_QUEUE_H
