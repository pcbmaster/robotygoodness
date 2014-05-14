#!/usr/bin/python2
import socket

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.bind(("127.0.0.1", 7877))

while True:
    data, addr = s.recvfrom(6)
    print data
