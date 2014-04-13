package entities;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import control.Player;
import entities.buildings.Bank;
import entities.buildings.Farm;
import entities.buildings.GoldMine;
import entities.buildings.LumberMill;
import entities.buildings.StoneMine;
import entities.buildings.Town_Hall;
import entities.buildings.University;
import entities.resources.Resources;

public class TestBuildingInterfaces {

	@Test
	public void testBrk() {
		// TODO Auto-generated method stub
		Player p = new Player("Xu", 111);
		Bank bk = new Bank(new UUID(0, 0), 1, 1, 1, null);
		bk.gold = 100000;
		p.resources.setGold(0);
		// bk.withdraw(1000);
		assertEquals(p.resources.getGold(), 1000);
		assertEquals(bk.gold, 99000);
		// bk.withdraw(11000);
		assertEquals(p.resources.getGold(), 12000);
		assertEquals(bk.gold, 88000);
		// assertEquals(bk.withdraw(100000),false);
		assertEquals(p.resources.getGold(), 12000);
		// bk.deposit(5000);
		assertEquals(p.resources.getGold(), 7000);
		// assertEquals(bk.deposit(50000),false);
		assertEquals(p.resources.getGold(), 7000);

	}

	@Test
	public void testResourceBuildings() {
		Player p = new Player("Xu", 111);
		Player p2 = new Player("MEATWAD", 69);

		/* TEST FARM */
		Farm farm = new Farm(new UUID(0, 0), 1, 1, 1);
		farm.setOwner(p);
		assertEquals(0, p.getResources().getFood());
		assertEquals(100, farm.getBaseResources().getFood());

		farm.advanceResourceProduction();

		assertEquals(10, p.getResources().getFood());
		assertEquals(90, farm.getBaseResources().getFood());

		farm.advanceResourceProduction();

		assertEquals(20, p.getResources().getFood());
		assertEquals(80, farm.getBaseResources().getFood());

		farm.advanceResourceProduction();
		farm.advanceResourceProduction();
		farm.advanceResourceProduction();
		farm.advanceResourceProduction();
		farm.advanceResourceProduction();
		farm.advanceResourceProduction();
		farm.advanceResourceProduction();
		farm.advanceResourceProduction();

		/* TEST STONE MINE */

		StoneMine sm = new StoneMine(new UUID(0, 0), 1, 1, 1);
		sm.setOwner(p2);

		assertEquals(0, p2.getResources().getStone());
		assertEquals(1000, sm.getBaseResources().getStone());

		sm.advanceResourceProduction();

		assertEquals(10, p2.getResources().getStone());
		assertEquals(990, sm.getBaseResources().getStone());

		/* TEST UNIVERSITY */

		University uv = new University(new UUID(0, 0), 1, 1, 1);
		uv.setOwner(p2);

		assertEquals(10, p2.getResources().getStone());
		assertEquals(0, p2.getResources().getResearchPts());
		assertEquals(990, sm.getBaseResources().getStone());
		assertEquals(5000, uv.getBaseResources().getResearchPts());

		uv.advanceResourceProduction();
		assertEquals(20, p2.getResources().getResearchPts());
		assertEquals(10, p2.getResources().getStone());
		assertEquals(990, sm.getBaseResources().getStone());
		assertEquals(4980, uv.getBaseResources().getResearchPts());

		assertEquals(100, p.getResources().getFood());
		assertEquals(0, farm.getBaseResources().getFood());

		farm.advanceResourceProduction();

		assertEquals(100, p.getResources().getFood());
		assertEquals(0, farm.getBaseResources().getFood());

		/* TEST GOLD MINE */
		GoldMine gm = new GoldMine(new UUID(0, 0), 1, 1, 1);
		gm.setOwner(p);
		assertEquals(100, p.getResources().getFood());
		assertEquals(0, p.getResources().getGold());
		assertEquals(10000, gm.getBaseResources().getGold());

		gm.advanceResourceProduction();

		assertEquals(100, p.getResources().getFood());
		assertEquals(20, p.getResources().getGold());
		assertEquals(9980, gm.getBaseResources().getGold());

		gm.advanceResourceProduction();

		assertEquals(100, p.getResources().getFood());
		assertEquals(40, p.getResources().getGold());
		assertEquals(9960, gm.getBaseResources().getGold());

		/* TEST LUMBERMILL */
		LumberMill lm = new LumberMill(new UUID(0, 0), 1, 1, 1);
		lm.setOwner(p);

		assertEquals(100, p.getResources().getFood());
		assertEquals(40, p.getResources().getGold());
		assertEquals(9960, gm.getBaseResources().getGold());

		lm.advanceResourceProduction();

		assertEquals(100, p.getResources().getFood());
		assertEquals(40, p.getResources().getGold());
		assertEquals(10, p.getResources().getWood());
		assertEquals(990, lm.getBaseResources().getWood());

	}

	@Test
	public void testMultipleInterfaces() {

		Player p = new Player("Xu", 111);
		Town_Hall th = new Town_Hall(new UUID(0, 0), 1, 1, 1, false);
		th.setOwner(p);
		
		th.advanceResourceProduction();
		th.advanceUnitProduction(1);

	}

}
