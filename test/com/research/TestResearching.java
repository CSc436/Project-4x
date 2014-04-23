package com.research;

import static org.junit.Assert.*;

import org.junit.Test;

import com.shared.model.control.Player;
import com.shared.model.entities.GameObjectType;
import com.shared.model.entities.PerfectCivilization;
import com.shared.model.research.TechnologyEnum;
import com.shared.model.research.TechnologyTree;
import com.shared.model.resources.Resources;

import entities.TestCivilization;

public class TestResearching {

	@Test
	public void researchTime() {
		Player p = new Player("BOB", 0, new PerfectCivilization(),
				new Resources(9999, 9999, 9999, 9999, 9999));
		String tech = TechnologyEnum.INFANTRYDAMAGE.name();

		// This research takes 50 units of time to complete.
		assertEquals(true, p.getTechTree().research(tech));
		assertEquals(0,
				p.getUpgrades().mapping.get(GameObjectType.UNIT.name()).damage);

		// only after the right amount of time has passed, does this research
		// complete.
		p.getTechTree().researchStep(40);
		// still needs 10 more units of time to complete.
		assertEquals(0,
				p.getUpgrades().mapping.get(GameObjectType.UNIT.name()).damage);

		p.getTechTree().researchStep(10);
		assertEquals(1,
				p.getUpgrades().mapping.get(GameObjectType.UNIT.name()).damage);
	}

	@Test
	public void researchCosts() {
		Player p = new Player("BOB", 0, new PerfectCivilization(),
				new Resources(250, 250, 250, 250, 250));
		String tech = TechnologyEnum.INFANTRYDAMAGE.name();

		// First Research works
		assertEquals(true, p.getTechTree().research(tech));
		assertEquals(0,
				p.getUpgrades().mapping.get(GameObjectType.UNIT.name()).damage);

		// only after the right amount of time has passed, does this research
		// complete.
		p.getTechTree().researchStep(50);
		assertEquals(1,
				p.getUpgrades().mapping.get(GameObjectType.UNIT.name()).damage);

		// Second also works
		assertEquals(true, p.getTechTree().research(tech));
		p.getTechTree().researchStep(90);

		// not enough resources
		assertEquals(false, p.getTechTree().research(tech));

		// check damage for INFANTRY. should be 2 now
		assertEquals(11,
				p.getUpgrades().mapping.get(GameObjectType.UNIT.name()).damage);
	}

	@Test
	public void civilizationResearch() {
		// TestCivilization can only research INFANTRYDAMAGE1 once.
		Player p = new Player("BOB", 0, new TestCivilization(), new Resources(
				9999, 9999, 9999, 9999, 9999));
		String tech = TechnologyEnum.INFANTRYDAMAGE.name();

		assertEquals(1, p.getTechTree().getResearchLevel(tech));

		TechnologyTree t = p.getTechTree();
		t.research(tech);
		t.researchStep(9999);

		// since we set the maximum level to 1, we should not be able to
		// research this a second time.
		assertEquals(false, t.research(tech));
	}

	@Test
	public void testDisabledTechs() {
		// Test that it is disabled by default.
		Player p = new Player("BOB", 0, new PerfectCivilization(),
				new Resources(9999, 9999, 9999, 9999, 9999));
		String tech = TechnologyEnum.DISABLEDTECHNOLOGY.name();

		assertEquals(false, p.getTechTree().research(tech));

		// Test that when enabled, it can be researched.
		TestCivilization civ = new TestCivilization();
		civ.addResearch(tech, 3);
		Player x = new Player("JIM", 1, civ, new Resources(9999, 9999, 9999,
				9999, 9999));

		assertEquals(true, x.getTechTree().research(tech));
	}
}
