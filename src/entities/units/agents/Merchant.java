package entities.units.agents;

import java.util.UUID;

import control.Player;
import control.UnitType;
import entities.GameObjectType;
import entities.gameboard.GameBoard;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;
import entities.units.Agent;

public class Merchant extends Agent{

	Goods goods;
	public Merchant(UUID id, int playerId, UnitStats new_stats, float xco, float yco, 
			int price, int number, int currency) {
		super(id, playerId, BaseStatsEnum.MERCHANT, new_stats, GameObjectType.UNIT, UnitType.MERCHANT,  xco,
				 yco);
		// TODO Auto-generated constructor stub
	}
	
	public int getPrestige(){
		if(this.prestige<100)
			return 1;
		else if (this.prestige>1000)
			return 3;
		else return 2;
	}

	public void addPrestige(int i){
		this.prestige+=i;
	}
	
	public void buy(Goods goods, int numbe){
		if(this.prestige<=50)
			System.out.println("The merchant doen't wanna sell it to you");
		else
		{
			goods.number += numbe;
			Player.resources.Gold -= numbe * goods.price;
		}
	}

	@Override
	public void update(Player p, GameBoard gb) {
		// TODO Auto-generated method stub
		
	}
}
