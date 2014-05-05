#!/usr/bin/python2
import curses, smbus, socket

i2c_addr = 0x0f
i2c = smbus.SMBus(1)

forward = 0x09
backwards = 0x06
left = 0x0A
right = 0x05
stop = 0x0f

port = 7777

i2c.write_i2c_block_data(i2c_addr, 0x82, [0xff, 0xff]) #Set motors at full power
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.bind(("", port))

while True:
    data, addr = s.recvfrom(3);
    if data == forward:
        i2c.write_i2c_block_data( i2c_addr, 0xaa, [forward, 0x01])
    elif data == backwards:
        i2c.write_i2c_block_data( i2c_addr, 0xaa, [backwards, 0x01])
    elif data == left:
        i2c.write_i2c_block_data( i2c_addr, 0xaa, [left, 0x01])
    elif data == right:
        i2c.write_i2c_block_data( i2c_addr, 0xaa, [right, 0x01])
    else:
        i2c.write_i2c_block_data( i2c_addr, 0xaa, [stop, 0x01])
