package commands;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import control.Controller;
import control.GameModel;
import control.Player;
import control.UnitType;
import control.commands.BuildingProductionCommand;
import control.commands.Command;
import entities.buildings.Building;
import entities.units.Unit;

public class CreateCommandTest {

	@Test
	public void testCreate() {
		Controller controller = new Controller();
		GameModel model = controller.getGameModel();
		Thread t = new Thread(controller);
		t.start();
		assertEquals(2, model.getPlayers().size());
		Player p = model.getPlayer(0);
		Map<UUID, Building> buildings = p.getGameObjects().getBuildings();
		assertEquals(1, buildings.size());
		Building castle = buildings.values().iterator().next();
		Command comm = new BuildingProductionCommand(p.getId(), castle.getId(), UnitType.INFANTRY);
		controller.addCommand(comm);
		Map<UUID, Unit> units = p.getGameObjects().getUnits();
		int count = 0;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(1, units.size());
		
		
	}

}
