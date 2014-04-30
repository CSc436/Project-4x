package entities;
import static org.junit.Assert.*;

import org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.shared.model.control.Player;
import com.shared.model.resources.Resources;
import com.shared.model.stats.UnitStats;
import com.shared.model.units.Agent;

public class TestMerchant {

	@Test
	public void testgp(){
	/*	Merchant mch = new Merchant(new UUID(0, 0),111, new UnitStats(0, 0, 0, 0, 0, 0, 0,new Resources(0,0,0,0,0)), 0, 0, 0, 0, 0);
		General gr = new General(new UUID(0, 0),111, new UnitStats(0, 0, 0, 0, 0, 0, 0,new Resources(0,0,0,0,0)), 0, 0);
		Player p = new Player("Xu",111);
		Player q = new Player("Graham",222);
		p.prestige = new HashMap<Agent,Integer>();
		q.prestige = new HashMap<Agent,Integer>();
		p.goodsNumber = new HashMap<Goods,Integer>();
		q.goodsNumber = new HashMap<Goods,Integer>();
		p.prestige.put(gr, 0);
		p.prestige.put(mch, 0);
		q.prestige.put(gr, 0);
		q.prestige.put(mch, 0);
		gr.addPrestige(100, p);
		gr.addPrestige(1, q);
		mch.addPrestige(80, p);
		mch.addPrestige(104,q);
		assertEquals(gr.getPrestige(p),100);
		assertEquals(gr.getPrestige(q),1);
		assertEquals(mch.getPrestige(p),80);
		assertEquals(mch.getPrestige(q),104);
		Goods.silk.price=10;
		p.resources.setGold(5000);
		q.resources.setGold(3000);
		p.goodsNumber.put(Goods.silk, 13);
		q.goodsNumber.put(Goods.silk, 15);
		mch.buy(Goods.silk, 120, p);
		mch.buy(Goods.silk, 130, q);
		assertEquals(p.resources.getGold(),3800);
		assertEquals(q.resources.getGold(),1700);
		assertEquals(Goods.silk.getNumber(p),133);
		assertEquals(Goods.silk.getNumber(q),145);*/
	}
}
