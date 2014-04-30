package com;

import static org.junit.Assert.*;

import org.junit.Test;

import com.shared.model.buildings.Barracks;
import com.shared.model.buildings.Building;
import com.shared.model.control.BuildingType;
import com.shared.model.control.Factory;
import com.shared.model.control.Player;
import com.shared.model.gameboard.GameBoard;

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
