package control;

import com.server.MovingNumber;

public class Controller implements Runnable {
	Player player1;
	GameBoard map;
	MovingNumber number;
	
	public Controller() {
		player1 = new Player("Bob");
		map = new GameBoard(5, 5, 2);
		number = new MovingNumber(0.0,1.0);
	}

	@Override
	public void run() {
		int turnNum = 0;
		
		while (turnNum < 20) {
			
		}
		
	}
}
