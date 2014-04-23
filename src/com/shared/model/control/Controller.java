package com.shared.model.control;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.shared.model.commands.Command;
import com.shared.model.gameboard.Tile;

public class Controller implements Runnable {

	private GameModel model;
	private ConcurrentLinkedDeque<Command> commandQueue;
	private int turnWaitTime;// in ms

	public Controller() {

		model = new GameModel();
		commandQueue = new ConcurrentLinkedDeque<Command>();
		turnWaitTime = 100;

	}

	/**
	 * run() -> actual game loop execution
	 */

	@Override
	public void run() {
		model.modelState();

		boolean gameRunning = true;
		while (gameRunning) {

			try {
				Thread.sleep(turnWaitTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while (!commandQueue.isEmpty()) {
				Command comm = commandQueue.poll();
				comm.performCommand(model);
			}
			model.advanceTimeStep();

		}
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

	public void addCommand(Command c) {
		commandQueue.add(c);
	}

	public GameModel getGameModel() {
		return model;
	}

}
