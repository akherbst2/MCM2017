/**
 * Constructor-- read from scanner for data I need
 * Maybe 10 numbers?
 * Time intervals, new car per lane, number of lanes, random seed
 *
 * carGenerator take a road in constructor
 * calling generate, will add the cars onto the road
 *
 *
 * @author Alyssa Herbst
 * created on 1/19/2017.
 */

import java.util.*;
import java.io.*;

import static java.lang.Math.*;

public class CarGenerator {
    //The interval of time at which we are at currently.  Increases by 1 each time.
    private double currentTime;
    private double timeTickSize;
    private List<Road> roads;//  Gets the frequency from roads
    HashMap<Road, Double> timeSinceLastCar;


    public CarGenerator(List<Road> roads, double currentTime, double timeTickSize) {
        this.roads = roads;
        this.currentTime = currentTime;
        this.timeTickSize = timeTickSize;
        timeSinceLastCar = new HashMap<>();
        for(Road road: roads) {
            timeSinceLastCar.put(road, 0.0);
        }
    }

    private void advanceTime() {
        currentTime += timeTickSize;
        for(Road road:roads) {
            timeSinceLastCar.put(road, timeSinceLastCar.get(road) + timeTickSize);
        }
    }

    //Adds cars where appropriate onto the road
    public void generate() {
        advanceTime();

        for(Road road:roads) {
            //The number of seconds between each car pass
            double timeLapsePerCar = road.getTimeLapsePerCar();
            //if the time that the car should have passed has passed sometime within the last time interval
            //then send out a car at that time.

            if(road.getTimeSinceLastCar() >= timeLapsePerCar) {
                road.addCar(new Car());

            }
        }
    }
}