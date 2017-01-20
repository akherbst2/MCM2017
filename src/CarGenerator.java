package MCM;

import java.util.Arrays;

public class CarGenerator {
	double freq;
    double currentTime;
    double timeTickSize;
	Road road;
	int carCount = 0;
	double initialSpeedOfCar;
	double[] timeLastCar;
	public CarGenerator(Road road, int numberOfLanes, double freq, double timeStep){
		this.freq = freq;
		this.road = road;
		this.timeTickSize = timeStep;
		initialSpeedOfCar = 88;
		currentTime = 0;
		timeLastCar = new double[numberOfLanes];
		Arrays.fill(timeLastCar, currentTime-1000);
	}
	
	public void advanceTime() {
        currentTime += timeTickSize;
    }

	
	
	public Car getCar(){
		int laneId = -1;
		double maxTimeDiff = -1;
		for(int a = 0; a < timeLastCar.length; a++){
			if(maxTimeDiff < currentTime-timeLastCar[a]){
				maxTimeDiff = currentTime-timeLastCar[a];
				laneId = a;
			}
		}
		
		if(Math.random() < freq * timeTickSize){
			carCount++;
			timeLastCar[laneId] = currentTime;
			return new Car(road, laneId, road.roadWidth, 88, carCount);
		}
		return null;
	}

}
