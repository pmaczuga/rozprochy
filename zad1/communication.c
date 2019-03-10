#include <pthread.h>
#include <stdio.h>
#include <fcntl.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include "communication.h"
#include "config.h"
#include "Queue.h"
#include "tcp.h"
#include "udp.h"

struct Queue *queue = NULL;
struct Args arguments;
struct Sockets *sockets;
int socket_multicast;

pthread_t thread;
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

int is_it_my_neighbour(const char *ip, int port)
{
    if (strcmp(ip, arguments.neighbour_ip) != 0) return FALSE;
    if (port != arguments.neighbour_port) return FALSE;
    return TRUE;
}

void init_muliticast()
{
    int res;

    socket_multicast = socket(AF_INET, SOCK_DGRAM, 0);
    if (socket_multicast < 0) end_with_error_errno("Problem creating multicast socket");

    struct sockaddr_in address;
    bzero(&address, sizeof(address));
    address.sin_family = AF_INET;
    address.sin_port = htons(MULTICAST_PORT);
    address.sin_addr.s_addr = inet_addr(MULTICAST_IP);

    res = connect(socket_multicast, (struct sockaddr *) &address, sizeof(address));
    if (res != 0) end_with_error_errno("Problem connecting multicast");
}

void send_multicast(struct Token token)
{
    char buf[BUF_SIZE];
    strcpy(buf, arguments.id);
    ssize_t size = strlen(buf);
    buf[size] = ' ';
    token_to_string(buf + size + 1, token);

    size = send(socket_multicast, buf, strlen(buf) + 1, 0);
    if (size < 0) print_error_errno("Problem sending multicast");
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
    if (is_it_my_neighbour(client_info.neighbour_ip, client_info.neighbour_port) == TRUE)
    {
        switch (arguments.protocol)
        {
            case TCP: change_neighbour_tcp(sockets, client_info.ip, client_info.port); break;
            case UDP: change_neighbour_udp(sockets, client_info.ip, client_info.port); break;
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
    switch (arguments.protocol)
    {
        case TCP: confirm_change_of_input_tcp(sockets, token->useful_int);
        case UDP: confirm_change_of_input_udp(sockets, token->useful_int);
    }

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

void handle_ttl(struct Token *token)
{
    if (strcmp(token->sender, arguments.id) == 0)
    {
        token->ttl--;
    }
    if (token->ttl <= 0)
    {
        printf("\nRemoving token with TTL = 0\n");
        clear_token(token);
    }
}

void *start_communication(void *ptr)
{
    while(1)
    {
        struct Token token;
        switch (arguments.protocol)
        {
            case TCP: token = receive_tcp(sockets); break;
            case UDP: token = receive_udp(sockets); break;
        }

        send_multicast(token);

        // -------------FOR-TESTING---------------
//        print_token(token);
        // ----------------END--------------------
        sleep(1);

        // Decrease ttl - clear token if 0
        handle_ttl(&token);

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
            switch (arguments.protocol)
            {
                case TCP: send_tcp(sockets, token); break;
                case UDP: send_udp(sockets, token); break;
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
        case UDP: sockets = init_udp(arguments); break;
    }

    init_muliticast();

    pthread_create(&thread, NULL, start_communication, NULL);
}

void clean_comunication()
{
    delete_queue(queue);

    pthread_cancel(thread);

    pthread_mutex_destroy(&memory_mutex);

    switch (arguments.protocol)
    {
        case TCP: clean_tcp(sockets); break;
        case UDP: clean_udp(sockets); break;
    }
}
