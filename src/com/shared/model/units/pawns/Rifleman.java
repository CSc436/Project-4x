package com.shared.model.units.pawns;

import java.util.UUID;

import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public class Rifleman extends Unit{

	public Rifleman(UUID id, int playerId,float xco, float yco) {
		super(id, playerId, BaseStatsEnum.RIFLEMAN, BaseStatsEnum.RIFLEMAN.getStats(), UnitType.RIFLEMAN, xco, yco);
		// TODO Auto-generated constructor stub
	}

}
