#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>



#define SIZE 1024

enum Protocol
{
    TCP,
    UDP
};

struct Token
        {
    int busy;
    char message[SIZE];
};

char id[SIZE];
char neighbour_addr[SIZE];
int neighbour_port;
enum Protocol protocol;
struct Token* token = NULL;

void parseArgs(int argc, char *argv[])
{
    if(argc < 5)
    {
        printf("Arguments:\n"
                        "client ID,\n"
                        "ip:port of neighbour,\n"
                        "has token (1/0),\n"
                        "protocol (TCP/UDP)\n");
        exit(EXIT_FAILURE);
    }
    strcpy(id, argv[1]);
    sscanf(argv[2], "%s:%d", neighbour_addr, neighbour_port);
    if (strcmp(argv[3], "1") == 0)
    {
        token = malloc(sizeof(struct Token));
        token->busy = 0;
        strcpy(token->message, "\0");
    } else if (strcmp(argv[3], "0") != 0)
    {
        fprintf(stderr, "ERROR: Third argument has to be either 0 or 1\n");
        exit(EXIT_FAILURE);
    }
    if (strcmp(argv[4], "TCP") == 0)
    {
        protocol = TCP;
    } else if (strcmp(argv[4], "UDP") == 0)
    {
        protocol = UDP;
    } else
    {
        fprintf(stderr, "ERROR: Fourth argument has to be either TCP or UDP\n");
        exit(EXIT_FAILURE);
    }

}

char *getIP()
{
    return inet_ntoa(*((struct in_addr*)
            host_entry->h_addr_list[0]));
}

int main(int argc, const char* argv[])
{
    parseArgs(argc, argv);


    printf("IP: %s\n", );

    printf("Hello, World!\n");
    return 0;
}
