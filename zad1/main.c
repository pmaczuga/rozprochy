#include <stdio.h>
#include <string.h>
#include <signal.h>
#include <stdlib.h>
#include "config.h"
#include "token.h"
#include "communication.h"

void handle_INT(int sig_number)
{
    printf("\nSIG_INT\n");
    exit(EXIT_SUCCESS);
}

void init_sig_handling()
{
    // handle INT signal
    struct sigaction act;
    act.sa_flags = 0;
    sigemptyset(&act.sa_mask);
    act.sa_handler = handle_INT;
    sigaction(SIGINT, &act, NULL);
}

void clean_up()
{
    printf("\n\nCleaning up...\n");
    clean_comunication();
}

void init_atexit()
{
    atexit(clean_up);
}

int main(int argc, char *argv[]) {

    init_sig_handling();
    init_atexit();

    struct Args args = init_args(argc, argv);
    print_args(args);

    init_communication(args);

    while(1)
    {
        char receiver[BUF_SIZE];
        char message[BUF_SIZE];
        printf("\nEnter receiver: \n");
        fgets(receiver, BUF_SIZE, stdin);
        printf("Enter message: \n");
        fgets(message, BUF_SIZE, stdin);

        ssize_t size = strlen(receiver);
        if (size > 0 && receiver[size-1] == '\n') receiver[size-1] = '\0';
        size = strlen(message);
        if (size > 0 && message[size-1] == '\n') message[size-1] = '\0';

        printf("Sending \"%s\" to \"%s\"\n", message, receiver);

        send_message(receiver, message);
    }

    return 0;
}
