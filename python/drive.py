#!/usr/bin/python2
import curses, socket

class Packet:
    def __init__(self, bus, addr):
        print "init!"
        #self.i2c_addr = addr
        #self.i2c = smbus.SMBus(bus)
    def begin(self):
        print "begin!"
        #self.i2c.write_i2c_block_data(self.i2c_addr, 0x82, [0xff, 0xff]) #Drive motors at full power
    def parse(self, data):
        Left_Dir = data[1]
        Right_Dir = data[2]
        Speed = data[3:]
        if Left_Dir == "1" and Right_Dir == "1":
            return 0x09
        if Left_Dir == "0" and Right_Dir == "0":
            return 0x06
        if Left_Dir == "1" and Right_Dir == "0":
            return 0x0A
        if Left_Dir == "0" and Right_Dir == "1":
            return 0x05
        if Left_Dir == "2" and Right_Dir == "2":
            return 0x0F

forward = 0x09
backwards = 0x06
left = 0x0A
right = 0x05
stop = 0x0f

port = 7877

p = Packet(0x0F, 1)
p.begin()
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.bind(("127.0.0.1", port))

while True:
    data, addr = s.recvfrom(6)
    data = p.parse(data)
    print(data)
'''
    if data == forward:
        i2c.write_i2c_block_data( p.i2c_addr, 0xaa, [forward, 0x01])
    elif data == backwards:
        i2c.write_i2c_block_data( p.i2c_addr, 0xaa, [backwards, 0x01])
    elif data == left:
        i2c.write_i2c_block_data( p.i2c_addr, 0xaa, [left, 0x01])
    elif data == right:
        i2c.write_i2c_block_data( p.i2c_addr, 0xaa, [right, 0x01])
    else:
        i2c.write_i2c_block_data( p.i2c_addr, 0xaa, [stop, 0x01])
'''
