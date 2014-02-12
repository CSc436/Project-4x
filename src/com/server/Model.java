package com.server;

public class Model implements Runnable {
	public MovingNumber number;
	public boolean isBlack;
	public int timeStep = 200; // Number of milliseconds per simulation step
	
	public Model() {
		number = new MovingNumber(0.0,1.0);
		isBlack = false;
	}
	
	public void run() {
		long lastEndTime = System.currentTimeMillis();
		while(true) {
			long startTime = lastEndTime;
			
			simulateFrame();
			
			while(System.currentTimeMillis() < startTime + timeStep);
		}
		
	}
	
	public void simulateFrame() {
		number.simulateTimeStep(timeStep);
	}

	public void increment() {
		number.increment();
	}

	public boolean canIncrement() {
		return number.canIncrement();
	}
	
	public boolean canDecrement() {
		return number.canDecrement();
	}

	public void decrement() {
		number.decrement();;
	}
	
	public MovingNumber getNumber() {
		return number;
	}
}
