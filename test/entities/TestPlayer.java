package entities;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import control.Factory;
import control.Player;
import entities.buildings.Building;
import entities.buildings.BuildingType;
import entities.gameboard.GameBoard;
import entities.units.Unit;
import entities.units.UnitType;

public class TestPlayer {
	GameBoard board = new GameBoard(200,200);
	Player p = new Player("meathook", 0);
	Building b = Factory.buildBuilding(p, 0, BuildingType.BARRACKS, 1.0f, 1.0f,board);
	
	Unit u = Factory.buildUnit(p, p.getId(), UnitType.ARCHER, 1.0f, 1.0f);

	@Test
	public void testGet() {

		p.getGameObjects().addBuilding(b);
		p.getGameObjects().addUnit(u);
		assertEquals("meathook", p.getAlias());
		assertEquals(0, p.getId());
		assertEquals(b.getId(), p.getBuilding(b.getId()).getId());
		assertEquals(u.getId(), p.getUnit(u.getId()).getId());
		/* Rando UUID Should return null */
		assertEquals(null, p.getBuilding(new UUID(22, 0)));
		assertEquals(null, p.getUnit(new UUID(22, 0)));

	}

	@Test
	public void testSet() {

	}

}
