package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import control.BuildingType;
import control.Controller;
import control.Player;
import control.Factory;
import control.UnitType;
import entities.Action;
import entities.PerfectCivilization;
import entities.PlayerUnits;
import entities.buildings.Barracks;
import entities.buildings.Building;
import entities.gameboard.GameBoard;
import entities.units.Agent;
import entities.units.Infantry;
import entities.units.Unit;

public class GameBoardTest {

	@Test
	public void testBoard() {

		GameBoard game = new GameBoard(20, 20, 2);
		assertEquals(20, game.getRows());
		assertEquals(20, game.getCols());

	}

	@Test
	public void testTile() {

		GameBoard game = new GameBoard(20, 20, 2);
		Player juan = new Player("juan", new PerfectCivilization());

	}

	@Test
	public void testAction() {

		// create a 200x200 board with 2 players
		GameBoard game = new GameBoard(200, 200, 2);
		// get the player references
		Player p1 = game.getPlayerList().get(0);
		Player p2 = game.getPlayerList().get(1);

		// no units should be added to this game yet
		assertEquals(0, p1.getGameObjects().getUnitList().size());
		assertEquals(0, p2.getGameObjects().getUnitList().size());

		// add a unit to player 1
		Factory.buildUnit(UnitType.INFANTRY, p1);

		assertEquals(1, p1.getGameObjects().getUnitList().size());
		assertEquals(0, p2.getGameObjects().getUnitList().size());

		// add a unit to player 2
		Factory.buildUnit(UnitType.INFANTRY, p2);

	}

	@Test
	public void testPlayerUnits() {

		// create a 200x200 board with 2 players
		GameBoard game = new GameBoard(200, 200, 2);
		// get the player references
		Player p1 = game.getPlayerList().get(0);
		Player p2 = game.getPlayerList().get(1);

		// no units should be added to this game yet
		assertEquals(0, p1.getGameObjects().getUnitList().size());
		assertEquals(0, p2.getGameObjects().getUnitList().size());

		// add a unit to player 1
		Factory.buildUnit(UnitType.INFANTRY, p1);

		assertEquals(1, p1.getGameObjects().getUnitList().size());
		assertEquals(0, p2.getGameObjects().getUnitList().size());

		// add a unit to player 2
		Factory.buildUnit(UnitType.INFANTRY, p2);
		Factory.buildStructure(BuildingType.BARRACKS, p1);
		// Agent a1 = new Agent(p1);

		PlayerUnits pu1 = p1.getGameObjects();
		PlayerUnits pu2 = p2.getGameObjects();

		assertFalse(pu1.hasUnitAt(0, 0));
		assertTrue(pu1.hasUnitAt(1, 1));
		assertFalse(pu1.hasUnitAt(0, 1));
		assertFalse(pu1.hasUnitAt(2, 2));
		assertFalse(pu2.hasUnitAt(1, 1));
		assertTrue(pu2.hasUnitAt(2, 2));
		assertFalse(pu2.hasUnitAt(0, 1));
		assertFalse(pu2.hasUnitAt(1, 0));

		assertFalse(pu1.hasBuildingAt(0, 1));
		assertTrue(pu1.hasBuildingAt(1, 1));
		assertFalse(pu1.hasBuildingAt(1, 0));
		assertFalse(pu1.hasBuildingAt(0, 0));

		assertEquals(1, pu1.getAgentList().size());

	}

	@Test
	public void testController() {

		Controller game = new Controller();
		assertEquals(1, game.map.getPlayerList().get(0).getGameObjects()
				.getUnitList().size());
	}
}
