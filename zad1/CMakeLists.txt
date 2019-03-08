cmake_minimum_required(VERSION 3.10)
project(zad1 C)

set(CMAKE_C_STANDARD 99)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -pthread")

add_executable(zad1 main.c token.h info.h token.c useful.c useful.h tcp.c tcp.h communication.c communication.h input_reading.c input_reading.h)
