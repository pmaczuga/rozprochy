//
// Created by pawel on 3/7/19.
//

#include <pthread.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "input_reading.h"

pthread_mutex_t memory_acces_mutex = PTHREAD_MUTEX_INITIALIZER;

char *buffer = NULL;

char *get_input()
{
    char *return_buffer = malloc(SIZE * sizeof(char));
    memset(return_buffer, '\0', SIZE * sizeof(char));

    pthread_mutex_lock(&memory_acces_mutex);
    if (buffer != NULL)
    {
        strcpy(return_buffer, buffer);
        free(buffer);
        buffer = NULL;
    }
    pthread_mutex_unlock(&memory_acces_mutex);

    return return_buffer;
}

void *read_input( void *ptr)
{
    char local_buffer[SIZE];
    while(1)
    {
        scanf("%[^.\n]", local_buffer);

        pthread_mutex_lock(&memory_acces_mutex);
        strcpy(buffer, local_buffer);
        pthread_mutex_unlock(&memory_acces_mutex);
    }
}
