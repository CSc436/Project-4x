package com.shared.model.units.pawns;

import java.util.UUID;

import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public class Cannon extends Unit{

	public Cannon(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.CANNON, BaseStatsEnum.CANNON.getStats(), UnitType.CANNON, xco, yco);
		// TODO Auto-generated constructor stub
	}


}
