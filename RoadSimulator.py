import re
import csv
import os, sys
from itertools import repeat
import time
try:
    from Tkinter import *
except ImportError:
    from tkinter import *

Window_Width=1000
Window_Height=600
laneWidth_ft = 12
buffer = 100
lanes = 8
scale = 1
t = 0
deltaT = .1

class Vehicle:
    def __init__(self, id, xloc, yloc, xsize, ysize):
        self.id = id
        self.rect = canvas.create_rectangle(buffer + (xloc - (xsize / 2.0)) * scale , 
                                            Window_Height -( yloc + ysize) * scale,
                                             buffer + (xloc + (xsize / 2.0)) * scale, Window_Height - yloc*scale,
                                              fill='green')
        #print(scale)
        
        self.xloc = xloc
        self.yloc = yloc
        self.xsize = xsize
        self.ysize = ysize
        
    def __eq__(self, other):
        if other == None:
            return FALSE
        else:
            return self.id == other.id
    
    def __nq__(self, other):
        return not self.__eq__(other)
        
    def animate(self, x, y):
        canvas.move(self.rect, (x - self.xloc)*scale, (self.yloc - y)*scale)
        self.xloc = x
        self.yloc = y
        
class Road:
    def __init__(self):
        self.rect = canvas.create_rectangle(buffer, 0,  buffer + lanes * laneWidth_ft * scale, Window_Height, fill='#111')
        for i in range(1, lanes):
            canvas.create_line( buffer + i * (laneWidth_ft * scale), 0,  buffer + i * (laneWidth_ft * scale), Window_Height, fill="yellow", dash=(6,5)) 
        
        
def readFile():
     f = open("CarData3.csv")
     rows = csv.reader(f)
     #next(rows, None)
     return rows
 
def exitWindow(root):
    root.destroy()
    
def pause():
    canvas.after(3000)
        
def main():
    File = readFile()
    sizes = File.next()
    global scale
    global lanes
    scale = Window_Height * 1.0 / float(sizes[0])
    lanes = int(sizes[1])
    Window_Width = lanes * laneWidth_ft * scale + buffer * 2
    root = Tk()
    global canvas
    canvas = Canvas(root, bg="#632", width=Window_Width, height=Window_Height)
    canvas.pack()
    road = Road()
    pauseButton = Button(root, text="OK", command=pause, state="active")
    pauseButton.pack(side=LEFT)
    vehicle_Dict = {}
    t = 0
    """start animation"""
    for line in File:
        if line[0] == "STOP":
            canvas.delete(vehicle_Dict[line[1]].rect)
            del vehicle_Dict[line[1]]
            continue
        if line[0] == "CRASH":
            canvas.itemconfig(vehicle_Dict.get(line[1]).rect,fill="red")
            #canvas.itemconfig(vehicle_Dict.get(line[2]).rect,fill="red")
            continue
        if not t == line[0]:
            canvas.after(90)
            t = line[0]
        vehicle = vehicle_Dict.get(line[1])
        if vehicle == None:
            vehicle_Dict[line[1]] = Vehicle(float(line[1]), float(line[2]), float(line[3]),
                                            float(line[4]), float(line[5]))
        else:
            vehicle.animate(float(line[2]), float(line[3]))
        root.update()
    canvas.after(10000)

main()


