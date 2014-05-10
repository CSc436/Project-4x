package com.shared.model.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.shared.model.behaviors.Attackable;
import com.shared.model.behaviors.Attacker;
import com.shared.model.behaviors.Movable;
import com.shared.model.behaviors.Producer;
import com.shared.model.behaviors.ResourceGenerator;
import com.shared.model.buildings.Building;
import com.shared.model.commands.SendMessageCommand;
import com.shared.model.diplomacy.trading.TradeManager;
import com.shared.model.entities.GameObject;
import com.shared.model.gameboard.GameBoard;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;
import com.shared.utils.Coordinate;
import com.shared.utils.PhysicsVector;

public class GameModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3911016502183103473L;
	private HashMap<Integer, Player> players;
	private GameBoard map;
	// Hashmap of all gameObjects
	private HashMap<Integer, GameObject> gameObjects;
	private HashMap<Integer, Attacker> attackers;
	private HashMap<Integer, Movable> movables;
	private HashMap<Integer, Attackable> attackables;
	private HashMap<Integer, Producer> producers;
	private HashMap<Integer, ResourceGenerator> resourceGenerators;
	
	private LinkedList<GameObject> producedBuildings = new LinkedList<GameObject>();

	private int turnNumber = 0;
	private int nextPlayerID = 1;
	private TradeManager tradeManager;

	private ArrayList<SendMessageCommand> chatLog; 
	
	
	// simple test model start up.
	// A different constructor should be used for different
	public GameModel() {
		this(500);
	}
	
	/**
	 * if we don't actually initialize a list of players anymore, might as
	 * well have a new constructor....
	 * 
	 * @param width width of gameboard
	 */
	public GameModel(int width) {
		players = new HashMap<Integer,Player>();
		
		gameObjects = new HashMap<Integer, GameObject>();
		attackers = new HashMap<Integer, Attacker>();
		movables = new HashMap<Integer, Movable>();
		attackables = new HashMap<Integer, Attackable>();
		producers = new HashMap<Integer, Producer>();
		resourceGenerators = new HashMap<Integer, ResourceGenerator>();
		
		map = new GameBoard(width, width);
		
		chatLog = new ArrayList<SendMessageCommand>();
		
		/* Do Not Remove this or accidentally merge it away! */
		tradeManager = new TradeManager();
	}
	
	/**
	 * updates chat log with a new message
	 * @param message
	 */
	public void updateChatLog(SendMessageCommand message)
	{
		chatLog.add(message);
	}
	
	/**
	 * returns current chat log.
	 * @return
	 */
	public ArrayList<SendMessageCommand> getChatLog()
	{
		return chatLog; 
	}
	
	//@deprecated 
	/*public GameModel(ArrayList<Player> players, int width) {
		//this.players = players;
		
		gameObjects = new HashMap<Integer, GameObject>();
		attackers = new HashMap<Integer, Attacker>();
		movables = new HashMap<Integer, Movable>();
		attackables = new HashMap<Integer, Attackable>();
		producers = new HashMap<Integer, Producer>();
		resourceGenerators = new HashMap<Integer, ResourceGenerator>();
		
		map = new GameBoard(width, width);
	}*/
	
	public void addPlayer( String playerName ) {
		players.put(nextPlayerID, new Player(playerName, nextPlayerID++));
	}
	
	public HashMap<Integer, Player> getPlayers() {
		return players;
	}

	public Player getPlayer(int playerId) {
		return players.get((playerId +1)); // Client model is one off from gameModel, so add one....
	}
	
	/**
	 * Move the simulate forward by a certain number of milliseconds
	 * @param timeStep - number of milliseconds to advance the simulation
	 */
	public void advanceTimeStep( int timeStep ) {
		turnNumber++;
		advanceProduction(timeStep);
		placeNewObjects();
		produceResources(timeStep);
		moveObjects(timeStep);
		attackObjects(timeStep);
		updateHealth(timeStep);
	}

	private void produceResources( int timeStep ) {
		Set<Integer> keySet = resourceGenerators.keySet();
		for( int i : keySet ) {
			ResourceGenerator r = resourceGenerators.get(i);
			Player p = getPlayer( ((GameObject) r).getPlayerID() );
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
		LinkedList<Integer> dead = new LinkedList<Integer>();
		for( int i : keySet ) {
			Attackable a = attackables.get(i);
			a.simulateDamage(timeStep);
			if(a.isDead()) {
				System.out.println("Object with ID " + i + " is dead!");
				dead.add(i);
			}
		}
		for( int i : dead )
			removeFromAll(gameObjects.get(i));
	}
	
	private void removeFromAll(GameObject o) {
		int id = o.getId();
		gameObjects.remove(id);
		attackers.remove(id);
		movables.remove(id);
		attackables.remove(id);
		resourceGenerators.remove(id);
		producers.remove(id);
		
		//int playerID = o.getPlayerID();
		//getPlayer( playerID ).getGameObjects().removeGameObject(id);
	}

	private void placeNewObjects() {
		
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
				Unit u = Factory.buildUnit(getPlayer(playerID), playerID, ut, x, y+1);
				int unitID = u.getId();
				gameObjects.put(unitID, u);
				attackers.put(unitID, u);
				movables.put(unitID, u);
				attackables.put(unitID, u);
			}
		}
		for( GameObject o : producedBuildings ) {
			int id = o.getId();
			gameObjects.put(id, o);
			movables.put(id, o);
			attackables.put(id, o);
			if(o instanceof Producer) producers.put(id, (Producer) o);
			if(o instanceof ResourceGenerator) resourceGenerators.put(id, (ResourceGenerator) o);
		}
		producedBuildings.clear();
	}

	public void modelState() {
		
		System.out.println("Game State:");
		System.out.print("Players: ");
		for (Player player : players.values()) {
			player.getAlias();
			System.out.print(player.getAlias() + ",");
		}
		System.out.println();
		System.out.println();
		for (Player player : players.values()) {
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

	/**
	 * Line-of-sight: used to obtain a listing of all objects visible to this
	 * object. Standards for visibility are defined within this method to allow
	 * for trivial changes, such as sight range, and may eventually be defined
	 * elsewhere; for the time being that problem was too subtle and tricky.
	 * 
	 * @param viewingObject
	 *            The object (unit, building) which sees other buildings/units
	 *            (or doesn't).
	 * @return A list containing references to the objects which are within
	 *         visibility of viewingObject. This list will contain viewingObject
	 *         unless it has been modified to use .equals (must be supported)
	 *         AND IFF viewingObject != sameObject.
	 */
	public ArrayList<GameObject> getVisibleUnits(GameObject viewingObject) {
		// Change this to change what can be visible.
		int sightRange = 10;

		// This method first determines all units which are within a large
		// square
		// containing the viewingObject at its center, then refines this list
		// based
		// on real distance.
		ArrayList<GameObject> visibles = new ArrayList<GameObject>();
		ArrayList<GameObject> inSquare = new ArrayList<GameObject>();

		int baseX = GameBoard.getCoordEquivalent((float) viewingObject.getPosition().getX());
		int baseY = GameBoard.getCoordEquivalent((float) viewingObject.getPosition().getY());

		// Define the square with viewingObject at its center.
		int minX = baseX - sightRange;
		int maxX = baseX + sightRange;
		int minY = baseY - sightRange;
		int maxY = baseY + sightRange;

		// Retrieve all GameObjects within the square.
		for (Player p : players.values()) {
			Map<Integer, GameObject> goMap = p.getGameObjects().getGameObjects();
			for (Integer u : goMap.keySet()) {
				GameObject go = goMap.get(u);
				int goX = GameBoard.getCoordEquivalent((float) go.getPosition().getX());
				int goY = GameBoard.getCoordEquivalent((float) go.getPosition().getY());

				boolean isInSquareX = (minX <= goX) && (goX <= maxX);
				boolean isInSquareY = (minY <= goY) && (goY <= maxY);

				if (isInSquareX && isInSquareY)
					inSquare.add(go);
			}
		}

		// Remove self from the list.
		for (GameObject go : inSquare)
			if (go == viewingObject)
				inSquare.remove(go);

		// Iterate over the game objects in the square and using pythagorean
		// distance calculations, see if they're visible based on float coords.
		for (GameObject go : inSquare) {
			double dist = go.getPosition().sub(viewingObject.getPosition()).magnitude();

			if (dist <= sightRange)
				visibles.add(go);
		}

		// Return the final list.
		return visibles;
	}
	
	public TradeManager getTradeManager() {
		return tradeManager;
	}
	
	public HashMap<Integer,GameObject> getGameObjects() {
		return gameObjects;
	}
	
	public LinkedList<GameObject> getProducedBuildings() {
		return producedBuildings;
	}
	
	public void createUnit( UnitType ut, int pn, Coordinate c ) {
		Unit u = Factory.buildUnit(players.get(pn), pn, ut, c.fx(), c.fy());
		int unitID = u.getId();
		gameObjects.put(unitID, u);
		attackers.put(unitID, u);
		movables.put(unitID, u);
		attackables.put(unitID, u);
		if(u != null) {
			System.out.println(ut.name() + " created with ID " + u.getId());
		}
	}
	
}
