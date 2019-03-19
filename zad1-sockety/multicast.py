import socket;

multicast_IP = "224.0.0.1"
multicast_port = 1997

serverSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
serverSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
serverSocket.bind((multicast_IP, multicast_port))
buff = []

while True:
    buff, address = serverSocket.recvfrom(1024)
    message = str(buff, 'utf-8')

    # who_get_token = ""
    # msg_type = 0
    # sender = ""
    # receiver = ""
    # usefult_int = 0
    # ttl = 0
    real_message = ""

    parts = message.split()

    print("{} --> TOKEN of type {}".format(parts[0], parts[1]))
