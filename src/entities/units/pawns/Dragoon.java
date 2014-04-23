package entities.units.pawns;

import java.util.HashMap;
import java.util.UUID;

import entities.stats.BaseStatsEnum;
import entities.units.Unit;
import entities.units.UnitType;

public class Dragoon extends Unit {

	public Dragoon(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.DRAGOON, BaseStatsEnum.DRAGOON.getStats(), UnitType.DRAGOON, xco, yco);
		// TODO Auto-generated constructor stub
	}


}
