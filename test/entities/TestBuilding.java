
package entities;

import static org.junit.Assert.*;

import org.junit.Test;

import com.shared.model.buildings.Building;
import com.shared.model.buildings.BuildingType;
import com.shared.model.control.Factory;
import com.shared.model.control.Player;
import com.shared.model.gameboard.GameBoard;
import com.shared.model.resources.Resources;

public class TestBuilding {
	GameBoard board = new GameBoard(200, 200);
	Player p = new Player("meathook", 0);
	

	@Test
	public void testGet() {
		p.resources.receive(new Resources(1000, 1000, 1000, 1000, 1000));
		Factory f = new Factory();
		Building b = f.buildBuilding(p, p.getId(), BuildingType.BARRACKS,
				1.0f, 1.0f, board);
		assertEquals(100.0, 0, b.getHealth());
		assertEquals(2, b.getHeight());
		assertEquals(0, b.getPlayerID());
		assertEquals(2, b.getWidth());
		assertEquals(1.0, 0, b.getPosition().getY());
		assertEquals(1.0, 0, b.getPosition().getX());


	}

	@Test
	public void testProduceUnit() {

	}
}
