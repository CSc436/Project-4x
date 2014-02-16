package entities.units;

import java.util.HashMap;

import com.fourx.buffs.UnitType;

import control.Player;
import entities.BaseStatsEnum;


/*
 * Programmer:  Benjamin Deininger
 * 
 * Purpose:   This class extends units to test a tentative unit.
 */

public class Infantry extends Unit {

	public Infantry(Player p, int x, int y) {
		super(p, BaseStatsEnum.FOOTMAN, UnitType.INFANTRY, x, y);
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
