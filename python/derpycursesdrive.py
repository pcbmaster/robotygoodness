#!/usr/bin/python2
import curses, smbus

i2c_addr = 0x0f


i2c = smbus.SMBus(1)


forward = 0x09

backwards = 0x06

left = 0x0A

right = 0x05

stop = 0x0f

i2c.write_i2c_block_data(i2c_addr, 0x82, [0xff, 0xff])

#os.system("i2cset -y 1 0x0f 0x82 0xff 0xff i")

scr = curses.initscr()                                                                                                                                 
curses.cbreak()                                                                                                                                        
scr.keypad(1)                                                                                                                                          
scr.timeout(75)

scr.addstr(0,10,"Hit 'q' to quit")

scr.refresh()

key = ''



while key != ord('q'):
    key = scr.getch()
    if key == curses.KEY_UP:
        i2c.write_i2c_block_data( i2c_addr, 0xaa, [forward, 0x01])
    elif key == curses.KEY_DOWN:
        i2c.write_i2c_block_data( i2c_addr, 0xaa, [backwards, 0x01])
    elif key == curses.KEY_LEFT:
        i2c.write_i2c_block_data( i2c_addr, 0xaa, [left, 0x01])
    elif key == curses.KEY_RIGHT:
        i2c.write_i2c_block_data( i2c_addr, 0xaa, [right, 0x01])
    else:
        i2c.write_i2c_block_data( i2c_addr, 0xaa, [stop, 0x01])

curses.endwin()