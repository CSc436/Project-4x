package control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.shared.Request;

import control.commands.Command;
import entities.gameboard.GameBoard;
import entities.buildings.Building;
import entities.buildings.ResourceBuilding;
import entities.gameboard.Tile;
import entities.units.Unit;
import entities.util.Point;

public class Controller implements Runnable {
	private GameModel model;
	private ConcurrentLinkedDeque<Command> commandQueue;
	private int turnWaitTime;// in ms

	public Controller() {
		model = new GameModel();
		commandQueue = new ConcurrentLinkedDeque<Command>();
		turnWaitTime = 100;
	}

	@Override
	public void run() {
		model.modelState();
		
		// actual game execution
		boolean gameRunning = true;
		while (gameRunning) {
			
			try {
				Thread.sleep(turnWaitTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(!commandQueue.isEmpty()) {
				Command comm = commandQueue.poll();
				comm.performCommand(model);
			}
			model.advanceTimeStep();
			// produceGameObjects
			agentDecision();
			unitInteraction();
			// When the timer on a unit in the production queue hits 0, add the
			// unit to the player's unit list.
			
			// TODO: change timestep to what we really want
			checkBuildingProductionQueue(10);

			gameRunning = playerCommands();

		}
	}

	private void checkBuildingProductionQueue(int timestep) {
		for (Player p : players) {
			Map<UUID, Building> bs = p.getGameObjects().getBuildings();
			Collection<Building> buildings = bs.values();
			for (Building b : buildings) {
				if (b.productionQueueEmpty())
					continue;
				b.advanceUnitProduction(timestep);
				Unit u = b.getProducedUnit();
				if (u != null) {
					p.getGameObjects().getUnits().put(u.getId(), u);
				}
			}
		}
	}

	private boolean playerCommands() {
		currentInstructions = sharedInstructions.dump();
		for (Command comm : currentInstructions) {

			switch (comm.getAction()) {

			case CREATE_BUILDING:

				List<Object> payload = comm.getPayload();

				int playerid = (int) payload.get(0);
				BuildingType type = (BuildingType) payload.get(1);
				Point bp = (Point) payload.get(2);

				Building b = Factory.buildBuilding(playerid, type, bp.x, bp.y);

				System.out.println("Create " + type + " at :" + "(" + bp.x
						+ "," + bp.y + ") for playerId : " + playerid);
				break;

			case CREATE_UNIT:

				break;

			}
		}
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
		for (Player player : players) {
			// for (Unit unit : player.getUnitQueue()) {
			// unit.
			// }
		}

	}

	private void agentDecision() {
		for (Player player : players) {
			// for (Agent agent : player.getAgents()) {
			// agent.makeDecision();
			// }
		}
	}

	private void produceResources() {
		for (Player player : players) {
			for (ResourceBuilding building : player.getGameObjects()
					.getResourceBuildings().values()) {

				building.generateResource();

			}
		}
	}

}
