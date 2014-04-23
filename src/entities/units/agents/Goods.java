package entities.units.agents;

import control.Player;

public enum Goods {

	silk,
	bullets;
	
	public static int price;
	public int number;
	
	public int getNumber(Player p){
		return p.goodsNumber.get(this);
	}
	
	public void addNumber(int number,Player p){
		p.goodsNumber.put(this, this.getNumber(p)+number);
	}
}
