package control;

import java.util.ArrayList;

import entities.buildings.Building;
import entities.buildings.ProductionBuilding;
import entities.buildings.ResearchBuilding;
import entities.buildings.ResourceBuilding;
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

		tickBuildings();

	}

	/*
	 * tickBuildings will iterate through a player's building list and execute
	 * timestep advances according to what building interfaces it uses.
	 */

	private void tickBuildings() {
		for (Player p : players) {
			for (Building b : p.getGameObjects().getBuildings().values()) {

				if (b instanceof ProductionBuilding) {

					Unit potentialUnit = ((ProductionBuilding) b)
							.advanceUnitProduction(1); // Adjust time step in
														// future
					if (potentialUnit != null) {
						Tile t = map.getTileAt(0, 0);
						t.addUnit(potentialUnit);
						p.getGameObjects().addUnit(potentialUnit);
					}

					if (b instanceof ResearchBuilding) {
						((ResearchBuilding) b).advanceResearchProduction(1);
					}
					if (b instanceof ResourceBuilding) {
						((ResourceBuilding) b).generateResource();
					}
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
