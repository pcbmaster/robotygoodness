#!/usr/bin/python2
import serial
ser = serial.Serial("/dev/ttyACM0",9600)
while 1:
	bla = ser.readline()
	for s in bla.split(","):
		print s
