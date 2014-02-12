package com.server;

public class MovingNumber {
	public double value;
	public double velocity; // Movement velocity, in units per second
	public double targetValue;
	
	public MovingNumber(double initValue, double vel) {
		value = initValue;
		targetValue = initValue;
		velocity = vel;
	}
	
	public void increment() {
		targetValue++;
	}
	
	public void decrement() {
		targetValue--;
	}
	
	public void simulateTimeStep(int timeStep) {
		if(targetValue > value) {
			value += velocity * timeStep / 1000.0;
			value = value > targetValue ? targetValue : value ; // Overshoot detection
		}
		if(targetValue < value) {
			value -= velocity * timeStep / 1000.0;
			value = value < targetValue ? targetValue : value ;
		}
	}
	
	public boolean canIncrement() {
		return value < 10.0;
	}
	
	public boolean canDecrement() {
		return value > -10.0;
	}
}
