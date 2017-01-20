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
	int xSize;
	int ySize;
	int id;
	public Car(Road road, int lane, double laneWidth, double initialSpeed, int carCount){
		this.road = road;
		this.lane = lane;
		xPos = lane * laneWidth + .5 * laneWidth;
		yPos = 0;
		id = carCount;
		yVel = initialSpeed;
		xVel = 0;
		xSize = 6;
		ySize = 12;
	}
	
	
	public void move(double timeInterval){
		xPos += xVel * timeInterval;
		yPos += yVel * timeInterval;
	}
	public String toString(){
		return "Car ID: " + id + " X: " + xPos + " Y: " + yPos + " Lane: " + lane;
	}


	public boolean isCollision(Car j) {
		boolean xOverlap = Math.abs(xPos-j.xPos) < (xSize + j.xSize)/2.0;
		boolean yOverlap = Math.abs(yPos-j.yPos) < (ySize + j.ySize)/2.0;
		return xOverlap && yOverlap;
	}
}
