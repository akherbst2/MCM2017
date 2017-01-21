//package MCM;

public class Car {
	double xPos;
	double yPos;
	double xVel;
	double yVel;
	double xAccel;
	double yAccel;
	//maximum accel or decel in ft/s
	static final double ACCEL_LIMIT = 15;
	double INITIAL_SPEED;
	// double accPotential;
	// double breakPotential;
	// double aggressiveness;
	Road road;
	double laneWidth;
	int laneChangeState;
	int lane;
	int xSize;
	int ySize;
	int id;
	boolean crashed;
	public Car(Road road, int lane, double laneWidth, double initialSpeed, int carCount) {
		this.road = road;
		this.lane = lane;
		xPos = lane * laneWidth + .5 * laneWidth;
		yPos = 0;
		id = carCount;
		yVel = initialSpeed;
		this.INITIAL_SPEED = initialSpeed;
		xVel = 0;
		this.laneWidth = laneWidth;
		xSize = 6;
		ySize = 12;
		laneChangeState = 0;
		xAccel = 0;
		yAccel = 0;
	}

	//other car must be in front of my car
	boolean isExpectedCrash(Car other) {
		if(other.yVel < this.yVel) {
			double vRel = other.yVel - this.yVel;
			double carDist = other.yPos - this.yPos - ySize;

			double minAccelNeededToNotCrash = (Math.pow(vRel, 2))/(2*(carDist)) - other.yAccel;
			return Math.abs(minAccelNeededToNotCrash) > ACCEL_LIMIT;
		}
		return false;
	}

	double getMinAccelNeededToNotCrash(Car other) {
		double vRel = other.yVel - this.yVel;
		double carDist = other.yPos - this.yPos - ySize;

		return (Math.pow(vRel, 2))/(2*(carDist)) - other.yAccel;
	}

	Car getCarInFront() {
		Car closestCar = null;
		double closestDist = Double.MAX_VALUE;
		for(Car other:this.road.carList) {
			if(other.lane == this.lane) {
				//check the yDist.
				//if other car is in front
				if(other.yPos > this.yPos) {
					if((other.yPos - this.yPos) < closestDist) {
						closestCar = other;
						closestDist = other.yPos - this.yPos;
					}
				}
			}
		}

		return closestCar;
	}

	//TODO peter do this :)
	public boolean attemptLaneChange() {
		return false;
	}

	public void makeVelocityAndAccelerationDecisions(double timeInterval) {
		double followDistance = (yVel / 10) * ySize;
		Car closest = getCarInFront();

		if((closest != null)&&((closest.yPos - this.yPos) < followDistance)) {
			if(isExpectedCrash(closest)) {
				attemptLaneChange();
			}
			else {
				//Slows down at an acceleration that is somewhere between the minimum deceleration needed
				//to not crash, and the max acceleration of the car
				double minAccel = getMinAccelNeededToNotCrash(closest);
				yAccel = -1 * ((minAccel + ACCEL_LIMIT) / 2);
			}
		}
		else if(yVel < INITIAL_SPEED) {
			//Speeds up to ideal speed if it's possible
			if((INITIAL_SPEED - yVel)*(1 / timeInterval) < ACCEL_LIMIT) {
				yVel = INITIAL_SPEED;
			}
			//otherwise, get as close to the ideal speed as we can
			else {
				yVel += (ACCEL_LIMIT * timeInterval);
			}
		}
	}


	public void move(double timeInterval) {

		//make acceleration decisions
		makeVelocityAndAccelerationDecisions(timeInterval);

		xVel += xAccel * timeInterval;
		yVel += yAccel * timeInterval;

		xPos += xVel * timeInterval;
		yPos += yVel * timeInterval;

		if (laneChangeState == 1) {
			xVel += 1;
			if (xPos > (lane + 1) * laneWidth + .5 * laneWidth) {
				lane++;
				laneChangeState = 0;
			}
		}
		if (laneChangeState == -1) {
			xVel -= 1;
			if (xPos < (lane - 1) * laneWidth + .5 * laneWidth) {
				lane--;
				laneChangeState = 0;
			}
		}

		if (laneChangeState == 0) {
			if (xVel > .5) {
				xVel = .2;
			}
			if (xVel < -.5) {
				xVel = -.2;
			}
			if (xPos < lane * laneWidth + .5 * laneWidth) {
				xVel += .1;
			} else {
				xVel -= .1;
			}
			if (Math.random() < .1 && lane > 0) {
				laneChangeState = -1;
			}
			if (Math.random() < .1 && lane < Math.round(road.roadWidth / laneWidth)) {
				laneChangeState = 1;
			}
		}

	}

	static boolean DEBUG = true;

	public String toString() {
		return id + "," + xPos + "," + yPos + "," + xSize + "," + ySize
				+ (DEBUG ? " lane: " + lane + " lane change: " + laneChangeState : "");
	}

	public boolean isCollision(Car j) {
		boolean xOverlap = Math.abs(xPos - j.xPos) < (xSize + j.xSize) / 2.0;
		boolean yOverlap = Math.abs(yPos - j.yPos) < (ySize + j.ySize) / 2.0;
		if (xOverlap && yOverlap && !crashed) {
			System.out.println("CRASH," + id);
			crashed = true;
		}
		return xOverlap && yOverlap;
	}
}
