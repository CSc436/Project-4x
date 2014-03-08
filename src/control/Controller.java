package control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

import entities.buildings.Building;
import entities.buildings.ResourceBuilding;
import entities.gameboard.GameBoard;
import entities.units.Unit;

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
		System.out.println(currentInstructions.peek().getTarget());
		for (Command comm : currentInstructions) {
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
			gameRunning = playerCommands();
			gs.update(players, map);
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
			case STARTUP_CREATE:
		
				break;
			case CREATE:
				// players.get(0).createBuilding(3,3);
				break;
			}
		}
		return true;
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