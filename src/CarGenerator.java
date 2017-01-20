public class CarGenerator {
	double freq;
	Road road;
	double initialSpeedOfCar;
	double timeStep;
	public CarGenerator(Road road, double freq, double timeStep){
		this.freq = freq;
		this.road = road;
		this.timeStep = timeStep;
		initialSpeedOfCar = 88;
	}
	
	public Car getCar(int lane){
		if(Math.random() < freq * timeStep ){
			return new Car(road, lane, road.roadWidth, 88);
		}
		return null;
	}

}
