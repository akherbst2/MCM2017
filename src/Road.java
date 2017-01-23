//package MCM;

import java.util.Iterator;
import java.util.LinkedList;

public class Road {
	double roadWidth;
	LinkedList<Car> carList;
	long totalAvgVelocity;
	long totalIntervals;
	long totalCars;


	public Road(double roadWidth) {
		carList = new LinkedList<Car>();
		this.roadWidth = roadWidth;
		this.totalAvgVelocity = 0;
		this.totalIntervals = 0;
	}

	public void addCar(Car c) {
		carList.add(c);
	}
	public int numberOfCars(){
		return carList.size();
	}
	public void executeTime(double timeInterval) {
		totalIntervals++;
        long myVel = 0;
		for (Car i : carList) {
			i.move(timeInterval);
			myVel += i.yVel;

		}
        totalAvgVelocity += ((myVel * 1.0) / carList.size());
		// CollisionCheck
		for (Car i : carList) {
			for (Car j : carList) {
				if (i.id < j.id) {
					if (i.isCollision(j)) {
						i.xVel = 0;
						i.yVel = 0;
                                                i.yAccel = 0;
                                                j.yAccel = 0;
						j.xVel = 0;
						j.yVel = 0;
					}
				}
			}
		}
		Iterator<Car> iter = carList.iterator();
		while (iter.hasNext()) {
			Car i = iter.next();
			if (i.yPos >= 500) {
				iter.remove();
				System.out.println("STOP," + i.id);
			}
		}

	}

	public double getAverageVelocity_ftPerSec() {
		return (1.0 * totalAvgVelocity) / totalIntervals;
	}

	public double getAverageVelocity_MPH() {
		return getAverageVelocity_ftPerSec() * 0.6818182;
	}

	public void printState(double time) {
		for (Car i : carList) {
			System.out.println(time + "," + i.toString());
		}
	}
}
