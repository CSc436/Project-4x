package entities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.shared.model.buildings.Bank;
import com.shared.model.buildings.Blacksmith;
import com.shared.model.buildings.BuildingType;
import com.shared.model.buildings.ResourceBuilding;
import com.shared.model.control.Player;
import com.shared.model.resources.Resources;
import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.stats.UnitStats;

public class TestBuildingInterfaces {

	@Test
	public void testBrk() {
		// TODO Auto-generated method stub
		Player p = new Player("Xu", 111);
		// Bank bk = new Bank(new UUID(0, 0), 1, 1, 1, null);
		Bank bk = new Bank(34, p.getId(), 5.0f, 5.0f, new Resources(100, 0, 0,
				0, 0));
		p.resources.setGold(0);
		// bk.withdraw(1000);

		assertEquals(bk.generateResource().getGold(), 100);
		
		/*
		 * assertEquals(p.resources.getGold(), 1000); assertEquals(bk.gold,
		 * 99000); // bk.withdraw(11000); assertEquals(p.resources.getGold(),
		 * 12000); assertEquals(bk.gold, 88000); //
		 * assertEquals(bk.withdraw(100000),false);
		 * assertEquals(p.resources.getGold(), 12000); // bk.deposit(5000);
		 * assertEquals(p.resources.getGold(), 7000); //
		 * assertEquals(bk.deposit(50000),false);
		 * assertEquals(p.resources.getGold(), 7000);
		 */
	}

	@Test
	public void testResearchBuildings() {
		Player p = new Player("Xu", 111);

		Blacksmith bs = new Blacksmith(64, p.getId(), 6f, 6f);
		assertEquals(4, bs.getTechnologyList().size());

	}

	@Test
	public void testResourceBuildings() {
		Player p = new Player("Xu", 111);

		/* TEST FARM */
		ResourceBuilding farm = new ResourceBuilding(80, p.getId(),
				BaseStatsEnum.FARM, new UnitStats(0, 0f, 2, 0f, 100f, 0f, 0f,
						new Resources(0, 0, 100, 0, 0)), BuildingType.FARM,
				7.0f, 7.0f, 1, 1, new Resources(0, 0, 100, 0, 0));
		assertEquals(0, p.getResources().getFood());
		assertEquals(100, farm.generateResource().getFood());

		//Those who wish to test anything other than a farm may read my code above as an example.
		
		/*farm.advanceResourceProduction();

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

		/*StoneMine sm = new StoneMine(new UUID(0, 0), 1, 1, 1);
		sm.setOwner(p2);

		assertEquals(0, p2.getResources().getStone());
		assertEquals(1000, sm.getBaseResources().getStone());

		sm.advanceResourceProduction();

		assertEquals(10, p2.getResources().getStone());
		assertEquals(990, sm.getBaseResources().getStone());

		/* TEST UNIVERSITY /

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

		/* TEST GOLD MINE /
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

		/* TEST LUMBERMILL /
		LumberMill lm = new LumberMill(new UUID(0, 0), 1, 1, 1);
		lm.setOwner(p);

		assertEquals(100, p.getResources().getFood());
		assertEquals(40, p.getResources().getGold());
		assertEquals(9960, gm.getBaseResources().getGold());

		lm.advanceResourceProduction();

		assertEquals(100, p.getResources().getFood());
		assertEquals(40, p.getResources().getGold());
		assertEquals(10, p.getResources().getWood());
		assertEquals(990, lm.getBaseResources().getWood());*/

	}

	@Test
	public void testMultipleInterfaces() {

		/*
		 * Player p = new Player("Xu", 111); Town_Hall th = new Town_Hall(new
		 * UUID(0, 0), 1, 1, 1, false); th.setOwner(p);
		 * 
		 * th.advanceResourceProduction(); th.advanceUnitProduction(1);
		 */

	}

}
