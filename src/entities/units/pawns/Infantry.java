package entities.units.pawns;

import java.util.HashMap;
import java.util.UUID;

import entities.stats.BaseStatsEnum;
import entities.units.Unit;
import entities.units.UnitType;

public class Infantry extends Unit {

	public Infantry(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.INFANTRY, BaseStatsEnum.INFANTRY.getStats(), UnitType.INFANTRY, xco, yco);
	}

}
