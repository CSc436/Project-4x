package entities.units.pawns;

import java.util.HashMap;
import java.util.UUID;

import entities.stats.BaseStatsEnum;
import entities.units.Unit;
import entities.units.UnitType;

public class Rifleman extends Unit{

	public Rifleman(UUID id, int playerId,float xco, float yco) {
		super(id, playerId, BaseStatsEnum.RIFLEMAN, BaseStatsEnum.RIFLEMAN.getStats(), UnitType.RIFLEMAN, xco, yco);
		// TODO Auto-generated constructor stub
	}

}
