//package MCM;

import java.util.*;

public class CarGenerator {
	static final double STANDARD_DEV_SPEED_MPH = 5;
	static final double AVG_SPEED_MPH = 60;
	static final double percentSmartCars = .9;
	double freq;
    double currentTime;
    double timeTickSize;
	Road road;
	int carCount = 0;
	double initialSpeedOfCar;
	double[] timeLastCar;
	Random random;
	public CarGenerator(Scanner input, Road road, int numberOfLanes, double freq, double timeStep){
		this.freq = freq;
		this.road = road;
		this.timeTickSize = timeStep;
		initialSpeedOfCar = 88;
		currentTime = 0;
		timeLastCar = new double[numberOfLanes];
		Arrays.fill(timeLastCar, currentTime-1000);
		this.random = new Random();
	}
	
	public void advanceTime() {
        currentTime += timeTickSize;
    }

	
	
	public Car getCar(){
//        		int laneId = -1;
//		double maxTimeDiff = -1;
//		for(int a = 0; a < timeLastCar.length; a++){
//			if(maxTimeDiff < currentTime-timeLastCar[a]){
//				maxTimeDiff = currentTime-timeLastCar[a];
//				laneId = a;
//			}
//		}

		//int laneId = -1;
		double totalTimeDiff = 0;
		List<Double> possibleLaneTimes = new ArrayList<>();
		List<Integer> possibleLaneIds = new ArrayList<>();
		for(int a = 0; a < timeLastCar.length; a++) {
			if((currentTime - timeLastCar[a]) > (timeTickSize * 12)) {
				possibleLaneIds.add(a);
				possibleLaneTimes.add(currentTime - timeLastCar[a]);
                totalTimeDiff += (currentTime - timeLastCar[a]);
			}
		}
        int laneId = 0;
        if(possibleLaneIds.size() > 0) {
            double result = Math.random();
            double currProbability = possibleLaneTimes.get(laneId) / totalTimeDiff;
            while (result > currProbability) {
                laneId++;
                currProbability += (possibleLaneTimes.get(laneId) / totalTimeDiff);
            }
        }
        else {
            laneId = (int)(Math.random() * timeLastCar.length);
        }


		if(Math.random() < freq * timeTickSize && road.numberOfCars() < 500){
			carCount++;
			timeLastCar[laneId] = currentTime;

			double randomInitialSpeed_ftpersec = (random.nextGaussian() * STANDARD_DEV_SPEED_MPH + AVG_SPEED_MPH) * (88.0 / 60);
			boolean isSmart =  (Math.random() < percentSmartCars);		
	
			return new Car(road, laneId, road.roadWidth, randomInitialSpeed_ftpersec, carCount, isSmart);
		}
		return null;
	}

}
