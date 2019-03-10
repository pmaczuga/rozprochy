cmake_minimum_required(VERSION 3.10)
project(zad1_new C)

set(CMAKE_C_STANDARD 99)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -pthread")

add_executable(zad1_new main.c token.c token.h config.c config.h Queue.c Queue.h communication.c communication.h tcp.c tcp.h)