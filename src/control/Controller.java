package control;

import java.util.List;
import java.util.Queue;

import java.util.Iterator;
import com.server.MovingNumber;

import entities.buildings.Building;
import entities.buildings.ResourceBuilding;
import entities.gameboard.GameBoard;
import entities.units.Unit;


public class Controller implements Runnable {
	private List<Player> players;
	private GameBoard map;
	private MovingNumber number;
	private PlayerCommands sharedInstructions;
	private Queue<Command> currentInstructions;
	private int turnWaitTime;//in ms
	
	
	public Controller(PlayerCommands instructions) {
		this.sharedInstructions = instructions;
		turnWaitTime = 1000;
	}

	@Override
	public void run() {
		//setup
		currentInstructions = sharedInstructions.dump();
		for (Command comm : currentInstructions) {
			if (comm.getAction() != Actions.STARTUP_CREATE) {
				System.out.println("You suck for not using startup_create");
			}
			Iterator<Object> it = comm.getPayload().iterator();
			switch (comm.getTarget()) {
			case PLAYER:
				players.add(new Player((String)it.next()));
				break;
			case MAP:
				map = new GameBoard((Integer)it.next(), (Integer)it.next(), players.size());
				break;
			default:
				System.out.println("You suck for screwing up the target in the command object");
				break;
			}
		}
		
		
		boolean gameRunning = true;
		while (gameRunning) {
			gameStatus();
			try {
				Thread.sleep(turnWaitTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			produceResources();
			agentDecision();
			unitInteraction();
			gameRunning = playerCommands();
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
		for (Player player : players) {
			System.out.println(player.getAlias()+ "'s Resources:");
			System.out.println(player.getResources().toString());
			System.out.println(player.getAlias() + "'s Units: ");
			for (Unit u : player.getUnits().getUnitList()) {
				System.out.println(u.toString());
			}
			System.out.println(player.getAlias() + "'s Buildings: ");
			for (Building b : player.getUnits().getBuildings()) {
				System.out.println(b.toString());
			}
		}
	}

	private boolean playerCommands() {
		currentInstructions = sharedInstructions.dump();
		for (Command comm : currentInstructions) {
			switch(comm.getAction()) {
			case STARTUP_CREATE:
			//	players.get(0).createUnit(0, 0);
				break;
			case CREATE:
			//	players.get(0).createBuilding(3,3);
				break;
			}
		}
		return true;
	}

	private void unitInteraction() {
		for(Player player : players) {
	//		for (Unit unit : player.getUnitQueue()) {
				//unit.
		//	}
		}
		
	}

	private void agentDecision() {
		for(Player player : players) {
	//		for (Agent agent : player.getAgents()) {
		//		agent.makeDecision();
	//		}
		}
	}

	private void produceResources() {
		for(Player player : players) {
			for (ResourceBuilding building : player.getUnits().getResourceBuildings()) {
				
				building.generateResource();
			}
		}
	}
	
	
}