package MCM;

import java.util.*;

public class Driver {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		// int randomSeed = input.nextInt();
		double numberOfTimeIntervals = input.nextDouble();
		double timeInterval = input.nextDouble();
		int numberOfLanes = input.nextInt();

		double laneWidth = 12;
		double freq = 4;
		System.out.println(500 + "," + numberOfLanes);
		Road road = new Road(laneWidth);
		CarGenerator generator = new CarGenerator(input, road, numberOfLanes, freq, timeInterval);
		for (double time = 0.0; time < numberOfTimeIntervals * timeInterval; time += timeInterval) {

			Car temp = generator.getCar();
			if (temp != null) {
				road.addCar(temp);
			}

			road.executeTime(timeInterval);
			generator.advanceTime();

			road.printState(time);

		}

	}
}
