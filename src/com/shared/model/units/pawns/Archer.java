package com.shared.model.units.pawns;

import java.util.UUID;

import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public class Archer extends Unit{

	public Archer(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.ARCHER, BaseStatsEnum.ARCHER.getStats(), UnitType.ARCHER, xco, yco);
		// TODO Auto-generated constructor stub
	}


}
