#include <stdio.h>
#include "config.h"
#include "token.h"
#include "communication.h"

int main(int argc, char *argv[]) {

    struct Args args = init_args(argc, argv);
    print_args(args);

    init_communication(args);

    while(1)
    {
        char receiver[BUF_SIZE];
        char message[BUF_SIZE];
        printf("\nEnter receiver: \n");
        scanf("%s", receiver);
        printf("Enter message: \n");
        scanf("%[^\n]", message);
        send_message(receiver, message);
    }

    return 0;
}
