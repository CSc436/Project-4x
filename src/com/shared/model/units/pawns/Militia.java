package com.shared.model.units.pawns;

import java.util.UUID;

import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public class Militia extends Unit{

	public Militia(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.MILITIA, BaseStatsEnum.MILITIA.getStats(), UnitType.MILITIA, xco, yco);
		// TODO Auto-generated constructor stub
	}

}
