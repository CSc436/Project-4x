package entities.gameboard;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import control.BuildingType;
import control.Factory;
import control.Player;
import control.UnitType;
import entities.buildings.Building;
import entities.units.Unit;

public class GameBoardTest {

	@Test
	public void testBoard() {

		GameBoard game = new GameBoard(20, 20);
		assertEquals(20, game.getRows());
		assertEquals(20, game.getCols());

	}

	@Test
	public void testTile() {

		GameBoard game = new GameBoard(20, 20);
		Factory fact = new Factory();
		Player juan = new Player("juan", 1);
		Unit temp = fact.buildUnit(1, UnitType.INFANTRY, 0, 0);
		Unit temp2 = fact.buildUnit(1, UnitType.INFANTRY, 5, 5);
		Building b = fact.buildBuilding(1, BuildingType.BARRACKS, 6, 6, 3, 3);

		game.placeBuildingAt(b, 6, 6);
		
		juan.getGameObjects().getUnits().put(temp.getId(), temp);
		juan.getGameObjects().getUnits().put(temp2.getId(), temp2);
		juan.getGameObjects().getBuildings().put(b.getId(), b);

		assertEquals(1, juan.getGameObjects().getBuildings().size());
		assertEquals(2, juan.getGameObjects().getUnits().size());

		// need to test
		assertFalse(game.getTileAt(15, 14).isOccupiedByBuilding());
		assertTrue(game.getTileAt(6, 6).isOccupiedByBuilding());
		assertTrue(game.getTileAt(7, 7).isOccupiedByBuilding());
		assertTrue(game.getTileAt(8, 7).isOccupiedByBuilding());

		game.removeBuilding(b);
		juan.getGameObjects().removeBuilding(b.getId());

		assertFalse(game.getTileAt(15, 14).isOccupiedByBuilding());
		assertFalse(game.getTileAt(6, 6).isOccupiedByBuilding());
		assertFalse(game.getTileAt(7, 7).isOccupiedByBuilding());
		assertFalse(game.getTileAt(8, 7).isOccupiedByBuilding());

	}

}
