package entities.units.agents;
import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import control.Player;
import entities.stats.UnitStats;

public class TestMerchant {

	@Test
	public void testgp(){
		Merchant mch = new Merchant(new UUID(0, 0),111, new UnitStats(0, 0, 0, 0, 0, 0, 0), 0, 0, 0, 0, 0);
		Player p = new Player("Xu",111);
		mch.prestige = 70;
		mch.goods.silk.price=10;
		p.resources.Gold = 5000;
		p.goods.silk.number = 13;
		mch.buy(Goods.silk, 120);
		assertEquals(p.resources.Gold,3800);
		assertEquals(p.getGoodsNumber(Goods.silk),133);
	}
}
