package entities.units.pawns;

import java.util.HashMap;
import java.util.UUID;

import control.UnitType;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;

public class Infantry extends Unit {

	public Infantry(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.INFANTRY, BaseStatsEnum.INFANTRY.getStats(), UnitType.INFANTRY, xco, yco);
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
