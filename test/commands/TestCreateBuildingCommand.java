package commands;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.UUID;
import org.junit.Test;
import control.BuildingType;
import control.Controller;
import control.GameModel;
import control.Player;
import control.commands.Command;
import control.commands.ConstructBuildingCommand;
import entities.buildings.Building;


public class TestCreateBuildingCommand {

	@Test
	public void testCreate() {
		Controller controller = new Controller();
		GameModel model = controller.getGameModel();
		Thread t = new Thread(controller);
		t.start();
		assertEquals(2, model.getPlayers().size());

		// test create for player 1
		Player p = model.getPlayer(1);
		Map<UUID, Building> buildings = p.getGameObjects().getBuildings();
		assertEquals(0, buildings.size());

		Command comm = new ConstructBuildingCommand(p, p.getId(),
				BuildingType.BARRACKS, 1, 1, model.getBoard());
		controller.addCommand(comm);
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(1, buildings.size());


	}

}
