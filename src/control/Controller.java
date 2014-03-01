package control;

import java.util.List;

import com.shared.MovingNumber;

import entities.buildings.ResourceBuilding;
import entities.gameboard.GameBoard;
import entities.units.Unit;

public class Controller implements Runnable {
	List<Player> players;
	public GameBoard map;
	MovingNumber number;
	UnitFactory ufact;

	public Controller() {
		ufact = new UnitFactory();
		map = new GameBoard(5, 5, 2);
		players = map.getPlayerList();
		number = new MovingNumber(0.0, 1.0);
		UnitFactory.buildUnit(UnitType.INFANTRY, players.get(0));

	}

	@Override
	public void run() {
		int turnNum = 0;
		// call to timer thread
		while (turnNum < 20) {

			for (Player player : players) {

				produceResources(player);
				unitInteraction(player);

				agentDecision(player);

				playerCommands(player);

			}
			turnNum++;
		}

	}

	/**
	 * Get the commands from the client and add them to each player's respective
	 * action queue
	 */
	private void playerCommands(Player p) {

	}

	/**
	 * @param p
	 *            the player whose actionQueue to pop.
	 * 
	 */
	private void unitInteraction(Player p) {
		// get command queue from the player
		// perform unit actions for each unit on the player's commandqueue

		CommandQueue q = p.getCommandQueue();
		Unit u = null;
		while ((u = q.pop()) != null) {
			u.performActions();
		}
	}

	/**
	 * 
	 * @param p
	 *            - the player to whom each agent may add their actions to.
	 */
	private void agentDecision(Player p) {
		// TODO Auto-generated method stub
		//

	}

	private void produceResources(Player player) {

		for (ResourceBuilding building : player.getUnits()
				.getResourceBuildings()) {
			// TODO: building.gen

		}

	}

	public static void main(String args[]) {
		(new Thread(new Controller())).start();
	}
}
