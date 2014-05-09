package com.shared.model.commands;

import com.shared.model.behaviors.Producer;
import com.shared.model.control.GameModel;
import com.shared.model.entities.GameObject;
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
	
	public BuildingProductionCommand() {}
	
	@Override
	public boolean validateCommand(GameModel model) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean performCommand(GameModel model) {
		GameObject o = model.getGameObject(buildingId);
		if(o instanceof Producer)
			((Producer) o).queueProduction(unitType);
		return true;
	}

}
