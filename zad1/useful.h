//
// Created by pawel on 3/7/19.
//

#ifndef ZAD1_USEFUL_H
#define ZAD1_USEFUL_H


void print_error(const char *msg);

void print_error_with_errno(const char *msg);

char *get_IP_from_ip_colon_port(const char *str);

int get_port_from_ip_colon_port(const char *str);

char *get_this_IP();

int set_socket_blocking(int socket_fd);

int set_socket_non_blocking(int socket_fd);

char *get_IP_from_sockaddr(struct sockaddr_in* address);

int get_port_from_sockaddr(struct sockaddr_in* address);

#endif //ZAD1_USEFUL_H
