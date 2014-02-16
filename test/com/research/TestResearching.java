package com.research;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fourx.buffs.UnitType;
import com.fourx.civilizations.PerfectCivilization;
import com.fourx.civilizations.TestCivilization;
import com.fourx.research.TechnologyEnum;
import com.fourx.research.TechnologyTree;
import com.fourx.resources.Resources;

import control.Player;

public class TestResearching {

	@Test
	public void researchTime() {
		Player p = new Player("BOB", new Resources(9999, 9999, 9999, 9999),
				new PerfectCivilization());
		String tech = TechnologyEnum.INFANTRYDAMAGE1.name();

		// This research takes 50 units of time to complete.
		assertEquals(true, p.getTechTree().research(tech));
		assertEquals(0, p.upgrades.mapping.get(UnitType.INFANTRY.name()).damage);

		// only after the right amount of time has passed, does this research
		// complete.
		p.getTechTree().researchStep(40);
		// still needs 10 more units of time to complete.
		assertEquals(0, p.upgrades.mapping.get(UnitType.INFANTRY.name()).damage);

		p.getTechTree().researchStep(10);
		assertEquals(1, p.upgrades.mapping.get(UnitType.INFANTRY.name()).damage);
	}

	@Test
	public void researchCosts() {
		Player p = new Player("BOB", new Resources(250, 250, 250, 250),
				new PerfectCivilization());
		String tech = TechnologyEnum.INFANTRYDAMAGE1.name();

		// First Research works
		assertEquals(true, p.getTechTree().research(tech));
		assertEquals(0, p.upgrades.mapping.get(UnitType.INFANTRY.name()).damage);

		// only after the right amount of time has passed, does this research
		// complete.
		p.getTechTree().researchStep(50);
		assertEquals(1, p.upgrades.mapping.get(UnitType.INFANTRY.name()).damage);

		// Second also works
		assertEquals(true, p.getTechTree().research(tech));
		p.getTechTree().researchStep(90);

		// not enough resources
		assertEquals(false, p.getTechTree().research(tech));

		// check damage for INFANTRY. should be 2 now
		assertEquals(11, p.upgrades.mapping.get(UnitType.INFANTRY.name()).damage);
	}

	@Test
	public void civilizationResearch() {
		// TestCivilization can only research INFANTRYDAMAGE1 once.
		Player p = new Player("BOB", new Resources(9999, 9999, 9999, 9999),
				new TestCivilization());
		String tech = TechnologyEnum.INFANTRYDAMAGE1.name();

		assertEquals(1, p.techTree.getResearchLevel(tech));

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
		Player p = new Player("BOB", new Resources(9999, 9999, 9999, 9999),
				new PerfectCivilization());
		String tech = TechnologyEnum.DISABLEDTECHNOLOGY.name();

		assertEquals(false, p.techTree.research(tech));

		// Test that when enabled, it can be researched.
		TestCivilization civ = new TestCivilization();
		civ.addResearch(tech, 3);
		Player x = new Player("BOB", new Resources(9999, 9999, 9999, 9999), civ);

		assertEquals(true, x.techTree.research(tech));
	}
}
