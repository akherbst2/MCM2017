//package MCM;

import java.util.*;

public class Driver {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		// int randomSeed = input.nextInt();
		double numberOfTimeIntervals = input.nextDouble();
		double timeInterval = input.nextDouble();
		int numberOfLanes = input.nextInt();
		
		double laneWidth = 12;
		double freq = 5;
		System.out.println(500 + "," + numberOfLanes);
		Road road = new Road(laneWidth);
		CarGenerator generator = new CarGenerator(input, road, numberOfLanes, freq, timeInterval);
		for (double time = 0.0; time < numberOfTimeIntervals * timeInterval; time += timeInterval) {

			Car temp = generator.getCar();
			if (temp != null) {
				road.addCar(temp);
			}
                        road.printState(time);
			road.executeTime(timeInterval);
			generator.advanceTime();


		}
        System.err.println("Average total velocity of human cars= " + road.getAverageVelocity_HumanCars_MPH() + " Miles Per Hour");
        System.err.println("Average total velocity of smart cars= " + road.getAverageVelocity_SmartCars_MPH() + " Miles Per Hour");
	}
}
