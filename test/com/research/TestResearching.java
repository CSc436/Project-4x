package com.research;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.fourx.Player;
import com.fourx.buffs.TYPE;
import com.fourx.research.TechnologyEnum;

public class TestResearching {
	Player p;

	@Before
	public void setUp() throws Exception {
		p = new Player("BOB");
	}

	@Test
	public void double_research() {
		String tech = TechnologyEnum.INFANTRYDAMAGE1.name();
		// INFANTRY damage starts at 0.
		assertEquals(0, p.upgrades.mapping.get(TYPE.INFANTRY.name()).damage);

		p.research(tech);
		// successfully researched this technology, damage is now 1.
		assertEquals(1, p.upgrades.mapping.get(TYPE.INFANTRY.name()).damage);

		// This research fails because the player has already researched it.
		// INFANTRY damage remains unchanged.
		p.research(tech);
		assertEquals(1, p.upgrades.mapping.get(TYPE.INFANTRY.name()).damage);
	}

	@Test
	public void requirements() {
		String tech = TechnologyEnum.INFANTRYDAMAGE1.name();
		String tech_2 = TechnologyEnum.INFANTRYDAMAGE2.name();
		// INFANTRY damage starts at 0.
		assertEquals(0, p.upgrades.mapping.get(TYPE.INFANTRY.name()).damage);
		
		// fails because INFANTRYDAMAGE1 is required.
		assertEquals(false, p.research(tech_2));
		// INFANTRY damage is still 0.
		assertEquals(0, p.upgrades.mapping.get(TYPE.INFANTRY.name()).damage);
		
		// has no requirements, INFANTRY damage is now 1.
		assertEquals(true, p.research(tech));
		assertEquals(1, p.upgrades.mapping.get(TYPE.INFANTRY.name()).damage);
		
		// This time it works because INFANTRYDAMAGE2 required INFANTRYDAMAGE1.
		assertEquals(true, p.research(tech_2));
		// successfully researched INFANTRYDAMAGE2, INFANTRY.damage is now 2.
		assertEquals(2, p.upgrades.mapping.get(TYPE.INFANTRY.name()).damage);		

	}
}
