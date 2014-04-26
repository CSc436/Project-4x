package control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.Set;

import com.shared.PhysicsVector;

import entities.GameObject;
import entities.behaviors.Attackable;
import entities.behaviors.Attacker;
import entities.behaviors.Movable;
import entities.behaviors.Producer;
import entities.behaviors.ResourceGenerator;
import entities.buildings.Building;
import entities.gameboard.GameBoard;
import entities.units.Unit;

public class GameModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3911016502183103473L;
	private ArrayList<Player> players;
	private GameBoard map;
	// Hashmap of all gameObjects
	private HashMap<Integer, GameObject> gameObjects;
	private HashMap<Integer, Attacker> attackers;
	private HashMap<Integer, Movable> movables;
	private HashMap<Integer, Attackable> attackables;
	private HashMap<Integer, Producer> producers;
	private HashMap<Integer, ResourceGenerator> resourceGenerators;
	
	private int turnNumber = 0;

	// simple test model start up.
	// A different constructor should be used for different
	public GameModel() {
		players = new ArrayList<Player>();
		players.add(new Player("Player 1", 1));
		players.add(new Player("Player 2", 2));
		
		gameObjects = new HashMap<Integer, GameObject>();

		map = new GameBoard(500, 500);

	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Player getPlayer(int playerId) {
		return players.get(playerId - 1);
	}
	
	/**
	 * Move the simulate forward by a certain number of milliseconds
	 * @param timeStep - number of milliseconds to advance the simulation
	 */
	public void advanceTimeStep( int timeStep ) {
		turnNumber++;
		advanceProduction(timeStep);
		placeProducedUnits();
		produceResources(timeStep);
		moveObjects(timeStep);
		attackObjects(timeStep);
		updateHealth(timeStep);
	}

	private void produceResources( int timeStep ) {
		Set<Integer> keySet = resourceGenerators.keySet();
		for( int i : keySet ) {
			ResourceGenerator r = resourceGenerators.get(i);
			Player p = players.get(((GameObject) r).getPlayerID());
			p.getResources().receive(r.generateResources(timeStep));
		}
	}
	
	private void moveObjects( int timeStep ) {
		Set<Integer> keySet = movables.keySet();
		for( int i : keySet ) {
			Movable m = movables.get(i);
			m.simulateMovement(timeStep);
		}
	}
	
	private void attackObjects( int timeStep ) {
		
		Set<Integer> keySet = attackers.keySet();
		for( int i : keySet ) {
			Attacker a = attackers.get(i);
			a.simulateAttack(timeStep);
		}
		
	}
	
	private void advanceProduction( int timeStep ) {
		
		Set<Integer> keySet = producers.keySet();
		for( int i : keySet ) {
			Producer p = producers.get(i);
			p.simulateProduction(timeStep);
		}
		
	}
	
	private void updateHealth( int timeStep ) {
		Set<Integer> keySet = attackables.keySet();
		for( int i : keySet ) {
			Attackable a = attackables.get(i);
			a.simulateDamage(timeStep);
			if(a.isDead()) {
				removeFromAll((GameObject) a);
			}
		}
	}
	
	private void removeFromAll(GameObject o) {
		int id = o.getId();
		gameObjects.remove(id);
		attackers.remove(id);
		movables.remove(id);
		attackables.remove(id);
		resourceGenerators.remove(id);
		producers.remove(id);
		
		int playerID = o.getPlayerID();
		players.get(playerID).getGameObjects().removeGameObject(id);
	}

	private void placeProducedUnits() {
		
		Set<Integer> keySet = producers.keySet();
		for( int k : keySet ) {
			Producer p = producers.get(k);
			PhysicsVector position = ((GameObject) p).getPosition();
			Queue<UnitType> unitQueue = p.getProducedUnits();
			int x = (int) position.getX();
			int y = (int) position.getY();
			
			// Attempt to place units in a spiraling pattern around the building
			/*
			int dx = 0, dy = 0;
			while( !unitQueue.isEmpty() ) {
				
				
				if(dy == 0) {
					dy = -(dx + 1);
					dx = 0;
					if(dy > 0)
						for(int i = 0; i < dx + dy; i++) {
							
						}
					else
						for(int i = 0; i < dx + dy; i++) {
							
						}
				} else {
					dx = dy;
					dy = 0;
				}
				
				if(dy == 0)
				for(int i = 0; i < dx + dy; i++) {
					
				}
				if()
				x += dx;
				y += dy;
				
			}*/
			
			int playerID = ((GameObject) p).getPlayerID();
			for( UnitType ut : unitQueue ) {
				Unit u = Factory.buildUnit(players.get(playerID), playerID, ut, x, y+1);
				int unitID = u.getId();
				gameObjects.put(unitID, u);
				attackers.put(unitID, u);
				movables.put(unitID, u);
				attackables.put(unitID, u);
			}
		}
	}

	public void modelState() {
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

	public GameBoard getBoard() {

		return map;
	}

	public GameObject getGameObject(int entityID) {
		// TODO Auto-generated method stub
		return gameObjects.get(entityID);
	}
	
	public int getTurnNumber() {
		return turnNumber;
	}
}
