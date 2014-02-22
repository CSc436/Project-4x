package entities.buildings;

import com.fourx.buffs.UnitType;

import control.Player;
import entities.BaseStatsEnum;

public class Barracks extends Building {

	public Barracks(Player p, int xco, int yco) {
		super(p, BaseStatsEnum.CASTLE, UnitType.BUILDING, xco, yco);
	}
}
