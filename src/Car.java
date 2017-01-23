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
	boolean isSmart;
	boolean crashed;
	public Car(Road road, int lane, double laneWidth, double initialSpeed, int carCount, boolean isSmart) {
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
		this.isSmart = isSmart;
	}
/*
	//other car must be in front of my car
	boolean isExpectedCrash(Car other) {
		if(other.yVel < this.yVel) {
			double minAccelNeededToNotCrash = getMinAccelNeededToNotCrash(other); 
			return Math.abs(minAccelNeededToNotCrash) > ACCEL_LIMIT;
		}
		return false;
	}
	double getMinAccelNeededToNotCrash(Car other) {
		double vRel = other.yVel - this.yVel;
		double carDist = .9 * (other.yPos - (this.yPos + ySize))-5;
                double ans = (Math.pow(vRel, 2))/(2*(carDist)) + other.yAccel;
               // System.err.println("Minimum Accel = " + ans);
	        if(ans < 0) System.err.println("VRel: " + vRel + " CarDistance: " + carDist + " other acc " + other.yAccel);
         	return ans;
	}
*/
	Car getCarInFront() {
		Car closestCar = null;
		double closestDist = Double.MAX_VALUE;
		for(Car other:this.road.carList) {
				//check the yDist.
				//if other car is in front
			if(12 > Math.abs(other.xPos-this.xPos)){
                         	if(other.yPos > this.yPos+ySize) {
					if((other.yPos - this.yPos) < closestDist ) {  
						closestCar = other;
						closestDist = other.yPos - this.yPos;
					}
				}
                       }
		}

		return closestCar;
	}


/*	public void makeVelocityAndAccelerationDecisions(double timeInterval) {
		double followDistance = (yVel / 10) * ySize + 20;
		Car closest = getCarInFront();
                //if(closest != null && !crashed)
                //System.err.println("Closest car of: " + id + " " + closest.id + " Follow distance: " + followDistance + "\n DistanceToNextCar: " + (closest.yPos-this.yPos));
		if(yVel > 0 && (!crashed)&&(closest != null)&&((closest.yPos - this.yPos) < followDistance)) {
                      //  System.err.println("Passed into acceleration logic");
			if(isExpectedCrash(closest)) {
                                System.err.println("Crash is impossible to avoid");
			}
				//Slows down at an acceleration that is somewhere between the minimum deceleration needed
				//to not crash, and the max acceleration of the car
				double minAccel = Math.max(0, getMinAccelNeededToNotCrash(closest));
				if(closest.yVel == 0) {
					yAccel = 0;
					yVel = 0;
				}
				else {
                                        if(closest.yPos-this.yPos < 10){
                                             yVel = 0;
                                             yAccel = 0;
                                        }
                                        else{
					     yAccel = -1 * minAccel; //-1 * Math.min(ACCEL_LIMIT, ((minAccel + ACCEL_LIMIT) / 2));
				        }
                                }
		}
		else if(yVel < INITIAL_SPEED) {
                        //System.err.println("Second section");
			//Speeds up to ideal speed if it's possible
			//if((INITIAL_SPEED - yVel)*(1 / timeInterval) < ACCEL_LIMIT) {
			//	yVel = INITIAL_SPEED;
			//}
			//otherwise, get as close to the ideal speed as we can
			//else {
                        yAccel = .5;
			//}
		}
		else if(crashed) {
                        //System.err.println("this shouldn't happen");
			yVel = 0;
			xVel = 0;
			yAccel = 0;
			xAccel = 0;
		}
	}*/
        public boolean laneChangeSafe(){
               double fakeX = (lane + laneChangeState) * laneWidth + .5 * laneWidth;
               double fakeY = yPos;
               for(Car i: road.carList){
                   if(i.id != id){
                       if(Math.abs(fakeX-i.xPos) < 12 && Math.abs(fakeY-i.yPos) < 100){
                           //System.err.println("Lane change was not safe becase: " + Math.abs(fakeX-i.xPos) + " " + Math.abs(fakeY-i.yPos));
                           return false;
                       }  
                   }
               }
               return true;
        }

	public void move(double timeInterval) {

		//make acceleration decisions
		//makeVelocityAndAccelerationDecisions(timeInterval);

                Car temp = getCarInFront();
		if (temp != null && temp.isSmart && this.isSmart)
		{
		    this.yAccel = temp.yAccel;
		    this.xVel = temp.xVel;
		    this.yVel = temp.yVel;
		    this.xAccel = temp.xAccel; 
		}
		else
		{
                    if(temp != null && (temp.yPos - yPos) < (100 + yVel) && yVel > 0 && temp.lane == lane){
                        yAccel = ACCEL_LIMIT * -1;
                    }                
                    else{
                        if(yVel < INITIAL_SPEED){
                           yAccel = ACCEL_LIMIT; 
                        }
                        else{
                           yAccel = 0;
                        }
                    }
		}
                xPos += .5 * xVel * timeInterval;
                yPos += .5 * yVel * timeInterval;
                
		xVel += xAccel * timeInterval;
                if(yAccel < 0 && yVel < yAccel*timeInterval){
                     yVel = 0;
                } 
                else{
		     yVel += yAccel * timeInterval;
                }
                xPos += .5 * xVel * timeInterval;
                yPos += .5 * yVel * timeInterval;
		
		if (!(temp != null &&temp.isSmart && this.isSmart)) {
		if (Math.abs(laneChangeState) == 1) {
                        //System.err.println("Attempting lane change " + id + " " + xPos + " " + yPos);

                        if(laneChangeSafe()){	
                             xVel += laneChangeState;
			     if (xPos > (lane + laneChangeState) * laneWidth + .5 * laneWidth) {
				lane+=laneChangeState;
				laneChangeState = 0;
			     }
                        }
                        else{
                             //System.err.println("No longer attempting lane change due to lack of room");

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

	}

	static boolean DEBUG = false;

	public String toString() {
		return id + "," + xPos + "," + yPos + "," + xSize + "," + ySize
				+ (DEBUG ? " lane: " + lane + " lane change: " + laneChangeState : "" + "," + isSmart);
	}

	public boolean isCollision(Car j) {
		boolean xOverlap = Math.abs(xPos - j.xPos) < (xSize + j.xSize) / 2.0;
		boolean yOverlap = Math.abs(yPos - j.yPos) < (ySize + j.ySize) / 2.0;
		if (xOverlap && yOverlap) {
                        if(!crashed){
			//System.out.println("CRASH," + id);
			crashed = true;
                        }
                        if(!j.crashed){
                        //System.out.println("CRASH,"+j.id);
                        j.crashed = true;
                        }
		}
		return xOverlap && yOverlap;
	}
}
