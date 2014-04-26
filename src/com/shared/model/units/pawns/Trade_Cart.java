package com.shared.model.units.pawns;

import java.util.UUID;

import com.shared.model.buildings.Town_Hall;
import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public class Trade_Cart extends Unit{
	
	Town_Hall tradingCity;

	public Trade_Cart(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.TRADE_CART, BaseStatsEnum.TRADE_CART.getStats(), UnitType.TRADE_CART, xco, yco);
		// TODO Auto-generated constructor stub
	}

	
	public void setTradingCity(Town_Hall x) {
		tradingCity = x;
	}
	
	public Town_Hall getTradingCity() {
		return tradingCity;
	}

}
