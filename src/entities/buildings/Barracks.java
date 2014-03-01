package entities.buildings;

import java.util.HashMap;

import com.fourx.buffs.UnitType;

import control.Player;
import entities.BaseStatsEnum;

public class Barracks extends Building {

	public Barracks(Player p, int xco, int yco) {
		super(p, BaseStatsEnum.CASTLE, UnitType.BUILDING, xco, yco);
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
