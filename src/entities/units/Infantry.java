package entities.units;

import control.Player;
import entities.UnitType;
import entities.stats.BaseStatsEnum;

/*
 * Programmer:  Benjamin Deininger
 * 
 * Purpose:   This class extends units to test a tentative unit.
 */

public class Infantry extends Unit {

	public Infantry(Player p, int xco,
			int yco) {
		super(p, BaseStatsEnum.FOOTMAN, UnitType.INFANTRY, xco, yco);
	}
}
