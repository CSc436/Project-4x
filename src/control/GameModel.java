package control;

import java.util.ArrayList;

import entities.GameObject;
import entities.buildings.Building;
import entities.gameboard.GameBoard;
import entities.gameboard.Tile;
import entities.units.Unit;

public class GameModel {
	private ArrayList<Player> players;
	private GameBoard map;

	// simple test model start up.
	// A different constructor should be used for different
	public GameModel() {
		players = new ArrayList<Player>();
		players.add(new Player("Player 1", 1));
		players.add(new Player("Player 2", 2));

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
	
	/**
	 * Line-of-sight: used to obtain a listing of all objects visible to this object.
	 * Standards for visibility are defined within this method to allow for trivial changes, such as sight range,
	 * and may eventually be defined elsewhere; for the time being that problem was too subtle and tricky.
	 * @param viewingObject The object (unit, building) which sees other buildings/units (or doesn't).
	 * @return A list containing references to the objects which are within visibility of viewingObject. This list will contain
	 * 			viewingObject unless it has been modified to use .equals (must be supported) AND IFF viewingObject != sameObject.
	 */
	public ArrayList<GameObject> getVisibleUnits(GameObject viewingObject) {
		//Change these to change what can be visible.
		int sightRange = 10;
		
		ArrayList<GameObject> visibles = new ArrayList<GameObject>();
		ArrayList<GameObject> inSquare = new ArrayList<GameObject>();
		
		int baseX = GameBoard.getCoordEquivalent(viewingObject.getX());
		int baseY = GameBoard.getCoordEquivalent(viewingObject.getY());
		
		int minX = baseX - sightRange;
		int maxX = baseX + sightRange;
		int minY = baseY - sightRange;
		int maxY = baseY + sightRange;
		
		
		
	}
}
