package com.shared.model.control;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import com.shared.model.commands.Command;
import com.shared.model.gameboard.Tile;

public class Controller implements Runnable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5520954150886924424L;
	private GameModel model;
	private CommandPacket commandPacket;
	private CommandPacket packetToSend;
	
	public int timeStep; // Number of milliseconds per simulation step
	public int turnNumber = 0;
	public boolean continueSimulation = true;
	private long lastTime = System.currentTimeMillis();
	private Queue<Integer> runningAvgQueue = new LinkedList<Integer>();
	private int movingAverage = timeStep;
	private int numTimesSaved = 3; // Keep track of the last n cycle times to compute moving average
	private boolean packetReady = false;
	private boolean stop = false;
	public boolean isRunning;
	private boolean debug = true;
	
	/**************************************************************/
	
	public Controller() {
		model = new GameModel();
		commandPacket = new CommandPacket();
		packetToSend = commandPacket;
		timeStep = 200;
		for(int i = 0; i < numTimesSaved; i++)
			runningAvgQueue.add(timeStep);
	}
	
	public Controller(GameModel m) {
		model = m;
		commandPacket = new CommandPacket();
		packetToSend = commandPacket;
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
	
	public synchronized void simulateFrame() {
		
		long currTime = System.currentTimeMillis();
		if( currTime < lastTime + timeStep || !continueSimulation ) return;
		
		continueSimulation = false;
		updateRunningAverage( (int) (currTime - lastTime) );
		lastTime = currTime;
		
		packetToSend = commandPacket;
		commandPacket = new CommandPacket();
		packetToSend.executeOn(model);
		
		if(debug) System.out.println(packetToSend.getCommandQueue().size() + " commands processed this turn");
		
		model.advanceTimeStep( movingAverage );
		packetToSend.setTime( movingAverage );
		packetToSend.setTurnNumber(turnNumber);
		turnNumber++;
		packetReady = true; // Game updated, ready to update clients
		
	}
	
	public synchronized void continueSimulation() {
		if(!continueSimulation) {
			if(debug) System.out.println(">>> Continuing simulation, turn " + turnNumber + " complete.");
			continueSimulation = true;
			packetReady = false;
		}
	}
	
	public void queueCommand( Command c ) {
		commandPacket.addCommand(c);
	}
	
	public boolean isPacketReady() {
		return packetReady;
	}
	
	public void stop() {
		stop = true;
	}
	
	/**************************************************************/

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

	public void addCommand(Command c) {
		commandPacket.addCommand(c);
	}

	public GameModel getGameModel() {
		return model;
	}
	
	public CommandPacket getCommandPacket() {
		return commandPacket;
	}
	
	private void updateRunningAverage(int newTime) {
		if(debug) System.out.println("Cycle time: " + newTime + " ms");
		runningAvgQueue.add(newTime);
		if( runningAvgQueue.size() > numTimesSaved )
			movingAverage += (newTime - runningAvgQueue.remove()) / numTimesSaved;
		else
			movingAverage += (newTime - movingAverage) / numTimesSaved;
	}

	public synchronized void sendCommands(Queue<Command> commandQueue) {
		commandPacket.addCommands(commandQueue);
	}
	
	public CommandPacket getPacketToSend() {
		return packetToSend;
	}

	public void setPacketToSend(CommandPacket packetToSend) {
		this.packetToSend = packetToSend;
	}

}
