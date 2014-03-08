package com.shared;

import java.util.LinkedList;
import java.util.Queue;


public class Model implements Runnable {
	public MovingUnit unit;
	public boolean isBlack;
	public int timeStep = 100; // Number of milliseconds per simulation step
	public int turnNumber;
	public boolean continueSimulation = true;
	private long lastTime = System.currentTimeMillis();
	private Queue<Integer> runningAvgQueue = new LinkedList<Integer>();
	private int movingAverage = timeStep;
	private int numTimesSaved = 3; // Keep track of the last n cycle times to compute moving average
	
	public Model() {
		unit = new MovingUnit( 0.0, 0.0, 3.0 );
		isBlack = false;
		for(int i = 0; i < numTimesSaved; i++)
			runningAvgQueue.add(timeStep);
	}
	
	public void run() {
		/*
		turnNumber = -1; // Last fully completed turn
		long lastEndTime = System.currentTimeMillis();
		while(true) {
			long startTime = lastEndTime;

			
			while( System.currentTimeMillis() < startTime + timeStep || !continueSimulation );
			
			continueSimulation = true;
			turnNumber++;
			
			lastEndTime = lastEndTime + timeStep;
			System.out.println("Turn " + turnNumber + " finished");
		}
		*/
		lastTime = System.currentTimeMillis();
	}
	
	public void simulateFrame() {
		// Wait for minimum time to elapse, if required
		long currTime;
		do {
			currTime = System.currentTimeMillis();
		} while( currTime < lastTime + timeStep );
		
		updateRunningAverage( (int) (currTime - lastTime) );
		unit.simulateTimeStep( movingAverage );
		System.out.println("Server >>> "  + unit.position.getX() + " " + unit.position.getY());
		lastTime = currTime;
		turnNumber++;
	}

	private void updateRunningAverage(int newTime) {
		runningAvgQueue.add(newTime);
		if( runningAvgQueue.size() > numTimesSaved ) {
			movingAverage += (newTime - runningAvgQueue.remove()) / numTimesSaved;
		} else {
			movingAverage += (newTime - movingAverage) / numTimesSaved;
		}
		System.out.println(movingAverage);
	}

	public void setTarget( double x, double y ) {
		unit.setTarget(x, y);
	}
	
	public MovingUnit getUnit() {
		return unit;
	}
	
	public void continueSimulation() {
		continueSimulation = true;
	}
	
}
