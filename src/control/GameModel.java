package control;

import java.util.ArrayList;

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
		Building castle = Factory.buildBuilding(players.get(0), 1,
				BuildingType.CASTLE, 0, 0);
		players.get(0).getGameObjects().addBuilding(castle);
		map.placeBuildingAt(castle, 0, 0);

		castle = Factory.buildBuilding(players.get(1), 2, BuildingType.CASTLE,
				100, 100);
		players.get(1).getGameObjects().addBuilding(castle);
		map.placeBuildingAt(castle, 100, 100);
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
				Unit potentialUnit = b.advanceUnitProduction();
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
}
