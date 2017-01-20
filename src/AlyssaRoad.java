/**
 *
 * TODO
 * @author Alyssa Herbst
 * created on 1/19/2017.
 */

import java.util.*;

public class AlyssaRoad {

    private int index;
    private int averageDailyTrafficCounts;
    //private double timeSinceLastCar;
    private List<Car> cars;

    public AlyssaRoad(int averageDailyTrafficCounts, int idx) {
        this.averageDailyTrafficCounts = averageDailyTrafficCounts;
        this.index = idx;
        this.cars = new ArrayList<>();
    }

    public List<Car> getCars() {
        return cars;
    }

    /**
     * TODO Make this return something unique
     * @return
     */
    @Override
    public int hashCode() {
        return this.index;
    }

    /**
     * Will alter this with standard distribution curve later.
     * @return
     */
    public double getTimeLapsePerCar() {
        return  (24.0 * 60 * 60) / (this.getAverageDailyTrafficCounts());
    }

    public void addCar(Car car) {
        this.cars.add(car);
        //this.timeSinceLastCar -= this.getTimeLapsePerCar();
    }

    public int getAverageDailyTrafficCounts() {
        return averageDailyTrafficCounts;
    }

//    public double getTimeSinceLastCar() {
//        return timeSinceLastCar;
//    }
//
//    public void advanceTimeBy(double time) {
//        timeSinceLastCar += time;
//    }
}