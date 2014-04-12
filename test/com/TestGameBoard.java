package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import control.Factory;
import control.Player;
import entities.buildings.Building;
import entities.buildings.BuildingType;
import entities.gameboard.GameBoard;
import entities.gameboard.Tile;
import entities.resources.Resources;

public class TestGameBoard {

	GameBoard board = new GameBoard(200, 200);
	Player p = new Player("meathook", 0);

	@Test
	public void testOccupied1() {

		p.resources.receive(new Resources(1000, 1000, 1000, 1000, 1000));
		assertEquals(200, board.getCols());
		assertEquals(200, board.getRows());

		Building b = Factory.buildBuilding(p, 0, BuildingType.BARRACKS, 1.0f,
				2.0f, board);

		for (int r = 0; r < 1; r++) {
			for (int c = 0; c < 2; c++) {
				assertFalse(board.getTileAt(r, c).isOccupiedByBuilding());
			}

		}

		assertTrue(board.getTileAt(1, 2).isOccupiedByBuilding());
		assertTrue(board.getTileAt(2, 2).isOccupiedByBuilding());
		assertTrue(board.getTileAt(1, 3).isOccupiedByBuilding());
		assertTrue(board.getTileAt(2, 3).isOccupiedByBuilding());
		assertFalse(board.getTileAt(1, 4).isOccupiedByBuilding());
		assertFalse(board.getTileAt(2, 4).isOccupiedByBuilding());
		assertFalse(board.getTileAt(1, 5).isOccupiedByBuilding());
		assertFalse(board.getTileAt(2, 5).isOccupiedByBuilding());

		for (int r = 2; r < 200; r++) {
			for (int c = 6; c < 200; c++) {
				assertFalse(board.getTileAt(r, c).isOccupiedByBuilding());
			}

		}

		board.removeBuilding(b);

		for (int r = 0; r < 200; r++) {
			for (int c = 0; c < 200; c++) {
				assertFalse(board.getTileAt(r, c).isOccupiedByBuilding());
			}

		}

	}

	@Test
	public void testOccupied2() {
		p.resources.receive(new Resources(1000, 1000, 1000, 1000, 1000));
		// These should fail, as there is a building at 1,2
		Building b = Factory.buildBuilding(p, 0, BuildingType.BARRACKS, 1.0f,
				2.0f, board);
		Building c = Factory.buildBuilding(p, 0, BuildingType.BARRACKS, 1.0f,
				3.0f, board);

		// This should pass
		c = Factory.buildBuilding(p, 0, BuildingType.BARRACKS, 5.0f, 4.0f,
				board);

		// This should fail, as it is off the map
		Building d = Factory.buildBuilding(p, 0, BuildingType.BARRACKS, 199.0f,
				3.0f, board);

	}

}
