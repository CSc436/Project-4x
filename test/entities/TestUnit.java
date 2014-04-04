package entities;

import static org.junit.Assert.*;

import org.junit.Test;

import control.Factory;
import control.Player;
import entities.units.Unit;
import entities.units.UnitType;

public class TestUnit {
	Player p = new Player("meathook", 0);
	Unit u = Factory.buildUnit(p, p.getId(), UnitType.ARCHER, 1.0f, 1.0f);

	@Test
	public void testGet() {
		assertEquals(20, 0, u.getHealth());
		assertEquals(1.0, 0, u.getX());
		assertEquals(1.0, 0, u.getY());
		assertEquals(0, u.getPlayerID());
		assertEquals(GameObjectType.UNIT, u.type);
		assertEquals(100, u.getCreationTime());

	}

	@Test
	public void testSet() {

		// test SetHealth
		assertEquals(20, 0, u.getHealth());
		assertTrue(u.setHealth(40.0f));
		assertEquals(20.0, 0, u.getHealth());
		assertTrue(u.setHealth(-40.0f));
		assertEquals(20.0, 0, u.getHealth());
		assertTrue(u.setHealth(10.0f));
		assertEquals(10.0, 0, u.getHealth());
		assertFalse(u.setHealth(0.0f));
		assertEquals(0.0, 0, u.getHealth());

		// test ModifyHealth
		assertTrue(u.modifyHealthBy(10.0f));
		assertEquals(10.0, 0, u.getHealth());
		assertFalse(u.modifyHealthBy(-10.0f));
		assertEquals(0.0, 0, u.getHealth());

		// test SetLocation

		assertEquals(1.0, 0, u.getX());
		assertEquals(1.0, 0, u.getY());
		u.setLocation(2.0f, 2.0f);
		assertEquals(2.0, 0, u.getX());
		assertEquals(2.0, 0, u.getY());
	}

}
