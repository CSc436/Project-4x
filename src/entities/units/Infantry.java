package entities.units;

import com.fourx.buffs.UnitType;
import control.Player;
import entities.BaseStatsEnum;

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
