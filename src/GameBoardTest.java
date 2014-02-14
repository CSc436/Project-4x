import static org.junit.Assert.*;

import org.junit.Test;

import entities.Player;
import entities.Tile;
import entities.buildings.Barracks;
import entities.buildings.Building;
import entities.units.Infantry;
import entities.units.Unit;

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
		
		Unit temp = new Infantry(juan, 0, 0);
		Unit temp2 = new Infantry(juan, 1, 1);
		Building b = new Barracks(juan,4,4,100);

		game.placeUnitAt(temp, 16, 15);
		game.placeBuildingAt(b, 16, 15);
		//
		
		
		

	}

}
