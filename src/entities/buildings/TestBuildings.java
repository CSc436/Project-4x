package entities.buildings;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;


import control.Player;


public class TestBuildings {

	@Test
	public void testBrk() {
		// TODO Auto-generated method stub
			Player p = new Player("Xu", 111);
			Bank bk = new Bank(new UUID(0, 0),1,1,1, null);
			bk.gold=100000;
			p.resources.setGold(0);
//			bk.withdraw(1000);
			assertEquals(p.resources.getGold(),1000);
			assertEquals(bk.gold,99000);
//			bk.withdraw(11000);
			assertEquals(p.resources.getGold(),12000);
			assertEquals(bk.gold,88000);
//			assertEquals(bk.withdraw(100000),false);
			assertEquals(p.resources.getGold(),12000);
//			bk.deposit(5000);
			assertEquals(p.resources.getGold(),7000);
//			assertEquals(bk.deposit(50000),false);
			assertEquals(p.resources.getGold(),7000);
			
	}

}
