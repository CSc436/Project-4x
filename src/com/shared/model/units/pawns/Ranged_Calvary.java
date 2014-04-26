package com.shared.model.units.pawns;

import java.util.UUID;

import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;
public class Ranged_Calvary extends Unit{

	public Ranged_Calvary(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.RANGED_CALVARY, BaseStatsEnum.RANGED_CALVARY.getStats(), UnitType.RANGED_CALVARY, xco, yco);
		// TODO Auto-generated constructor stub
	}


}
