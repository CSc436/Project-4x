package com;

import static org.junit.Assert.*;

import org.junit.Test;

import com.shared.model.buildings.Building;
import com.shared.model.buildings.BuildingType;
import com.shared.model.control.Factory;
import com.shared.model.control.Player;
import com.shared.model.gameboard.GameBoard;
import com.shared.model.gameboard.Tile;

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
