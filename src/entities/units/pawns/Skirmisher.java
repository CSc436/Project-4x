package entities.units.pawns;

import java.util.HashMap;
import java.util.UUID;

import entities.stats.BaseStatsEnum;
import entities.units.Unit;
import entities.units.UnitType;

public class Skirmisher extends Unit{

	public Skirmisher(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.SKIRMISHER, BaseStatsEnum.SKIRMISHER.getStats(), UnitType.SKIRMISHER, xco, yco);
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
