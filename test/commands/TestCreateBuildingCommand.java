package commands;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.shared.model.buildings.Building;
import com.shared.model.buildings.BuildingType;
import com.shared.model.commands.Command;
import com.shared.model.commands.ConstructBuildingCommand;
import com.shared.model.control.Controller;
import com.shared.model.control.GameModel;
import com.shared.model.control.Player;
import com.shared.model.resources.Resources;

public class TestCreateBuildingCommand {

	@Test
	public void testCreate() {
		ArrayList<String> plist = new ArrayList<>();
		plist.add("Greg");
		plist.add("Pedro");
		GameModel model = new GameModel(plist, 500);
		Controller controller = new Controller(model);

		Thread t = new Thread(controller);
		t.start();
		assertEquals(2, model.getPlayers().size());

		// test create for player 1
		Player p = model.getPlayer(1);
		p.resources.receive(new Resources(1000, 1000, 1000, 1000, 1000));
		Map<UUID, Building> buildings = p.getGameObjects().getBuildings();
		assertEquals(0, buildings.size());

		Command comm = new ConstructBuildingCommand(p, p.getId(),
				BuildingType.BARRACKS, 1, 1, model.getBoard());
		controller.addCommand(comm);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(1, buildings.size());

	}

}
