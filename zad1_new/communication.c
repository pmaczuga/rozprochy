#include <pthread.h>
#include <stdio.h>
#include <fcntl.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include "communication.h"
#include "config.h"
#include "Queue.h"
#include "tcp.h"

struct Queue *queue = NULL;
struct Args arguments;
struct Sockets *sockets;

pthread_mutex_t memory_mutex = PTHREAD_MUTEX_INITIALIZER;

void send_message(const char *receiver, const char *message)
{
    struct Token token = new_token(MESSAGE, arguments.id, receiver, message);
    pthread_mutex_lock(&memory_mutex);
    if (is_full_queue(queue) == 0) put_queue(queue, token);
    else print_error("Full message queue");
    pthread_mutex_unlock(&memory_mutex);
}


int is_it_me(const char *ip, int port)
{
    if (strcmp(ip, arguments.this_ip) != 0) return FALSE;
    if (port != arguments.this_port) return FALSE;
    return TRUE;
}

void handle_empty(struct Token *token)
{
    struct Token waiting_token;
    pthread_mutex_lock(&memory_mutex);
    if (is_empty_queue(queue) == 0)
    {
        waiting_token = get_queue(queue);
        copy_token(token, &waiting_token);
    }
    pthread_mutex_unlock(&memory_mutex);
}

void handle_message(struct Token *token)
{
    if (strcmp(token->receiver, arguments.id) == 0)
    {
        print_token(*token);
        clear_token(token);
    }
}

void handle_change_neighbour(struct Token *token)
{
    struct ClientInfo client_info = client_info_from_string(token->message);
    // If I am neighbour of new client neighbour - then I must pin to new client
    if (is_it_me(client_info.neighbour_ip, client_info.neighbour_port) == TRUE)
    {
        switch (arguments.protocol)
        {
            case TCP: change_neighbour_tcp(sockets, client_info.ip, client_info.port); break;
            case UDP: break; // TODO
        }
        // And change neighbour in arguments struct - just in case
        strcpy(arguments.neighbour_ip, client_info.ip);
        arguments.neighbour_port = client_info.port;

        // Now send confirmation token to new neighbour
        // Message and more importantly useful_int can't be changed
        token->type = CONFIRM_CHANGE;
        strcpy(token->sender, arguments.id);
        strcpy(token->receiver, ANY);
    }
    // If not just send it further
}

void handle_confirm_change(struct Token *token)
{
    // There is socket responsible for new client in useful_int
    confirm_change_of_input(sockets, token->useful_int);
    clear_token(token);
}

void handle_not_a_real_token(struct Token *token)
{
    // It's not a real token - it wan't be sent anywhere right now
    // It will have to wait for it's turn in queue
    struct Token token_to_queue;
    copy_token(&token_to_queue, token);
    token_to_queue.type = CHANGE_NEIGHBOUR;

    pthread_mutex_lock(&memory_mutex);
    if (is_full_queue(queue) == 0) put_queue(queue, token_to_queue);
    else print_error("Can't put change neighbour token to queue");
    pthread_mutex_unlock(&memory_mutex);
}

void *start_communication(void *ptr)
{
    while(1)
    {
        struct Token token;
        switch (arguments.protocol)
        {
            case TCP: token = receive_tcp(sockets); break;
            case UDP: break; // TODO
        }

        // -------------FOR-TESTING---------------
        print_token(token);
        // ----------------END--------------------
        sleep(2);

        // Depending on what type is that token we can change its content and send it forward
        switch (token.type)
        {
            case EMPTY: handle_empty(&token); break;
            case MESSAGE: handle_message(&token); break;
            case CHANGE_NEIGHBOUR: handle_change_neighbour(&token); break;
            case CONFIRM_CHANGE: handle_confirm_change(&token); break;
            case NOT_A_REAL_TOKEN: handle_not_a_real_token(&token); break;
        }

        // Now if it is real token we send it to neighbour
        if (token.type != NOT_A_REAL_TOKEN)
        {
            printf("SENDING TOKEN OF TYPE: %d\n", token.type);
            switch (arguments.protocol)
            {
                case TCP: send_tcp(sockets, token); break;
                case UDP: break; // TODO
            }
        }
    }
}

void init_communication(struct Args args)
{
    arguments = args;
    queue = new_queue();
    switch (arguments.protocol)
    {
        case TCP: sockets = init_tcp(arguments); break;
        case UDP: break; // TODO
    }

    pthread_t thread;
    pthread_create(&thread, NULL, start_communication, NULL);
}
