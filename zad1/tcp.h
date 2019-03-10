//
// Created by pawel on 3/7/19.
//

#ifndef ZAD1_TCP_H
#define ZAD1_TCP_H

void init_TCP(const char *neighbour_IP, int neighbour_port);

void send_message_TCP(const char *msg);

char *receive_message();

char *receive_message_TCP();

char *check_connection_request_TCP();

void change_neighbour_TCP(const char *neighbour_IP, int neighbour_port);

int is_neighbour_equal_to_TCP(const char *ip, int port);

int is_myself_equal_to_TCP(const char *ip, int port);

void confirm_in_connection_change_TCP();


void close_connection_TCP();

#endif //ZAD1_TCP_H
