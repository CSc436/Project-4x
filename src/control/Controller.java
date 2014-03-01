package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.fourx.civilizations.PerfectCivilization;
import com.fourx.resources.Resources;
import com.server.MovingNumber;

import entities.buildings.Building;
import entities.buildings.ResourceBuilding;
import entities.gameboard.GameBoard;
import entities.units.Agent;
import entities.units.Unit;


public class Controller implements Runnable {
	private List<Player> players;
	private GameBoard map;
	private MovingNumber number;
	private PlayerCommands instructions;
	
	
	
	public Controller(PlayerCommands instructions) {
		this.instructions = instructions;
		//put this in controller constructor
		players = new ArrayList<Player>();
		players.add(new Player("Bob", new Resources(500, 500, 500, 500), new PerfectCivilization()));
		//put this in controller constructor
		map = new GameBoard(5, 5, 2);
		number = new MovingNumber(0.0,1.0);
	}

	@Override
	public void run() {
		boolean gameRunning = true;
		while (gameRunning) {
			gameStatus();
			try {
				Thread.sleep(2000);
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
		Queue<Integer> currentInstructions = instructions.dump();
		for (Integer i : currentInstructions) {
			switch(i) {
			case 1:
			//	players.get(0).createUnit(0, 0);
				break;
			case 2:
			//	players.get(0).createBuilding(3,3);
				break;
			case 3:
				return false;
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