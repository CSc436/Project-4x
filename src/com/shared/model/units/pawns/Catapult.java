package com.shared.model.units.pawns;

import java.util.UUID;

import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public class Catapult extends Unit {

	public Catapult(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.CATAPULT, BaseStatsEnum.CATAPULT.getStats(), UnitType.CATAPULT, xco, yco);
		// TODO Auto-generated constructor stub
	}

}
