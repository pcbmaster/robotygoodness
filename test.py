import gamepad, socket, time

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
while 1:
    while 1:
        gdata = gamepad.get()
        #gdata[0] = int(round(gdata[0]*10))
        #gdata[1] = int(round(gdata[1]*10))
        #gdata[2] = int(round(gdata[2]*10))
        #gdata[3] = int(round(gdata[3]*10))
        #gdata[4] = int(round(gdata[4]*10))
        #gdata[5] = int(round(gdata[5]*10))
        send = ''
        for data in gdata:
            if len(str(data)) == 1:
                send = send + str(data) + "  " + ","
            elif len(str(data)) == 2:
                send = send + str(data) + " " + ","
            elif len(str(data)) == 3:
                send = send + str(data) + ","
        #print(len(send))
        s.sendto(send, ("localhost", 7877))
