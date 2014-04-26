package control.commands;

import control.GameModel;
import control.UnitType;
import entities.behaviors.Producer;

public class BuildingProductionCommand implements Command{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1803484539458784096L;
	private int buildingId;
	private UnitType unitType;
	
	public BuildingProductionCommand(int buildingId, UnitType unitType) {
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
		Producer p = (Producer) model.getGameObject(buildingId);
		p.queueProduction(unitType);
		return false;
	}

}
