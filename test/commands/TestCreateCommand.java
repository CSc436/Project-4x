package commands;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.shared.model.buildings.Building;
import com.shared.model.buildings.BuildingType;
import com.shared.model.commands.BuildingProductionCommand;
import com.shared.model.commands.Command;
import com.shared.model.commands.ConstructBuildingCommand;
import com.shared.model.control.Controller;
import com.shared.model.control.GameModel;
import com.shared.model.control.Player;
import com.shared.model.resources.Resources;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public class TestCreateCommand {

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
		p = model.getPlayer(2);
		p.resources.receive(new Resources(1000, 1000, 1000, 1000, 1000));
		p = model.getPlayer(1);
		Map<UUID, Building> buildings = p.getGameObjects().getBuildings();
		assertEquals(0, buildings.size());
		//	public ConstructBuilding(Player p, int playerId, BuildingType bt, int xco,
		// int yco, GameBoard gb)
		
		Command comm = new ConstructBuildingCommand(p,p.getId(),BuildingType.CASTLE,1,1,
				model.getBoard());
		controller.addCommand(comm);
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Building castle = buildings.values().iterator().next();
		assertEquals(1, buildings.size());
		
		comm = new BuildingProductionCommand(p.getId(), castle.getId(),
				UnitType.INFANTRY);
		controller.addCommand(comm);
		Map<UUID, Unit> units = p.getGameObjects().getUnits();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(1, units.size());
		Unit u = units.values().iterator().next();
		assertEquals(p.getId(), u.getPlayerID());
		assertEquals(castle.getX(), u.getX(), .001);
		assertEquals(castle.getY(), u.getY(), .001);
		assertEquals(UnitType.INFANTRY, u.getUnitType());

		// test create for player 2
		p = model.getPlayer(2);
		p.resources.receive(new Resources(1000, 1000, 1000, 1000, 1000));	
		comm = new ConstructBuildingCommand(p,p.getId(),BuildingType.CASTLE,12,12,
				model.getBoard());
		controller.addCommand(comm);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		buildings = p.getGameObjects().getBuildings();
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
		assertEquals(p.getId(), u.getPlayerID());
		assertEquals(castle.getX(), u.getX(), .001);
		assertEquals(castle.getY(), u.getY(), .001);
		assertEquals(UnitType.INFANTRY, u.getUnitType());
	}

}
