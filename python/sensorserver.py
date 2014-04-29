import socket, time, random
from subprocess import check_output

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
data = []

data.append({"cputemp": -1})
iter = 0
while 1:
    while 1:
        data[0]["cputemp"] = out = check_output(["/opt/vc/bin/vcgencmd", "measure_temp"])
        time.sleep(.1)
        send = ""

        for elements in data:
            send = send + str(elements)
        send = send + str(";")
        s.sendto(send, ("192.168.80.8", 7877))
        iter = iter + 1
        if (iter == 10):
            print "Heartbeat"
            iter = 0
