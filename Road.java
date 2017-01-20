package MCM;

import java.util.ArrayList;

public class Road {
	double roadWidth;
	ArrayList<Car> carList;
	public Road(double roadWidth){
		carList = new ArrayList<Car>();
		this.roadWidth = roadWidth;
	}
	public void addCar(Car c){
		carList.add(c);
	}
	public void executeTime(double timeInterval){
		for(Car i: carList){
			i.move(timeInterval);
		}
	}
	public void printState() {
		// TODO Auto-generated method stub
		for(int car = 0; car < carList.size(); car++){
			System.out.println(car + ": " + carList.get(car).toString());
		}
	}
}
