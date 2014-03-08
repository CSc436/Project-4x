package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.UUID;

import org.junit.Test;

import control.BuildingType;
import control.Factory;
import control.Player;
import control.UnitType;
import controller.GameBoard;
import entities.buildings.Building;
import entities.units.Unit;

public class GameBoardTest {

	@Test
	public void testBoard() {

		GameBoard game = new GameBoard(20, 20);
		assertEquals(20, game.getRows());
		assertEquals(20, game.getCols());

	}

	@Test
	public void testTile() {

		GameBoard game = new GameBoard(20, 20);
		Player juan = new Player("juan", 1);

	}

	@Test
	public void testAction() {

		// create a 200x200 board with 2 players
		GameBoard game = new GameBoard(200, 200);
		// get the player references
		Player p1 = new Player("juan", 1);
		Player p2 = new Player("lucas", 2);
		
		// no units should be added to this game yet
		assertEquals(0, p1.getGameObjects().getUnits().size());
		assertEquals(0, p2.getGameObjects().getUnits().size());

		// add a unit to player 1
		Unit temp = Factory.buildUnit(1, UnitType.INFANTRY, 0, 0);
		assertEquals(1, temp.getPlayerId());
		
		p1.getGameObjects().getUnits().put(temp.getId(), temp);
		assertEquals(1, p1.getGameObjects().getUnits().size());
		assertEquals(0, p2.getGameObjects().getUnits().size());

		// add a unit to player 2
		temp = Factory.buildUnit(2, UnitType.INFANTRY, 0, 0);
		assertEquals(2, temp.getPlayerId());

		p2.getGameObjects().getUnits().put(temp.getId(), temp);
		assertEquals(1, p1.getGameObjects().getUnits().size());
		assertEquals(1, p2.getGameObjects().getUnits().size());
		

	}

	@Test
	public void testPlayerUnits() {

		// create a 200x200 board with 2 players
		GameBoard game = new GameBoard(200, 200);
		// get the player references
		Factory fact = new Factory();
		Player p1 = new Player("juan", 1);
		Player p2 = new Player("lucas", 2);

		// no units should be added to this game yet
		assertEquals(0, p1.getGameObjects().getUnits().size());
		assertEquals(0, p2.getGameObjects().getUnits().size());

		// add a unit to player 1
		Unit temp = Factory.buildUnit(1, UnitType.INFANTRY, 0, 0);
		p1.getGameObjects().getUnits().put(temp.getId(), temp);
		game.addUnitAt(temp, temp.getTileX(), temp.getTileY());
		
		assertEquals(1, temp.getPlayerId());
		assertEquals(temp.getId(), game.getUnitsAt(0, 0).get(temp.getId()).getId());
		assertEquals(1, p1.getGameObjects().getUnits().size());
		assertEquals(0, p2.getGameObjects().getUnits().size());

		// add a unit to player 2
		temp = Factory.buildUnit(2, UnitType.INFANTRY, 0, 0);
		game.addUnitAt(temp, temp.getTileX(), temp.getTileY());
		assertEquals(2, temp.getPlayerId());
		p2.getGameObjects().getUnits().put(temp.getId(), temp);
		
		HashMap<UUID, Unit> unitsAtLoc = game.getUnitsAt(0, 0);
		assertEquals(temp.getId(), unitsAtLoc.get(temp.getId()).getId());
		assertEquals(2, unitsAtLoc.size());
		assertEquals(1, p1.getGameObjects().getUnits().size());
		assertEquals(1, p2.getGameObjects().getUnits().size());
		
		Building building = Factory.buildBuilding(2, BuildingType.BARRACKS, 6, 6, 3, 3);
		game.placeBuildingAt(building, 6, 6);
		
		//Lloyd: Not sure if check for unit at location is necessary
		
		assertFalse(game.getTileAt(0, 1).isOccupiedByBuilding());
		assertTrue(game.getTileAt(7, 8).isOccupiedByBuilding());

//		assertEquals(1, pu1.getAgentList().size());

	}
}
