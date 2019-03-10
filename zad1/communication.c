#include <sys/socket.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include "communication.h"
#include "info.h"
#include "tcp.h"
#include "token.h"

char id[SIZE];
enum Protocol protocol;
int has_token = FALSE;

char *to_receive = NULL;
char *to_send_normal = NULL;
char *to_send_system = NULL;

pthread_mutex_t memory_mutex = PTHREAD_MUTEX_INITIALIZER;


void send_token(struct Token *token)
{
    if (protocol == TCP)
    {
        send_message_TCP(token_to_string(token));
    }
    else if (protocol == UDP)
    {
        // TODO
    }
}

struct Token *receive_token()
{
    char *msg;
    struct Token *token;
    if (protocol == TCP)
    {
        msg = receive_message_TCP();
        token = string_to_token(msg);
        free(msg);
    }
    else if (protocol == UDP)
    {
        // TODO
    }
    return token;
}

int send_message(const char *to, const char *msg)
{
    pthread_mutex_lock(&memory_mutex);
    if (to_send_normal != NULL) return 1;

    size_t size = sizeof(to) + sizeof(msg) + 3;
    to_send_normal = malloc(size);
    memset(to_send_normal, '\0', size);
    sprintf(to_send_normal, "%s %s", to, msg);
    pthread_mutex_unlock(&memory_mutex);
}

char *receive_message()
{
    if (to_receive == NULL) return NULL;

    char *msg = malloc(SIZE * sizeof(char));
    memset(msg, '\0', SIZE);

    pthread_mutex_lock(&memory_mutex);
    strcpy(msg, to_receive);
    free(&to_receive);
    to_receive = NULL;
    pthread_mutex_unlock(&memory_mutex);

    return msg;
}

int check_connection_request()
{
    if (to_send_system != NULL) return 1;
    if (protocol == TCP)
    {
        char *msg = check_connection_request_TCP();
        if (msg == NULL) return 1;
        to_send_system = malloc(sizeof(*msg + 1));
        memset(to_send_system, '\0', sizeof(*to_send_system));
        strcpy(to_send_system, msg);
        free(msg);
    }
    else if (protocol == UDP)
    {
        // TODO
    }
    return 0;
}

void *start(void *ptr)
{
    while(1)
    {
        // receive token  and sleep // TODO multicast
        struct Token *token = receive_token();
        has_token = TRUE;
        sleep(1);

        pthread_mutex_lock(&memory_mutex);
        if (token->is_busy == TRUE)
        {
            if (token->type == NORMAL && strcmp(token->receiver, id) == 0)
            {
                to_receive = malloc(sizeof(token->message) + 1);
                memset(to_receive, '\0', sizeof(*to_receive));
                strcpy(to_receive, token->message);
                memset(token, '\0', sizeof(*token));
                token->is_busy = FALSE;
            }
            else if (token->type == CHANGE_NEIGHBOUR)
            {
                printf("Change neighbour\n");
                char old_neighbour_ip[SIZE];
                int old_neighbour_port;
                char new_neighbour_ip[SIZE];
                int new_neighbour_port;
                sscanf(token->message, "%s %d %s %d", new_neighbour_ip, &new_neighbour_port, old_neighbour_ip, &old_neighbour_port);

                printf("Neighbour change msg: %s\n", token->message);
                printf("%s %d %s %d\n", new_neighbour_ip, new_neighbour_port, old_neighbour_ip, old_neighbour_port);

                if (protocol == TCP && is_neighbour_equal_to_TCP(old_neighbour_ip, old_neighbour_port) == TRUE)
                {
                    change_neighbour_TCP(new_neighbour_ip, new_neighbour_port);
                }
                else if (protocol == UDP && 0)
                {
                    // TODO
                }
                token->type == CONFIRMATION;

            }
            else if (token->type == CONFIRMATION)
            {
                char new_commer_ip[SIZE];
                int new_commer_port;
                char his_neighbour_ip[SIZE];
                int his_neighbour_port;
                sscanf(token->message, "%s %d %s %d", new_commer_ip, &new_commer_port, his_neighbour_ip, &his_neighbour_port);

                if (protocol == TCP && is_myself_equal_to_TCP(his_neighbour_ip, his_neighbour_port))
                {
                    confirm_in_connection_change_TCP();
                    memset(token, '\0', sizeof(*token));
                    token->is_busy = FALSE;
                }
                else if (protocol == UDP && 0)
                {
                    // TODO
                }
            }
        }

        if (token->is_busy == FALSE)
        {
            // check if there are requests to connect
            check_connection_request();

            if (to_send_system != NULL)
            {
                strcpy(token-> message, to_send_system);
                strcpy(token->receiver, RECV_ANY);
                token->type = CHANGE_NEIGHBOUR;
//                free(to_send_system);
                to_send_system = NULL;
                strcpy(token->sender, id);
                token->is_busy = TRUE;
                printf("SENT SYSTEM\n");
            }
            else if (to_send_normal != NULL)
            {
                char receiver[SIZE];
                char msg[SIZE];
                sscanf(to_send_normal, "%s %[^.]", receiver, msg);
                free(to_send_normal);
                to_send_normal = NULL;
                strcpy(token->receiver, receiver);
                strcpy(token->message, msg);
                token->type = NORMAL;
                strcpy(token->sender, id);
                token->is_busy = TRUE;
            }
        }
        pthread_mutex_unlock(&memory_mutex);


        send_token(token);
        has_token = FALSE;
        free(token);
    }
}

void init_communication(enum Protocol protocol_type, const char *my_id, int has_token_at_start, const char *neighbour_IP, int neighbour_port)
{
    protocol = protocol_type;
    strcpy(id, my_id);
    has_token = has_token_at_start;

    if (protocol_type == TCP)
    {
        init_TCP(neighbour_IP, neighbour_port);
    }
    else if (protocol_type == UDP)
    {
        // TODO
    }

    if (has_token == TRUE)
    {
        struct Token *token = malloc(sizeof(struct Token));
        memset(token, '\0', sizeof(*token));
        token->is_busy = FALSE;
        send_message_TCP(token_to_string(token));
        free(token);
    }

    pthread_t communication_thread;
    pthread_create(&communication_thread, NULL, &start, NULL);
}
