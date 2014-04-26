package com.shared.model.units.pawns;

import java.util.UUID;

import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public class Infantry extends Unit {

	public Infantry(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.INFANTRY, BaseStatsEnum.INFANTRY.getStats(), UnitType.INFANTRY, xco, yco);
	}

}
