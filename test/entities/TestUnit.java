
package entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.shared.model.control.Factory;
import com.shared.model.control.Player;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public class TestUnit {
	Player p = new Player("meathook", 0);
	Factory f = new Factory();
	Unit u = f.buildUnit(p, p.getId(), UnitType.ARCHER, 1.0f, 1.0f);

	@Test
	public void testGet() {
		assertEquals(20, 0, u.getHealth());
		assertEquals(1.0, 0, u.getPosition().getX());
		assertEquals(1.0, 0, u.getPosition().getY());
		assertEquals(0, u.getPlayerID());
		//assertEquals(GameObjectType.UNIT, u.type);
		assertEquals(100, u.getCreationTime());

	}

	@Test
	public void testSet() {

		// Commented because this method has been removed.
		// test SetHealth
//		assertEquals(20, 0, u.getHealth());
//		assertTrue(u.setHealth(40.0f));
//		assertEquals(20.0, 0, u.getHealth());
//		assertTrue(u.setHealth(-40.0f));
//		assertEquals(20.0, 0, u.getHealth());
//		assertTrue(u.setHealth(10.0f));
//		assertEquals(10.0, 0, u.getHealth());
//		assertFalse(u.setHealth(0.0f));
//		assertEquals(0.0, 0, u.getHealth());

		// test ModifyHealth
		assertTrue(u.modifyHealthBy(10.0f));
		assertEquals(10.0, 0, u.getHealth());
		assertFalse(u.modifyHealthBy(-10.0f));
		assertEquals(0.0, 0, u.getHealth());

		// test SetLocation

		assertEquals(1.0, 0, u.getPosition().getX());
		assertEquals(1.0, 0, u.getPosition().getY());
		u.setMoveTarget(2.0f, 2.0f);
		assertEquals(2.0, 0, u.getPosition().getX());
		assertEquals(2.0, 0, u.getPosition().getY());
	}

}
