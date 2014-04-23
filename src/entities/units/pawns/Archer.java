package entities.units.pawns;

import java.util.HashMap;
import java.util.UUID;

import entities.stats.BaseStatsEnum;
import entities.units.Unit;
import entities.units.UnitType;

public class Archer extends Unit{

	public Archer(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.ARCHER, BaseStatsEnum.ARCHER.getStats(), UnitType.ARCHER, xco, yco);
		// TODO Auto-generated constructor stub
	}


}
