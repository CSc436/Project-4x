package entities;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import control.BuildingType;
import control.Factory;
import control.Player;
import control.UnitType;
import entities.buildings.Building;
import entities.gameboard.GameBoard;
import entities.research.TechnologyTree;
import entities.units.Unit;

public class TestPlayer {
	GameBoard board = new GameBoard(200,200);
	Player p = new Player("meathook", 0);
	Building b = Factory.buildBuilding(p, 0, BuildingType.BARRACKS, 1.0f, 1.0f,board);
	Unit u = Factory.buildUnit(p, p.getId(), UnitType.ARCHER, 1.0f, 1.0f);

	@Test
	public void testGet() {

		assertEquals("meathook", p.getAlias());
		assertEquals(0, p.getId());
		assertEquals(b, p.getBuilding(b.getId()));
		assertEquals(u, p.getUnit(u.getId()));
		/* Rando UUID Should return null */
		assertEquals(null, p.getBuilding(new UUID(22, 0)));
		assertEquals(null, p.getUnit(new UUID(22, 0)));

	}

	@Test
	public void testSet() {

	}

}
