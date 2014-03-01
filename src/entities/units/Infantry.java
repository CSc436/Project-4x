package entities.units;

import control.Player;
import entities.UnitType;
import entities.stats.BaseStatsEnum;

public class Infantry extends Unit {

	public Infantry(Player p, int xco, int yco, int id) {
		super(p, BaseStatsEnum.FOOTMAN, UnitType.INFANTRY, xco, yco, id);
	}
}
