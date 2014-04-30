package com.shared.model.units.agents;

import com.shared.model.control.Player;
import com.shared.model.entities.GameObjectType;
import com.shared.model.gameboard.GameBoard;
import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.stats.UnitStats;
import com.shared.model.units.Agent;
import com.shared.model.units.UnitType;

public class Merchant extends Agent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8984570260940183249L;

	public Merchant(int id, int playerId, UnitStats new_stats, float xco, float yco, 
			int price, int number, int currency) {
		super(id, playerId, BaseStatsEnum.MERCHANT, new_stats, GameObjectType.UNIT, UnitType.MERCHANT,  xco,
				 yco);
		// TODO Auto-generated constructor stub
	}
	
	public Merchant() {}
	
	@Override
	public void update(Player p, GameBoard gb) {
		// TODO Auto-generated method stub
		
	}
	public int getPrestige(Player p){
		return p.prestige.get(this);
	}
	
	public void addPrestige(int i,Player p){
		p.prestige.put(this, this.getPrestige(p)+i);
	}
	
	public void buy(Goods goods, int number, Player p){
		if(this.getPrestige(p)<=50)
			System.out.println("The merchant doen't wanna sell it to you");
		else
		{
			p.goodsNumber.put(goods, p.getGoodsNumber(goods)+number);
			p.resources.setGold(p.resources.getGold() - number * goods.price);
		}
	}
}
