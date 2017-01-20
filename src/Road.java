/**
 *
 * TODO
 * @author Alyssa Herbst
 * created on 1/19/2017.
 */

import java.util.*;
import java.io.*;

import static java.lang.Math.*;

public class Road {
    private int AverageDailyTrafficCounts;
    private double timeSinceLastCar;
    private List<Car> cars;

    public Road(int averageDailyTrafficCounts) {
        AverageDailyTrafficCounts = averageDailyTrafficCounts;
    }

    /**
     * Will alter this with standard distribution curve later.
     * @return
     */
    public double getTimeLapsePerCar() {
        return  (this.getAverageDailyTrafficCounts()) / (24.0 * 60 * 60);
    }

    public void addCar(Car car) {
        this.cars.add(car);
        this.timeSinceLastCar -= this.getTimeLapsePerCar();
    }

    public int getAverageDailyTrafficCounts() {
        return AverageDailyTrafficCounts;
    }

    public double getTimeSinceLastCar() {
        return timeSinceLastCar;
    }

    public void advanceTimeBy(double time) {
        timeSinceLastCar += time;
    }
}