//package MCM;

import java.util.Iterator;
import java.util.LinkedList;

public class Road {
	double roadWidth;
	LinkedList<Car> carList;
	long totalIntervalsWithHumanCar;
    long totalIntervalsWithSmartCar;
    long totalAvgVelocitySmart;
    long totalAvgVelocityHuman;
    final double FT_PER_SECOND_TO_MPH = 0.6818182;

    public Road(double roadWidth) {
		carList = new LinkedList<Car>();
		this.roadWidth = roadWidth;
		this.totalAvgVelocityHuman = 0;
		this.totalIntervalsWithHumanCar = 0;
	}

	public void addCar(Car c) {
		carList.add(c);
	}
	public int numberOfCars(){
		return carList.size();
	}
	public void executeTime(double timeInterval) {
        boolean hasEncounteredHuman = false;
        boolean hasEncounteredSmart = false;
        long totalVelocitiesHuman = 0;
        long totalVelocitiesSmart = 0;
        long totalHumanCars = 0;
        long totalSmartCars = 0;
		for (Car i : carList) {
			i.move(timeInterval);
            if(i.isSmart) {
                totalVelocitiesSmart += i.yVel;
                totalSmartCars++;
                hasEncounteredSmart = true;
            }
            else {
                totalVelocitiesHuman += i.yVel;
                totalHumanCars++;
                hasEncounteredHuman = true;
            }
		}
        totalAvgVelocityHuman += ((totalVelocitiesHuman * 1.0) / totalHumanCars);
        totalAvgVelocitySmart += ((totalVelocitiesSmart * 1.0) / totalSmartCars);
        if(hasEncounteredHuman) {
            totalIntervalsWithHumanCar++;
        }
        if(hasEncounteredSmart) {
            totalIntervalsWithSmartCar++;
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
		return (1.0 * totalAvgVelocityHuman) / totalIntervalsWithHumanCar;
	}

	public double getAverageVelocity_HumanCars_MPH() {
		return getAverageVelocity_HumanCars_ftPerSec() * FT_PER_SECOND_TO_MPH;
	}

    public double getAverageVelocity_SmartCars_ftPerSec() {
        return (1.0 * totalAvgVelocitySmart) / totalIntervalsWithSmartCar;
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
