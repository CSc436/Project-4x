package com.shared.model.units.pawns;

import java.util.UUID;

import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public class Dragoon extends Unit {

	public Dragoon(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.DRAGOON, BaseStatsEnum.DRAGOON.getStats(), UnitType.DRAGOON, xco, yco);
		// TODO Auto-generated constructor stub
	}


}
