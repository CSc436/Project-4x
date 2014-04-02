package com;

import static org.junit.Assert.*;

import org.junit.Test;

import control.BuildingType;
import control.Factory;
import control.Player;
import entities.buildings.Barracks;
import entities.buildings.Building;
import entities.gameboard.GameBoard;

public class testPlayer {

	@Test
	public void testSelectAndDeselect() {
		
		Player p1 = new Player("meathook",0);
		GameBoard g = new GameBoard(10, 10);
		assertFalse(p1.hasSelectedEligibleBuilding());
		Building a = Factory.buildBuilding(p1, 0, BuildingType.BARRACKS, 1, 1, g);
		Building b = Factory.buildBuilding(p1, 0, BuildingType.BARRACKS, 1, 1, g);
		Building c = Factory.buildBuilding(p1, 0, BuildingType.BARRACKS, 1, 1, g);
		
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