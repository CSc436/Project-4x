package entities.units.pawns;

import java.util.HashMap;
import java.util.UUID;

import entities.GameObjectType;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;
import entities.units.Unit;
import entities.units.UnitType;

public class Medic extends Unit{

	public Medic(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.MEDIC, BaseStatsEnum.MEDIC.getStats(), UnitType.MEDIC, xco, yco);
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
