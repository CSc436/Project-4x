package entities;

import static org.junit.Assert.*;

import org.junit.Test;

import control.BuildingType;
import control.Factory;
import control.Player;
import entities.buildings.Building;
import entities.gameboard.GameBoard;

public class TestBuilding {
	GameBoard board = new GameBoard(200, 200);
	Player p = new Player("meathook", 0);
	Building b = Factory.buildBuilding(p, p.getId(), BuildingType.BARRACKS,
			1.0f, 1.0f, board);

	@Test
	public void testGet() {

		assertEquals(100.0, 0, b.getHealth());
		assertEquals(2, b.getHeight());
		assertEquals(0, b.getPlayerID());
		assertEquals(4, b.getWidth());
		assertEquals(1.0, 0, b.getY());
		assertEquals(1.0, 0, b.getX());
		b.getProducingUnit();
		System.out.println(b.getCastleId());

	}

	@Test
	public void testProduceUnit() {

	}
}