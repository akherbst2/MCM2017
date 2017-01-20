package MCM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Road {
	double roadWidth;
	LinkedList<Car> carList;
	public Road(double roadWidth){
		carList = new LinkedList<Car>();
		this.roadWidth = roadWidth;
	}
	public void addCar(Car c){
		carList.add(c);
	}
	public void executeTime(double timeInterval){
		for(Car i: carList){
			i.move(timeInterval);
		}
		//CollisionCheck
		for(Car i: carList){
			for(Car j: carList){
				if(i != j){
					if(i.isCollision(j)){
						i.xVel = 0;
						i.yVel = 0;
						j.xVel = 0;
						j.yVel = 0;
					}
				}
			}
		}
		LinkedList<Car> temp = new LinkedList<Car>();
		
		for(Car i: carList){
			if(i.yPos < 500){
				temp.add(i);
			}
		}
		carList = temp;
		
	}
	public void printState() {
		for(Car i: carList){
			System.out.println(i.toString());
		}
	}
}
