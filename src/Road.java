//package MCM;

import java.util.Iterator;
import java.util.LinkedList;

public class Road {
	double roadWidth;
	LinkedList<Car> carList;

    long allVelocitiesHumanCar;
    long allVelocitiesSmartCar;

    long instanceOfHumanCar;
    long instanceOfSmartCar;

    final double FT_PER_SECOND_TO_MPH = 0.6818182;

    public Road(double roadWidth) {
		carList = new LinkedList<Car>();
		this.roadWidth = roadWidth;
        this.allVelocitiesHumanCar = 0;
        this.allVelocitiesSmartCar = 0;
        this.instanceOfHumanCar = 0;
        this.instanceOfSmartCar = 0;
	}

	public void addCar(Car c) {
		carList.add(c);
	}
	public int numberOfCars(){
		return carList.size();
	}
	public void executeTime(double timeInterval) {



		for (Car i : carList) {
			i.move(timeInterval);

            if(i.isSmart) {
                this.instanceOfSmartCar++;
                this.allVelocitiesSmartCar += ((long)i.yVel);
            }
            else {
                this.instanceOfHumanCar++;
                this.allVelocitiesHumanCar += ((long)i.yVel);
            }
		}


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

	public double getAverageVelocity_HumanCars_ftPerSec() {
		return (1.0 * this.allVelocitiesHumanCar) / this.instanceOfHumanCar;
	}

	public double getAverageVelocity_HumanCars_MPH() {
		return getAverageVelocity_HumanCars_ftPerSec() * FT_PER_SECOND_TO_MPH;
	}

    public double getAverageVelocity_SmartCars_ftPerSec() {
        return (1.0 * this.allVelocitiesSmartCar) / this.instanceOfSmartCar;
    }

    public double getAverageVelocity_SmartCars_MPH() {
        return getAverageVelocity_SmartCars_ftPerSec() * FT_PER_SECOND_TO_MPH;
    }

	public void printState(double time) {
		for (Car i : carList) {
            System.out.println(time + "," + i.toString());
		}
	}
}
