package entities.units;

import control.Player;
import entities.BaseStatsEnum;
import entities.UnitType;

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
