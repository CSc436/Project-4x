
package entities.stats;

import static org.junit.Assert.*;

import org.junit.Test;

import com.shared.model.control.Factory;
import com.shared.model.control.Player;
import com.shared.model.stats.UnitStats;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public class TestStats {
	Player p = new Player("meathook", 0);
	Factory f = new Factory();
	Unit u = f.buildUnit(p, p.getId(), UnitType.ARCHER, 1.0f, 1.0f);
	UnitStats us = u.getStats();

	@Test
	public void testS() {
		assertEquals(20.0, 0, us.health);
		assertEquals(0, 0, us.armor);
		assertEquals(1.0, 0, us.actionSpeed);
		assertEquals(2, us.damage);
		assertEquals(0.0, 0, us.health_regen);
		assertEquals(20.0, 0, us.max_health);
		assertEquals(3.0, 0, us.movementSpeed);
		assertEquals(4.0, 0, us.range);
		
		
		
	

	}
}
