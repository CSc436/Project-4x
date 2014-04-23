package com.shared.model.commands;

import java.util.UUID;

import com.shared.model.buildings.Building;
import com.shared.model.buildings.ProductionBuilding;
import com.shared.model.control.Factory;
import com.shared.model.control.GameModel;
import com.shared.model.control.Player;
import com.shared.model.entities.GameObject;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public class BuildingProductionCommand implements Command {
	private int playerId;
	private UUID buildingId;
	private UnitType unitType;

	public BuildingProductionCommand(int playerId, UUID buildingId,
			UnitType unitType) {
		this.playerId = playerId;
		this.buildingId = buildingId;
		this.unitType = unitType;
	}

	@Override
	public boolean validateCommand(GameModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean performCommand(GameModel model) {
		// Find the building's location so we can spawn a unit there
		Player player = model.getPlayer(playerId);
		ProductionBuilding thisBuilding = (ProductionBuilding) player
				.getGameObjects().getBuildings().get(buildingId);

		Unit u = Factory.buildUnit(player, playerId, unitType,
				((GameObject) thisBuilding).getX(),
				((GameObject) thisBuilding).getY());

		// Add this unit to the player's queue
		thisBuilding.queueUnit(u);

		return false;
	}

}
