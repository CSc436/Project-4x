package com.shared.model.commands;

import com.shared.model.behaviors.Producer;
import com.shared.model.control.GameModel;
import com.shared.model.units.UnitType;

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
