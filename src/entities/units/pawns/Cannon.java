package entities.units.pawns;

import java.util.HashMap;
import java.util.UUID;

import control.UnitType;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;

public class Cannon extends Unit{

	public Cannon(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.CANNON, BaseStatsEnum.CANNON.getStats(), UnitType.CANNON, xco, yco);
		// TODO Auto-generated constructor stub
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
