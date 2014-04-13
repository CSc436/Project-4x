package control.commands;

import control.Factory;
import control.GameModel;
import control.Player;
import control.UnitType;
import entities.buildings.Building;
import entities.units.Unit;

public class BuildingProductionCommand implements Command{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1803484539458784096L;
	private int playerId;
	private int buildingId;
	private UnitType unitType;
	
	public BuildingProductionCommand(int playerId, int buildingId, UnitType unitType) {
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
		//Find the building's location so we can spawn a unit there
		Player player = model.getPlayer(playerId);
		Building thisBuilding = player.getGameObjects().getBuildings().get(buildingId);
		float x = (float) thisBuilding.getMoveBehavior().getPosition().getX();
		float y = (float) thisBuilding.getMoveBehavior().getPosition().getY();
		Unit u = Factory.buildUnit(player, playerId, unitType, x, y);
		
		//Add this unit to the player's queue
		thisBuilding.queueUnit(u);
		
		
		
		
		
		return false;
	}

}
