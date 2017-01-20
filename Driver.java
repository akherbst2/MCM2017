package MCM;
import java.util.*;
public class Driver {
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
//		int randomSeed = input.nextInt();
		double numberOfTimeIntervals = input.nextDouble();
		double timeInterval = input.nextDouble();
		int numberOfLanes = input.nextInt();
		
		
		double laneWidth =12;
		double freq = .5;
		
		
		
		Road road = new Road(laneWidth);
		CarGenerator generator = new CarGenerator(road, freq, timeInterval);
		for(double time = 0.0;time < numberOfTimeIntervals*timeInterval;time+=timeInterval){
			
			
			for(int lane = 0; lane < numberOfLanes; lane++){
				Car temp = generator.getCar(lane);
				if(temp != null){
					road.addCar(temp);
				}
			}
			
			road.executeTime(timeInterval);
			
			

			System.out.println("TIME: " + time + ": ");
			road.printState();
			
			
			
		}
		
		
		
		
	}
}
