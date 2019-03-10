#include "stdio.h"
#include <stdlib.h>
#include <string.h>

#include "Queue.h"
#include "token.h"


struct Queue *new_queue()
{
    struct Queue *queue = malloc(sizeof(struct Queue));
    bzero(queue, sizeof(struct Queue));
    queue->first = 0;
    queue->last = 0;
}

int is_empty_queue(struct Queue *queue)
{
    if (queue->first == queue->last) return 1;
    else return  0;
}

int is_full_queue(struct Queue *queue)
{
    if ((queue->last + 1) % QUEUE_SIZE == queue->first) return 1;
    else return 0;
}

void put_queue(struct Queue *queue ,struct Token token)
{
    queue->elems[queue->last] = token;
    queue->last = (queue->last + 1) % QUEUE_SIZE;
}

struct Token get_queue(struct Queue *queue)
{
    struct Token token = queue->elems[queue->first];
    queue->first = (queue->first + 1) % QUEUE_SIZE;

    return token;
}

void delete_queue(struct Queue *queue)
{
    if (queue != NULL) free(queue);
}