///**
// * @author Alyssa Herbst
// * created on 1/19/2017.
// */
//
//import java.util.*;
//
//public class Driver {
//    static StringBuilder sb;
//
//    public static void main(String[] args) {
//
//        List<AlyssaRoad> rds = new ArrayList<>();
//        rds.add(new AlyssaRoad(50000, 1));
//        rds.add(new AlyssaRoad((100000), 2));
//        rds.add(new AlyssaRoad((150000), 3));
//
//
//        AlyssaCarGenerator gen = new AlyssaCarGenerator(rds, 0, 0.1);
//
//        for(int i= 0; i < 100; i++) {
//            gen.generate();
//        }
//        for(AlyssaRoad road:rds) {
//            System.out.println(road.getCars().size());
//        }
//
//    }
//
//}
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
