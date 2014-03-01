package entities.buildings;

import java.util.HashMap;

import com.fourx.buffs.UnitType;

import control.Player;
import entities.BaseStatsEnum;

public class Castle extends Building {

	// main hub
	private int castleLevel;
	private int populationCap;
	private int influenceArea;

	public Castle(Player p, int xco, int yco) {
		/*
		 * 
		 * public Building(Player p, BaseStatsEnum baseStats, UnitType type, int
		 * xco, int yco) {
		 */

		super(p, BaseStatsEnum.CASTLE, UnitType.BUILDING, xco, yco);
		castleLevel = 1;
		populationCap = 100; // Random number for now.
		influenceArea = 2; // a nxn radius from the row and column of its board
							// tile.
	}

	@Override
	protected void setActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, String> getActions() {
		// TODO Auto-generated method stub
		return null;
	}
}
