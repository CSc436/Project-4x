package commands;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import org.junit.Test;
import com.server.Controller;
import com.shared.model.buildings.Building;
import com.shared.model.buildings.BuildingType;
import com.shared.model.commands.BuildingProductionCommand;
import com.shared.model.commands.Command;
import com.shared.model.commands.ConstructBuildingCommand;
import com.shared.model.control.GameModel;
import com.shared.model.control.Player;
import com.shared.model.resources.Resources;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;
import com.shared.utils.Coordinate;

public class TestCreateCommand {

	@Test
	public void testCreate() {

		GameModel model = new GameModel(500);
		model.addPlayer("Greg");
		model.addPlayer("Pedro");
		Controller controller = new Controller(model);

		Thread t = new Thread(controller);
		t.start();
		assertEquals(2, model.getPlayers().size());

		// test create for player 1
		Player p = model.getPlayer(0);
		p.resources.receive(new Resources(1000, 1000, 1000, 1000, 1000));

		p = model.getPlayer(1);
		p.resources.receive(new Resources(1000, 1000, 1000, 1000, 1000));

		p = model.getPlayer(0);
		Map<Integer, Building> buildings = p.getGameObjects().getBuildings();
		assertEquals(0, buildings.size());
		// public ConstructBuilding(Player p, int playerId, BuildingType bt, int
		// xco,
		// int yco, GameBoard gb)

		Command comm = new ConstructBuildingCommand(BuildingType.CASTLE,
				p.getId(), new Coordinate(1, 1));
		controller.addCommand(comm);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Currently fails.
		buildings = p.getGameObjects().getBuildings();
		assertEquals(1, buildings.size());
		

	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		controller.continueSimulation();
		buildings = p.getGameObjects().getBuildings();
		assertEquals(1, buildings.size());

		// test create for player 2
		p = model.getPlayer(1);
		buildings = p.getGameObjects().getBuildings();
		assertEquals(0, buildings.size());
		p.resources.receive(new Resources(1000, 1000, 1000, 1000, 1000));
		comm = new ConstructBuildingCommand(BuildingType.CASTLE, p.getId(),
				new Coordinate(12, 12));
		controller.addCommand(comm);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		buildings = p.getGameObjects().getBuildings();
		assertEquals(1, buildings.size());


	}

}
