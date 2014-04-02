package entities.units.pawns;

import java.util.HashMap;
import java.util.UUID;

import control.UnitType;
import entities.buildings.Town_Hall;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;

public class Trade_Cart extends Unit{
	
	Town_Hall tradingCity;

	public Trade_Cart(UUID id, int playerId, float xco, float yco) {
		super(id, playerId, BaseStatsEnum.TRADE_CART, BaseStatsEnum.TRADE_CART.getStats(), UnitType.TRADE_CART, xco, yco);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, String> getActions() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setTradingCity(Town_Hall x) {
		tradingCity = x;
	}
	
	public Town_Hall getTradingCity() {
		return tradingCity;
	}

}
