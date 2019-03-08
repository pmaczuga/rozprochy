#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <netdb.h>
#include <signal.h>
#include <time.h>
#include <fcntl.h>
#include <errno.h>
#include <pthread.h>

#include "token.h"
#include "info.h"
#include "useful.h"
#include "communication.h"
#include "input_reading.h"


// Parsed arguments + token
const char *id;                 // client id string
const char *neighbour_addr;     // address where token is send
int neighbour_port;
enum Protocol protocol;         // protocol type - UDP or TCP
int token_at_start = FALSE;

// sockets, addr structs
int socket_in;
int socket_out;
struct sockaddr *address_in;
struct sockaddr *address_out;

void handle_INT(int sig_number)
{
    exit(EXIT_SUCCESS);
}

void clean_up()
{
    printf("Closing...\n");
    printf("Cleaning up...\n");

    shutdown(socket_in, SHUT_RDWR);
    shutdown(socket_out, SHUT_RDWR);

    close(socket_in);
    close(socket_out);
    printf("DONE!\n");
}

void init()
{
    // Clean up after exit
    atexit(clean_up);

    // handle INT signal
    struct sigaction act;
    act.sa_flags = 0;
    sigemptyset(&act.sa_mask);
    act.sa_handler = handle_INT;
    sigaction(SIGINT, &act, NULL);
}


/**
 * Parse arguments and fill global variables
 */
void parse_args(int argc, const char **argv)
{
    if(argc < 5)
    {
        printf("Arguments:\n"
                        " - client ID,\n"
                        " - ip:port of neighbour,\n"
                        " - has token (1/0),\n"
                        " - protocol (TCP/UDP)\n");
        exit(EXIT_FAILURE);
    }

    id = argv[1];

    neighbour_addr = get_IP_from_ip_colon_port(argv[2]);
    if (neighbour_addr == NULL) print_error("Wrong ip address");

    neighbour_port = get_port_from_ip_colon_port(argv[2]);
    if (neighbour_port == -1) print_error("Wrong port");

    if (strcmp(argv[3], "1") == 0)
    {
        token_at_start = TRUE;
    } else if (strcmp(argv[3], "0") != 0) print_error("Third argument has to be either 0 or 1");

    if (strcmp(argv[4], "TCP") == 0)
    {
        protocol = TCP;
    } else if (strcmp(argv[4], "UDP") == 0)
    {
        protocol = UDP;
    } else print_error("Fourth argument has to be either TCP or UDP");
}



void print_args()
{
    printf("ID: %s\n", id);
    printf("neighbour IP: %s\n", neighbour_addr);
    printf("neighbour port: %d\n", neighbour_port);
    printf("protocol: %d\n", protocol);
    printf("token: %d\n", token_at_start);
}



void test()
{
    printf("----------------------------\n");
    printf("------------TESTING---------\n");
    printf("----------------------------\n");

    char string[SIZE] = "0 0 sender receiver message asfsdf sdfsdg\0";
    struct Token *my_token = string_to_token(string);
    print_token(my_token);
    char *another_string = token_to_string(my_token);
    printf("%s\n", another_string);

    printf("----------------------------\n\n\n");
}

int main(int argc, const char* argv[])
{
    init();

    test();

    parse_args(argc, argv);

    print_args();
    printf("\n");

    init_communication(protocol, id, token_at_start, neighbour_addr, neighbour_port);

    printf("\n");
    printf("IP: %s\n", get_IP_from_sockaddr(address_in));
    printf("PORT: %d\n", get_port_from_sockaddr(address_in));
    printf("\n");

    pthread_t input_thread;
    pthread_create(&input_thread, NULL, &read_input, NULL);

    while(1)
    {
        char msg[SIZE];
        char receiver[SIZE];
        scanf("%s %[^.\n]", receiver, msg);
        printf("!!!\n");
        send_message(receiver, msg);

        char *recv_msg = receive_message();
        if (recv_msg != NULL)
        {
            printf("Got message: %s\n", recv_msg);
            free(recv_msg);
        }
    }

    return 0;
}
