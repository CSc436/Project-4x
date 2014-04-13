package control;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import control.commands.Command;
import entities.gameboard.Tile;

public class Controller implements Runnable {

	private GameModel model;
	private Queue<Command> commandQueue = new LinkedList<Command>();
	
	public int timeStep; // Number of milliseconds per simulation step
	public int turnNumber = 0;
	public boolean continueSimulation = true;
	private long lastTime = System.currentTimeMillis();
	private Queue<Integer> runningAvgQueue = new LinkedList<Integer>();
	private int movingAverage = timeStep;
	private int numTimesSaved = 3; // Keep track of the last n cycle times to compute moving average
	private boolean stop = false;
	private boolean sendGame = false;
	public boolean isRunning;
	
	/**************************************************************/
	
	public Controller() {
		model = new GameModel();
		timeStep = 200;
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
		
		Queue<Command> frameCommandQueue = commandQueue;
		commandQueue = new LinkedList<Command>();
		
		while(!frameCommandQueue.isEmpty()) {
			Command c = frameCommandQueue.remove();
			c.performCommand(this.model);
		}
		
		model.advanceTimeStep( movingAverage );
		//System.out.println("Server >>> "  + unit.position.getX() + " " + unit.position.getY());
		turnNumber++;
		sendGame = true; // Game updated, ready to update clients
		
	}
	
	public void continueSimulation() {
		//System.out.println(">>> Continuing simulation, turn " + turnNumber + " complete.");
		continueSimulation = true;
		sendGame = false;
	}
	
	public void queueCommand( Command c ) {
		commandQueue.add(c);
	}
	
	public boolean sendingGame() {
		return sendGame;
	}
	
	public void stop() {
		stop = true;
	}
	
	/**************************************************************/

	private void checkBuildingProductionQueue(int timestep) {

	}

	private boolean playerCommands() {

		return true;
	}

	/*
	 * TODO implement - or move somewhere better. pathFinding() Description:
	 * General pathfinding algorithm for units, somehow need to view the map.
	 * uses A*.
	 * 
	 * since in controller both have access to map and to
	 */
	public Queue<Tile> pathFinding() {
		return null;
	}

	private void unitInteraction() {

	}

	private void agentDecision() {

	}

	private void produceResources() {

	}

	public void addCommand(Command c) {
		commandQueue.add(c);
	}

	public GameModel getGameModel() {
		return model;
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

}
