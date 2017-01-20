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
Window_Height=700
t = 0
deltaT = .1

class Vehicle:
    def __init__(self, id, xloc, yloc, xsize, ysize):
        self.id = id
        self.rect = canvas.create_rectangle(xloc, yloc, xloc + xsize, yloc + ysize, fill='green')
        self.xloc = xloc
        self.yloc = yloc
        self.xsize = xsize
        self.ysize = ysize
        #self.speed = speed
        
    def __eq__(self, other):
        return self.id == other.id
    
    def __nq__(self, other):
        return not self.__eq__(other)
        
    def animate(self, x, y):
        #print(self.xloc, self.yloc, self.yloc + self.speed)
        canvas.move(self.rect, x - self.xloc, y - self.yloc)
        self.xloc = x
        self.yloc = y
        
        
def readFile():
     f = open("CarData.csv")
     rows = csv.reader(f)
     next(rows, None)
     return rows
        
def main():
    File = readFile()
    root = Tk()
    global canvas
    canvas = Canvas(root, bg="black", width=Window_Width, height=Window_Height)
    canvas.pack()
    vehicle_Dict = {}
    #car = Vehicle(69, 50, 50, 30, 60, 1)
    #car2 = Vehicle(1, 200, 50, 30, 60, 2)
    #vehicle_List.append(car)
    #vehicle_List.append(car2)
    t = 0
    for line in File:   
        if not t == line[0]:
            canvas.after(1000)
            t = line[0]
        vehicle = vehicle_Dict.get(line[1])
        if vehicle == None:
            vehicle_Dict[line[1]] = Vehicle(line[1], line[2], line[3],
                                            line[4], line[5])
        else:
            vehicle.animate()
        root.update()
    
    
main()


