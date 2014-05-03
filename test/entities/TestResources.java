package entities;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.server.Controller;
import com.shared.model.buildings.Building;
import com.shared.model.buildings.BuildingType;
import com.shared.model.control.Factory;
import com.shared.model.control.GameModel;
import com.shared.model.control.Player;
import com.shared.model.resources.Resources;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public class TestResources {

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

		Player p = model.getPlayer(1);

		// Give the player some resources
		p.resources.receive(new Resources(1000, 1000, 1000, 1000, 1000));
		assertEquals(1000, p.resources.getGold());
		assertEquals(1000, p.resources.getWood());
		assertEquals(1000, p.resources.getStone());
		assertEquals(1000, p.resources.getResearchPts());
		assertEquals(1000, p.resources.getFood());

		// The player should be able to spend 1 of each resource
		assertTrue(p.resources.spend(new Resources(1, 1, 1, 1, 1)));

		assertEquals(999, p.resources.getGold());
		assertEquals(999, p.resources.getWood());
		assertEquals(999, p.resources.getStone());
		assertEquals(999, p.resources.getResearchPts());
		assertEquals(999, p.resources.getFood());

		// The player shouldnt be able to spend more than he has
		assertFalse(p.resources.spend(new Resources(1000, 1000, 1000, 1000,
				1000)));
		// The resources should not have changed
		assertEquals(999, p.resources.getGold());
		assertEquals(999, p.resources.getWood());
		assertEquals(999, p.resources.getStone());
		assertEquals(999, p.resources.getResearchPts());
		assertEquals(999, p.resources.getFood());

		// Test chargePlayer
		assertTrue(p.chargePlayerForUnitProduction(new Resources(12, 12, 12,
				12, 12)));

		assertEquals(987, p.resources.getGold());
		assertEquals(987, p.resources.getWood());
		assertEquals(987, p.resources.getStone());
		assertEquals(987, p.resources.getResearchPts());
		assertEquals(987, p.resources.getFood());

		assertFalse(p.chargePlayerForUnitProduction(new Resources(22212,
				1222222, 1222222, 1222222, 222212)));

		assertEquals(987, p.resources.getGold());
		assertEquals(987, p.resources.getWood());
		assertEquals(987, p.resources.getStone());
		assertEquals(987, p.resources.getResearchPts());
		assertEquals(987, p.resources.getFood());

		Unit u = Factory.buildUnit(p, p.getId(), UnitType.ARCHER, 1.0f, 1.0f);
		assertTrue(p.chargePlayerForUnitProduction(u.getProductionCost()));

		assertEquals(887, p.resources.getGold());
		assertEquals(987, p.resources.getWood());
		assertEquals(987, p.resources.getStone());
		assertEquals(987, p.resources.getResearchPts());
		assertEquals(987, p.resources.getFood());

		Building b = Factory.buildBuilding(p, p.getId(), BuildingType.BARRACKS,
				1, 1, model.getBoard());

		assertTrue(p.chargePlayerForUnitProduction(u.getProductionCost()));

		assertEquals(687, p.resources.getGold());
		assertEquals(987, p.resources.getWood());
		assertEquals(987, p.resources.getStone());
		assertEquals(987, p.resources.getResearchPts());
		assertEquals(987, p.resources.getFood());

		assertTrue(p.resources.spend(new Resources(587, 987, 987, 987, 987)));

		assertEquals(100, p.resources.getGold());
		assertEquals(0, p.resources.getWood());
		assertEquals(0, p.resources.getStone());
		assertEquals(0, p.resources.getResearchPts());
		assertEquals(0, p.resources.getFood());

		Building c = Factory.buildBuilding(p, p.getId(), BuildingType.BARRACKS,
				10, 10, model.getBoard());

		assertEquals(0, p.resources.getGold());
		assertEquals(0, p.resources.getWood());
		assertEquals(0, p.resources.getStone());
		assertEquals(0, p.resources.getResearchPts());
		assertEquals(0, p.resources.getFood());

		/*
		 * Command comm = new ConstructBuildingCommand(p, p.getId(),
		 * BuildingType.BARRACKS, 1, 1, model.getBoard());
		 * controller.addCommand(comm);
		 * 
		 * try { Thread.sleep(2000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * 
		 * assertEquals(1, buildings.size());
		 */

	}

}
