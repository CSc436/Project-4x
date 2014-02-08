import static org.junit.Assert.*;

import org.junit.Test;

public class GameBoardTest {

	@Test
	public void test() {

		GameBoard game = new GameBoard(20, 20, 2);
		assertEquals(20, game.getRows());
		assertEquals(20, game.getCols());
		

	}
}
