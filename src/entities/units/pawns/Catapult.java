package entities.units.pawns;

import java.util.HashMap;
import java.util.UUID;

import entities.stats.BaseStatsEnum;
import entities.units.Unit;
import entities.units.UnitType;

public class Catapult extends Unit {

	public Catapult(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.CATAPULT, BaseStatsEnum.CATAPULT.getStats(), UnitType.CATAPULT, xco, yco);
		// TODO Auto-generated constructor stub
	}

}
