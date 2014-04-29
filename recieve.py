from graphics import *
import socket

global win

class Bar:
    global win
    def __init__(self, loc, color, _min, _max):
        self.out = Rectangle(loc, Point(loc.getX()+51,loc.getY()+101))
        self.fill = Rectangle(Point(loc.getX()+1,loc.getY()+100),Point(loc.getX()+50, loc.getY()+100))
        self.out.setOutline(color)
        self.out.setFill("white")
        self.out.draw(win)
        self.fill.draw(win)
        self.color = color
        self._min = _min
        self._max = _max
        self.loc = loc
    def Set(self, val):
        mval = (val - self._min) * 100 / (self._max - self._min)
        self.fill.undraw()
        self.fill = Rectangle(Point(self.loc.getX()+1,self.loc.getY()+mval),Point(self.loc.getX()+50,self.loc.getY()+100))
        self.fill.setOutline(self.color)
        self.fill.setFill(self.color)
        self.fill.draw(win)
class Button:
    global win
    def __init__(self, loc, color):
        self.c = Circle(loc, 10)
        self.color = color
        self.c.setOutline(color)
        self.c.setFill("white")
        self.c.draw(win)
    def Press(self):
        self.c.setFill(self.color)
    def Release(self):
        self.c.setFill("white")
    def Set(self, state):
        if state:
            self.c.setFill(self.color)
        else:
            self.c.setFill("white")

win = GraphWin("Recived Gamepad Data", 640, 480)
X = Button(Point(500,240),"blue")
B = Button(Point(560,240),"red")
A = Button(Point(530,270),"green")
Y = Button(Point(530,210),"yellow")
LB = Button(Point(500,150),"grey")
RB = Button(Point(560,150),"grey")
X1 = Bar(Point(10,100),"red",-10,10)
Y1 = Bar(Point(55,100),"green",-10,10)
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.bind(("localhost", 7877))
while 1:
    msg, addr = s.recvfrom(68)
    data = msg.split(",")
    print(msg)
    if len(data) > 12:
        X1.Set(int(data[0]))
        Y1.Set(int(data[1]))
        A.Set(int(data[6]))
        B.Set(int(data[7]))
        X.Set(int(data[8]))
        Y.Set(int(data[9]))
        LB.Set(int(data[10]))
        RB.Set(int(data[11]))
