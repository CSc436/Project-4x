package control.commands;

import java.util.UUID;

import control.GameModel;
import control.Player;
import control.UnitType;

public class BuildingProductionCommand implements Command{
	private int playerId;
	private UUID buildingId;
	private UnitType unitType;
	
	BuildingProductionCommand(int playerId, UUID buidingId, UnitType unitType) {
		playerId = playerId;
		buildingId = buildingId;
		unitType = unitType;
	}
	
	@Override
	public boolean validateCommand(GameModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean performCommand(GameModel model) {
		Player player = model.getPlayer(playerId);
		player.getGameObjects().getBuildings().get(buildingId)
		.queueUnit(unitType);
		
		
		
		return false;
	}

}
