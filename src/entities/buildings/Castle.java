package entities.buildings;

import control.Player;
import entities.UnitType;
import entities.stats.BaseStatsEnum;

public class Castle extends Building {

	// main hub
	private int castleLevel;
	private int populationCap;
	private int influenceArea;

	public Castle(Player p, int xco, int yco, int idno) {
		/*
		 * 
		 * public Building(Player p, BaseStatsEnum baseStats, UnitType type, int
		 * xco, int yco) {
		 */

		super(p, BaseStatsEnum.CASTLE, UnitType.BUILDING, xco, yco, idno);
		castleLevel = 1;
		populationCap = 100; // Random number for now.
		influenceArea = 2; // a nxn radius from the row and column of its board
							// tile.
	}
}
