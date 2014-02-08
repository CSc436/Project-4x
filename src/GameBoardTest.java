import static org.junit.Assert.*;

import org.junit.Test;

import entities.Infantry;
import entities.Player;
import entities.Tile;
import entities.Unit;

public class GameBoardTest {

	@Test
	public void testBoard() {

		GameBoard game = new GameBoard(20, 20, 2);
		assertEquals(20, game.getRows());
		assertEquals(20, game.getCols());

	}

	@Test
	public void testTile() {

		GameBoard game = new GameBoard(20, 20, 2);
		Player juan = new Player("juan");
		Unit temp = new Infantry(juan);
		Unit temp2 = new Infantry(juan);
		game.placeUnitAt(temp, 0, 0);
		game.placeUnitAt(temp2, 0, 0);

		Tile tester = game.getTileAt(0, 0);

		assertEquals(true, tester.isPassable());
		tester.setPassable(false);
		assertEquals(false, tester.isPassable());
		assertEquals(2, tester.getUnitCount());

		System.out.println("Resource: " + tester.getResource());
		System.out.println("Height :" + tester.getHeight());

		tester.removeUnit(temp);
		assertEquals(1, tester.getUnitCount());
		tester.removeUnit(temp2);
		assertEquals(0, tester.getUnitCount());

		assertEquals(null, tester.getOwner());
		tester.setOwner(juan);
		assertEquals(juan, tester.getOwner());
		tester.setOwner(null);
		assertEquals(null, tester.getOwner());

	}
}
