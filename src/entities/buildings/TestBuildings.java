package entities.buildings;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import control.BuildingType;
import control.Player;
import entities.GameObjectType;
import entities.PerfectCivilization;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;

public class TestBuildings {

	@Test
	public void testBrk() {
		// TODO Auto-generated method stub
			Player p = new Player("Xu", 111);
			Bank bk = new Bank(new UUID(0, 0),1,BaseStatsEnum.BANK,new UnitStats(0, 0, 0, 0, 0, 0, 0), 
					GameObjectType.BUILDING, BuildingType.BANK,1,1,1,1, null);
			bk.gold=100000;
			p.resources.Gold=0;
//			bk.withdraw(1000);
			assertEquals(p.resources.Gold,1000);
			assertEquals(bk.gold,99000);
//			bk.withdraw(11000);
			assertEquals(p.resources.Gold,12000);
			assertEquals(bk.gold,88000);
//			assertEquals(bk.withdraw(100000),false);
			assertEquals(p.resources.Gold,12000);
//			bk.deposit(5000);
			assertEquals(p.resources.Gold,7000);
//			assertEquals(bk.deposit(50000),false);
			assertEquals(p.resources.Gold,7000);
			
	}

}
