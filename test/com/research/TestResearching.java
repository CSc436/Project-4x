package com.research;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fourx.Player;
import com.fourx.buffs.TYPE;
import com.fourx.research.TechnologyEnum;

public class TestResearching {
	
	@Test
	public void researchTime() {
		Player p = new Player("BOB", 9999, 9999, 9999, 9999);
		String tech = TechnologyEnum.INFANTRYDAMAGE1.name();
		
		// This research takes 50 units of time to complete.
		assertEquals(true, p.research(tech));
		assertEquals(0, p.upgrades.mapping.get(TYPE.INFANTRY.name()).damage);
		
		// only after the right amount of time has passed, does this research complete.
		p.researchStep(40);
		// still needs 10 more units of time to complete.
		assertEquals(0, p.upgrades.mapping.get(TYPE.INFANTRY.name()).damage);

		p.researchStep(10);
		assertEquals(1, p.upgrades.mapping.get(TYPE.INFANTRY.name()).damage);
	}
	
	@Test
	public void researchCosts() {
		Player p = new Player("BOB", 250, 250, 250, 250);
		String tech = TechnologyEnum.INFANTRYDAMAGE1.name();
		
		// First Research works
		assertEquals(true, p.research(tech));
		assertEquals(0, p.upgrades.mapping.get(TYPE.INFANTRY.name()).damage);
		
		// only after the right amount of time has passed, does this research complete.
		p.researchStep(50);
		assertEquals(1, p.upgrades.mapping.get(TYPE.INFANTRY.name()).damage);
		
		// Second also works
		assertEquals(true, p.research(tech));
		p.researchStep(90);
		
		// not enough resources
		assertEquals(false, p.research(tech));
		
		// check damage for INFANTRY. should be 2 now
		assertEquals(11, p.upgrades.mapping.get(TYPE.INFANTRY.name()).damage);
	}
}
