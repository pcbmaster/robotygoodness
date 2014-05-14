import gamepad, socket, time

ip = "192.168.80.12"
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
old = []
gdata = []
while 1:
    while old == gdata:
        gdata = gamepad.get()
    old = gdata
    if gdata[0] > 5:
        s.sendto("R10255",(ip,7777))
    elif gdata[0] < -5:
        s.sendto("R01255",(ip,7777))
    elif gdata[1] < -5:
        s.sendto("R11255",(ip,7777))
    elif gdata[1] > 5:
        s.sendto("R00255",(ip,7777))
    else:
        s.sendto("R22255",(ip,7777))
