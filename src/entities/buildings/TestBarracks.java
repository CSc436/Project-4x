package entities.buildings;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import control.BuildingType;
import control.Player;
import entities.GameObjectType;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;

public class TestBarracks {

	@Test
	public void testbrk(){
		Player p = new Player("Xu", 111);
		Barracks brk = new Barracks(new UUID(0, 0),1,BaseStatsEnum.BARRACKS,new UnitStats(0, 0, 0, 0, 0, 0, 0), 
				 BuildingType.BARRACKS,1,1,1,1);
		brk.soldierNum=0;
		brk.level=1;
		assertEquals(brk.getSoldiers(),50);
		brk.upgrade();
		assertEquals(brk.getSoldiers(),130);
		assertEquals(brk.getSoldiers(),210);
		brk.upgrade();
		brk.upgrade();
		brk.upgrade();
		assertEquals(brk.getSoldiers(),410);
	}
}
