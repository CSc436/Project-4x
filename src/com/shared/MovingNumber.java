package com.shared;

import java.io.Serializable;

public class MovingNumber implements Serializable {
	public double value;
	public double velocity; // Movement velocity, in units per second
	public double targetValue;
	public int turnNumber;
	public long lastRequestTime;
	
	public MovingNumber() {
		value = 0;
		targetValue = 0;
		velocity = 1;
		turnNumber = 0;
	}
	
	public MovingNumber(double initValue, double vel) {
		value = initValue;
		targetValue = initValue;
		velocity = vel;
		turnNumber = 0;
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
		
		turnNumber++;
		System.out.println(value + " " + turnNumber);
	}
	
	public boolean canIncrement() {
		return targetValue < 10.0;
	}
	
	public boolean canDecrement() {
		return targetValue > -10.0;
	}
	
	public void setRequestTime(long reqTime) {
		lastRequestTime = reqTime;
	}
	
	public long getRequestTime() {
		return lastRequestTime;
	}
	
	public MovingNumber clone() {
		MovingNumber n = new MovingNumber();
		n.value = value;
		n.velocity = velocity;
		n.targetValue = targetValue;
		n.turnNumber = turnNumber;
		n.lastRequestTime = lastRequestTime;
		return n;
	}
}
