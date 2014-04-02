package entities.units.pawns;

import java.util.HashMap;
import java.util.UUID;

import control.UnitType;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;

public class Ranged_Calvary extends Unit{

	public Ranged_Calvary(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.RANGED_CALVARY, BaseStatsEnum.RANGED_CALVARY.getStats(), UnitType.RANGED_CALVARY, xco, yco);
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
