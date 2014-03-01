package entities.buildings;

import control.Player;
import entities.BaseStatsEnum;
import entities.UnitType;

public class Barracks extends Building {

	public Barracks(Player p, int xco, int yco) {
		super(p, BaseStatsEnum.CASTLE, UnitType.BUILDING, xco, yco);
	}
}
