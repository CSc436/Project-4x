package com;
import static org.junit.Assert.*;

import org.junit.Test;

import control.GameBoard;
import entities.Player;
import entities.Tile;
import entities.buildings.Barracks;
import entities.buildings.Building;
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
		Player juan = new Player("juan");

		Unit temp = new Infantry(juan, -1, -1);
		Unit temp2 = new Infantry(juan, -1, -1);
		Building b = new Barracks(juan, 4, 4, 100);

		game.placeUnitAt(temp, 15, 15);
		game.placeBuildingAt(b, 15, 15);
		//

		assertFalse(juan.hasUnitAt(0, 0));
		assertFalse(juan.hasUnitAt(1, 1));
		assertTrue(juan.hasUnitAt(15, 15));
		// assertTrue(game.getTileAt(0, 0).);

		// need to test
		assertFalse(game.getTileAt(15, 14).isOccupiedByBuilding());
		assertTrue(game.getTileAt(15, 15).isOccupiedByBuilding());
		assertTrue(game.getTileAt(18, 15).isOccupiedByBuilding());
		assertFalse(game.getTileAt(14, 19).isOccupiedByBuilding());

		game.removeBuilding(b);

		assertFalse(game.getTileAt(15, 14).isOccupiedByBuilding());
		assertFalse(game.getTileAt(15, 15).isOccupiedByBuilding());
		assertFalse(game.getTileAt(18, 15).isOccupiedByBuilding());
		assertFalse(game.getTileAt(14, 19).isOccupiedByBuilding());

	}

}
