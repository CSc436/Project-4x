package com.shared.model.commands;

import com.shared.model.behaviors.Producer;
import com.shared.model.control.GameModel;
import com.shared.model.control.Player;
import com.shared.model.entities.GameObject;
import com.shared.model.resources.Resources;
import com.shared.model.stats.BaseStatsEnum;
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
		if( o == null ) return false;
		
		Resources cost;
		
		// This is awful but I'd rather not refactor someone else's code to make it better
		switch (unitType) {
			case MILITIA: 
				cost = BaseStatsEnum.MILITIA.getProductionCost(); break;
			case INFANTRY: 
				cost = BaseStatsEnum.INFANTRY.getProductionCost(); break;
			case ARCHER: 
				cost = BaseStatsEnum.ARCHER.getProductionCost(); break;
			case SKIRMISHER: 
				cost = BaseStatsEnum.SKIRMISHER.getProductionCost(); break;
			case KNIGHT: 
				cost = BaseStatsEnum.KNIGHT.getProductionCost(); break;
			case RANGED_CALVARY: 
				cost = BaseStatsEnum.RANGED_CALVARY.getProductionCost(); break;
			case TRANSPORT: 
				cost = BaseStatsEnum.TRANSPORT.getProductionCost(); break;
			case CATAPULT: 
				cost = BaseStatsEnum.CATAPULT.getProductionCost(); break;
			case BATTERING_RAM: 
				cost = BaseStatsEnum.BATTERING_RAM.getProductionCost(); break;
			case RIFLEMAN: 
				cost = BaseStatsEnum.RIFLEMAN.getProductionCost(); break;
			case DRAGOON: 
				cost = BaseStatsEnum.DRAGOON.getProductionCost(); break;
			case CANNON: 
				cost = BaseStatsEnum.CANNON.getProductionCost(); break;
			case MEDIC: 
				cost = BaseStatsEnum.MEDIC.getProductionCost(); break;
			case TRADE_CART: 
				cost = BaseStatsEnum.TRADE_CART.getProductionCost(); break;
			default:
				cost = new Resources(); break;
		}
		
		Player p = model.getPlayer(o.getPlayerID());
		if( p != null && o instanceof Producer && p.resources.can_spend(cost)) {
			((Producer) o).queueProduction(unitType);
			model.getPlayer(o.getPlayerID()).resources.spend(cost);
		}
		return true;
	}

}
