import socket, time, random, serial
from subprocess import check_output

ser = serial.Serial("/dev/ttyACM0",9600)

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
data = []

data.append({"cputemp": -1})
data.append({"ser": -1})
iter = 0
while 1:
    while 1:
        data[0]["cputemp"] = out = check_output(["/opt/vc/bin/vcgencmd", "measure_temp"])
        data[1]["ser"] = ser.readline()
        time.sleep(.1)
        send = ""
        
        for elements in data:
            send = send + str(elements)
        send = send + str(";")
        s.sendto(send, ("192.168.80.7", 7877))
        iter = iter + 1

