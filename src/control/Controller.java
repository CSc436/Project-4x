package control;

import java.util.ArrayList;
import java.util.List;

import com.server.MovingNumber;

import entities.buildings.Building;
import entities.gameboard.GameBoard;


public class Controller implements Runnable {
	List<Player> players;
	GameBoard map;
	MovingNumber number;
	
	public Controller() {
		players = new ArrayList<Player>();
		players.add(new Player("Bob", 500, 500, 500, 500, 500));
		map = new GameBoard(5, 5, 2);
		number = new MovingNumber(0.0,1.0);
	}

	@Override
	public void run() {
		int turnNum = 0;
		//call to timer thread
		while (turnNum < 20) {
			produceResources();
			agentDecision();
			unitInteraction();
			playerCommands();
		}
		
	}
	
	private void playerCommands() {
		// TODO Auto-generated method stub
		
	}

	private void unitInteraction() {
		// TODO Auto-generated method stub
		
	}

	private void agentDecision() {
		// TODO Auto-generated method stub
		
	}

	private void produceResources() {
		for(Player player : players) {
			for (Building building : player.getBuildings()) {
				building.gen
			}
		}
	}
	
	
}
