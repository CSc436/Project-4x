package entities.buildings;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import control.BuildingType;
import control.Player;
import entities.GameObjectType;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;

public class TestMultiPlayers {

	@Test
	public void mtp(){
		Player p = new Player ("Xu",101);
		Player q = new Player ("Graham", 202);
		Bank bk = new Bank(new UUID(0, 0),1,BaseStatsEnum.BANK,new UnitStats(0, 0, 0, 0, 0, 0, 0), 
				GameObjectType.BUILDING, BuildingType.BANK,1,1,1,1, null);
		bk.gold = 1000000;
		p.resources.Gold = 0;
		q.resources.Gold = 0;
		bk.withdraw(1000,p);
		bk.withdraw(500,q);
		assertEquals(p.resources.Gold,1000);
		assertEquals(q.resources.Gold,500);
		assertEquals(bk.gold,998500);
	}
}
