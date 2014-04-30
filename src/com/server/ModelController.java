package com.server;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import com.shared.model.Request;
import com.shared.model.SimpleGameModel;

public class ModelController implements Runnable {
	
	public SimpleGameModel game;
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
	public boolean isRunning;
	
	public ModelController() {
		game = new SimpleGameModel( 5 );
		for(int i = 0; i < numTimesSaved; i++)
			runningAvgQueue.add(timeStep);
	}
	
	public void run() {
		isRunning = true;
		lastTime = System.currentTimeMillis();
		stop = false;
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				simulateFrame();
			}
		};

		timer.scheduleAtFixedRate(task, 0, 10);

	}
	
	public void simulateFrame() {
		
		long currTime = System.currentTimeMillis();
		//if( !continueSimulation ) return;
		if( currTime < lastTime + timeStep || !continueSimulation ) return;
		
		continueSimulation = false;
		updateRunningAverage( (int) (currTime - lastTime) );
		lastTime = currTime;
		
		Queue<Request> frameRequestQueue = requestQueue;
		requestQueue = new LinkedList<Request>();
		
		while(!frameRequestQueue.isEmpty()) {
			Request r = frameRequestQueue.remove();
			r.executeOn(this.game);
		}
		
		game.simulateTimeStep( movingAverage );
		//System.out.println("Server >>> "  + unit.position.getX() + " " + unit.position.getY());
		turnNumber++;
		sendGame = true; // Game updated, ready to update clients
		
	}

	private void updateRunningAverage(int newTime) {
		runningAvgQueue.add(newTime);
		if( runningAvgQueue.size() > numTimesSaved ) {
			movingAverage += (newTime - runningAvgQueue.remove()) / numTimesSaved;
		} else {
			movingAverage += (newTime - movingAverage) / numTimesSaved;
		}
		//System.out.println(movingAverage);
	}
	
	public SimpleGameModel getGame() {
		return game;
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
