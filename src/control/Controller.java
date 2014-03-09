package control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;

import entities.buildings.Building;
import entities.buildings.ResourceBuilding;
import entities.gameboard.GameBoard;
import entities.gameboard.Tile;
import entities.units.Unit;
import entities.util.Point;

public class Controller implements Runnable {
	private ArrayList<Player> players;
	private GameBoard map;
	private PlayerCommands sharedInstructions;
	private Queue<Command> currentInstructions;
	private int turnWaitTime;// in ms
	private GameState gs;

	public Controller(PlayerCommands instructions, GameState gs) {
		players = new ArrayList<Player>();
		this.sharedInstructions = instructions;
		turnWaitTime = 1000;
		this.gs = gs;
	}

	@Override
	public void run() {
		System.out.println("Setup : ");
		currentInstructions = sharedInstructions.dump();

		for (Command comm : currentInstructions) {
			System.out.println(comm.getTarget());
			if (comm.getAction() != Actions.STARTUP_CREATE) {
				System.out.println("You suck for not using startup_create");
			}
			Iterator<Object> it = comm.getPayload().iterator();
			switch (comm.getTarget()) {
			case PLAYER:
				players.add(new Player((String) it.next(), (Integer) it.next()));
				break;
			case MAP:
				map = new GameBoard((Integer) it.next(), (Integer) it.next());
				break;
			default:
				System.out
						.println("You suck for screwing up the target in the command object");
				break;
			}
			// turnNum++;
		}

		gs.update(players, map);
		gameStatus();
		// actual game execution
		boolean gameRunning = true;
		while (gameRunning) {
			// gs.toString();
			try {
				Thread.sleep(turnWaitTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			produceResources();
			// produceGameObjects
			agentDecision();
			unitInteraction();
			// When the timer on a unit in the production queue hits 0, add the
			// unit to the player's unit list.
			checkBuildingProductionQueue(10);

			gameRunning = playerCommands();
			System.out.println("STILL RUNNING: " + gameRunning);
			System.out
					.println("STILL RUNNING: "
							+ players.get(0).getGameObjects().getBuildings()
									.toString());
			gs.update(players, map);
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
					p.getGameObjects().getUnits()
							.put(u.getId(), b.getProducedUnit());
				}
			}
		}
	}

	private void gameStatus() {
		System.out.println("Game State:");
		System.out.print("Players: ");
		for (Player player : players) {
			player.getAlias();
			System.out.print(player.getAlias() + ",");
		}
		System.out.println();
		System.out.println();
		for (Player player : players) {
			System.out.println(player.getAlias() + "'s Resources:");
			System.out.println(player.getResources().toString());
			System.out.println(player.getAlias() + "'s Units: ");
			for (Unit u : player.getGameObjects().getUnits().values()) {
				System.out.println(u.toString());
			}
			System.out.println(player.getAlias() + "'s Buildings: ");
			for (Building b : player.getGameObjects().getBuildings().values()) {
				System.out.println(b.toString());
			}
			System.out.println();
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

				map.placeBuildingAt(b, (int) Math.round(bp.x),
						(int) Math.round(bp.y));
				break;

			case CREATE_UNIT:

				payload = comm.getPayload();

				playerid = (int) payload.get(0);
				UnitType ut = (UnitType) payload.get(1);
				bp = (Point) payload.get(2);

				Unit u = Factory.buildUnit(playerid, ut, bp.x, bp.y);

				System.out.println("Create " + ut + " at :" + "(" + bp.x + ","
						+ bp.y + ") for playerId : " + playerid);

				// TODO : Place created unit in a building
				// TODO : Need selected unit implemented
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

	// for testing
	public GameBoard getGameBoard() {

		return map;
	}

}
