import gamepad, socket, time

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
while 1:
    while 1:
        gdata = gamepad.get()
        if gdata[0] > 5:
            s.sendto("R10255",("127.0.0.1",7877))
        elif gdata[0] < -5:
            s.sendto("R01255",("127.0.0.1",7877))
        elif gdata[1] < -5:
            s.sendto("R11255",("127.0.0.1",7877))
        elif gdata[1] > 5:
            s.sendto("R00255",("127.0.0.1",7877))
        else:
            s.sendto("R22255",("127.0.0.1",7877))
