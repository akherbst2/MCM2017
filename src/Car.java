package MCM;

public class Car {
    double xPos;
    double yPos;
    double xVel;
    double yVel;
    boolean laneChange;
    double accPotential;
    double breakPotential;
    double aggressiveness;
    Road road;
    int lane;
    public Car(Road road, int lane, double laneWidth, double initialSpeed){
        this.road = road;
        this.lane = lane;
        xPos = lane * laneWidth + .5 * laneWidth;
        yPos = 0;
        yVel = initialSpeed;
        xVel = 0;
    }


    public void move(double timeInterval){
        xPos += xVel * timeInterval;
        yPos += yVel * timeInterval;
    }
    public String toString(){
        return "X: " + xPos + " Y: " + yPos + " Lane: " + lane;
    }
}
