package com.shared;

import java.util.LinkedList;
import java.util.Queue;


public class Model implements Runnable {
	public MovingUnit unit;
	public boolean isBlack;
	public int timeStep = 200; // Number of milliseconds per simulation step
	public int turnNumber = 0;
	public boolean continueSimulation = true;
	private long lastTime = System.currentTimeMillis();
	private Queue<Integer> runningAvgQueue = new LinkedList<Integer>();
	private int movingAverage = timeStep;
	private int numTimesSaved = 3; // Keep track of the last n cycle times to compute moving average
	private Queue<Request> requestQueue = new LinkedList<Request>();
	private boolean stop = false;
	private boolean sendGame = false;
	
	public Model() {
		unit = new MovingUnit( 0.0, 0.0, 3.0 );
		isBlack = false;
		for(int i = 0; i < numTimesSaved; i++)
			runningAvgQueue.add(timeStep);
	}
	
	public void run() {
		lastTime = System.currentTimeMillis();
		stop = false;
		while(!stop) {
			simulateFrame();
		}
	}
	
	public void simulateFrame() {
		Queue<Request> frameRequestQueue = requestQueue;
		requestQueue = new LinkedList<Request>();
		
		while(!frameRequestQueue.isEmpty()) {
			Request r = frameRequestQueue.remove();
			r.executeOn(this);
		}
		
		unit.simulateTimeStep( movingAverage );
		//System.out.println("Server >>> "  + unit.position.getX() + " " + unit.position.getY());
		turnNumber++;
		sendGame = true; // Game updated, ready to update clients
		// Wait for minimum time to elapse and wait for clients to confirm receipt of game state
		long currTime;
		do {
			currTime = System.currentTimeMillis();
			//System.out.println(">>> Waiting for clients...");
			System.out.println( (currTime < lastTime + timeStep) + " " + !continueSimulation );
			
			while( System.currentTimeMillis() < currTime + 30) {
				continue;
			}
			
		} while( currTime < lastTime + timeStep || !continueSimulation );
		
		continueSimulation = false;
		updateRunningAverage( (int) (currTime - lastTime) );
		lastTime = currTime;
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
		System.out.println(">>> Continuing simulation, turn " + turnNumber + " complete.");
		continueSimulation = true;
		sendGame = false;
	}
	
	public void queueRequest( Request r ) {
		requestQueue.add(r);
	}
	
	public void stop() {
		stop = true;
	}
	
	public boolean sendingGame() {
		return sendGame;
	}
	
}
