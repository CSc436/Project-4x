package com;

import static org.junit.Assert.*;

import org.junit.Test;

import control.Factory;
import control.Player;
import entities.buildings.Building;
import entities.buildings.BuildingType;
import entities.gameboard.GameBoard;
import entities.gameboard.Tile;

public class TestTile {

	GameBoard board = new GameBoard(200, 200);
	Player p = new Player("meathook", 0);
	Building b = Factory.buildBuilding(p, 0, BuildingType.BARRACKS, 1.0f, 2.0f,
			board);
	Tile t = board.getTileAt(0, 0);

	@Test
	public void testSet() {

		assertEquals(null, t.getOwner());
		t.setOwner(p);
		assertEquals(p, t.getOwner());
		Player test = t.getOwner();
		assertEquals(test, p);

		assertEquals(0.0, 0, t.getXCoordinate());
		assertEquals(0.0, 0, t.getYCoordinate());
	}

}
