package control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import entities.GameObject;
import entities.buildings.Building;
import entities.gameboard.GameBoard;
import entities.gameboard.Tile;
import entities.units.Unit;

public class GameModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3911016502183103473L;
	private ArrayList<Player> players;
	private GameBoard map;
	// Hashmap of all gameObjects
	private HashMap<UUID, GameObject> gameObjects;

	// simple test model start up.
	// A different constructor should be used for different
	public GameModel() {
		players = new ArrayList<Player>();
		players.add(new Player("Player 1", 1));
		players.add(new Player("Player 2", 2));
		
		gameObjects = new HashMap<UUID, GameObject>();

		map = new GameBoard(500, 500);

	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Player getPlayer(int playerId) {
		return players.get(playerId - 1);
	}

	public void advanceTimeStep() {
		produceResources();
		placeNewUnits();

	}

	private void produceResources() {
		// TODO Auto-generated method stub

	}

	private void placeNewUnits() {
		for (Player p : players) {
			for (Building b : p.getGameObjects().getBuildings().values()) {
				Unit potentialUnit = b.advanceUnitProduction(1); // Adjust time step in future 
				if (potentialUnit != null) {
					Tile t = map.getTileAt(0, 0);
					t.addUnit(potentialUnit);
					p.getGameObjects().addUnit(potentialUnit);
				}
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
}
