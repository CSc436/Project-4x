package com;

import static org.junit.Assert.*;

import org.junit.Test;

import control.BuildingType;
import control.Factory;
import control.Player;
import entities.buildings.Barracks;
import entities.buildings.Building;

public class testPlayer {

	@Test
	public void testSelectAndDeselect() {
		
		Player p1 = new Player("meathook",0);
		assertFalse(p1.hasSelectedEligibleBuilding());
		Building a = Factory.buildBuilding(0, BuildingType.BARRACKS, 1, 1);
		Building b = Factory.buildBuilding(0, BuildingType.BARRACKS, 1, 1);
		Building c = Factory.buildBuilding(0, BuildingType.BARRACKS, 1, 1);
		p1.selectBuilding(a);
		p1.selectBuilding(b);
		assertTrue(p1.hasSelectedEligibleBuilding());
		p1.selectBuilding(c);
		assertTrue(p1.hasSelectedEligibleBuilding());
		p1.deselect();
		assertFalse(p1.hasSelectedEligibleBuilding());

		
	}

}
