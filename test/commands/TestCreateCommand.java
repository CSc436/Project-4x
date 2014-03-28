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

public class TestCreateCommand {

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
		assertEquals(1, buildings.size());
		Building castle = buildings.values().iterator().next();

		Command comm = new BuildingProductionCommand(p.getId(), castle.getId(),
				UnitType.INFANTRY);
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
		Unit u = units.values().iterator().next();
		assertEquals(p.getId(), u.getPlayerId());
		assertEquals(castle.getX(), u.getX(), .001);
		assertEquals(castle.getY(), u.getY(), .001);
		assertEquals(UnitType.INFANTRY, u.getUnitType());

		// test create for player 2
		p = model.getPlayer(2);
		buildings = p.getGameObjects().getBuildings();
		assertEquals(1, buildings.size());
		castle = buildings.values().iterator().next();

		comm = new BuildingProductionCommand(p.getId(), castle.getId(),
				UnitType.INFANTRY);
		controller.addCommand(comm);
		units = p.getGameObjects().getUnits();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(1, units.size());
		u = units.values().iterator().next();
		assertEquals(p.getId(), u.getPlayerId());
		assertEquals(castle.getX(), u.getX(), .001);
		assertEquals(castle.getY(), u.getY(), .001);
		assertEquals(UnitType.INFANTRY, u.getUnitType());
	}

}
