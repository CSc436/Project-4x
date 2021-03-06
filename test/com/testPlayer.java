package com;

import static org.junit.Assert.*;

import org.junit.Test;

import control.Factory;
import control.Player;
import entities.buildings.Barracks;
import entities.buildings.Building;
import entities.buildings.BuildingType;
import entities.gameboard.GameBoard;
import entities.resources.Resources;

public class testPlayer {

	@Test
	public void testSelectAndDeselect() {
		
		Player p1 = new Player("meathook",0);
		GameBoard g = new GameBoard(20, 20);
		p1.resources.receive(new Resources(1000, 1000, 1000, 1000, 1000));
		assertFalse(p1.hasSelectedEligibleBuilding());
		Building a = Factory.buildBuilding(p1, 0, BuildingType.BARRACKS, 1, 1, g);
		Building b = Factory.buildBuilding(p1, 0, BuildingType.BARRACKS, 5, 5, g);
		Building c = Factory.buildBuilding(p1, 0, BuildingType.BARRACKS, 12, 12, g);
		
		if (b == null || a == null || c== null)
		{
			System.out.println("SOMETHING  NULL\n");
		}
		p1.selectBuilding(a);
		p1.selectBuilding(b);
		assertTrue(p1.hasSelectedEligibleBuilding());
		p1.selectBuilding(c);
		assertTrue(p1.hasSelectedEligibleBuilding());
		p1.deselect();
		assertFalse(p1.hasSelectedEligibleBuilding());

		
	}

}
