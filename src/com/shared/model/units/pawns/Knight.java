package com.shared.model.units.pawns;

import java.util.UUID;

import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public class Knight extends Unit{

	public Knight(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.KNIGHT, BaseStatsEnum.KNIGHT.getStats(), UnitType.KNIGHT, xco, yco);
		// TODO Auto-generated constructor stub
	}


}
