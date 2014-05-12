package com.shared.model.control;

import java.io.Serializable;
import java.util.ArrayList;

import com.shared.model.behaviors.Producer;
import com.shared.model.behaviors.StandardProduction;
import com.shared.model.buildings.*;
import com.shared.model.gameboard.GameBoard;
import com.shared.model.resources.Resources;
import com.shared.model.stats.BaseStatsEnum;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public class Factory implements Serializable {
	
	/**
	 * 
	 */
	public static final long serialVersionUID = -8480151288632582040L;
	int nextID = 1;
	
	public void setId(int id) {
		nextID = id;
	}
	
	public int getUniqueID() {
		return nextID++;
	}

	public Unit buildUnit(Player p, int playerId, UnitType unitType,
			float xco, float yco) {

		int id = getUniqueID();
		Unit result = null;
		switch (unitType) {

		case MILITIA:
			result = new Unit(id, playerId, BaseStatsEnum.MILITIA, BaseStatsEnum.MILITIA.getStats(), UnitType.MILITIA, xco, yco);
			break;
		case INFANTRY:
			result = new Unit(id, playerId, BaseStatsEnum.INFANTRY, BaseStatsEnum.INFANTRY.getStats(), UnitType.INFANTRY, xco, yco);
			break;
		case ARCHER:
			result = new Unit(id, playerId, BaseStatsEnum.ARCHER, BaseStatsEnum.ARCHER.getStats(), UnitType.ARCHER, xco, yco);
			break;
		case SKIRMISHER:
			result = new Unit(id, playerId, BaseStatsEnum.SKIRMISHER, BaseStatsEnum.SKIRMISHER.getStats(), UnitType.SKIRMISHER, xco, yco);
			break;
		case KNIGHT:
			result = new Unit(id, playerId, BaseStatsEnum.KNIGHT, BaseStatsEnum.KNIGHT.getStats(), UnitType.KNIGHT, xco, yco);
			break;
		case RANGED_CALVARY:
			result = new Unit(id, playerId, BaseStatsEnum.RANGED_CALVARY, BaseStatsEnum.RANGED_CALVARY.getStats(), UnitType.RANGED_CALVARY, xco, yco);
			break;
		case TRANSPORT:
			result = new Unit(id, playerId, BaseStatsEnum.TRANSPORT, BaseStatsEnum.TRANSPORT.getStats(), UnitType.TRANSPORT, xco, yco);
			break;
		case CATAPULT:
			result = new Unit(id, playerId, BaseStatsEnum.CATAPULT, BaseStatsEnum.CATAPULT.getStats(), UnitType.CATAPULT, xco, yco);
			break;
		case BATTERING_RAM:
			result = new Unit(id, playerId, BaseStatsEnum.BATTERING_RAM, BaseStatsEnum.BATTERING_RAM.getStats(), UnitType.BATTERING_RAM, xco, yco);
			break;
		case RIFLEMAN:
			result = new Unit(id, playerId, BaseStatsEnum.RIFLEMAN, BaseStatsEnum.RIFLEMAN.getStats(), UnitType.RIFLEMAN, xco, yco);
			break;
		case DRAGOON:
			result = new Unit(id, playerId, BaseStatsEnum.DRAGOON, BaseStatsEnum.DRAGOON.getStats(), UnitType.DRAGOON, xco, yco);
			break;
		case CANNON:
			result = new Unit(id, playerId, BaseStatsEnum.CANNON, BaseStatsEnum.CANNON.getStats(), UnitType.CANNON, xco, yco);
			break;
		case MEDIC:
			result = new Unit(id, playerId, BaseStatsEnum.MEDIC, BaseStatsEnum.MEDIC.getStats(), UnitType.MEDIC, xco, yco);
			break;
		case TRADE_CART:
			result = new Unit(id, playerId, BaseStatsEnum.TRADE_CART, BaseStatsEnum.TRADE_CART.getStats(), UnitType.TRADE_CART, xco, yco);
			break;

		default:
			break;
		}
		if( p != null) p.getGameObjects().addUnit(result);
		return result;

	}

	// Do not remove Player p arg. It is needed to maintain the collection of
	// buildings for the player

	public Building buildBuilding(Player p, int playerId,
			BuildingType buildingType, float xco, float yco, GameBoard gb) {
		int newId = getUniqueID();
		Building result = null;

		// if the tile is not occupied
		if (!gb.getTileAt((int) xco, (int) yco).isOccupiedByBuilding()) {
			
			ArrayList<UnitType> producibleUnitTypes = new ArrayList<UnitType>();
			switch (buildingType) {
			case TOWN_HALL:
				result = new Barracks(newId, playerId, xco, yco);
				break;
				
			case BARRACKS:
				producibleUnitTypes.add(UnitType.INFANTRY);
				producibleUnitTypes.add(UnitType.SKIRMISHER);
				producibleUnitTypes.add(UnitType.MILITIA);
				producibleUnitTypes.add(UnitType.ARCHER);
				result = new ProductionBuilding(newId, playerId, BaseStatsEnum.BARRACKS, BaseStatsEnum.BARRACKS.getStats(),
						BuildingType.BARRACKS, xco, yco, 2, 4, new StandardProduction( producibleUnitTypes ));
				break;
			case BANK:
				result = new Barracks(newId, playerId, xco, yco);
				break;
			case CASTLE:
				producibleUnitTypes.add(UnitType.RANGED_CALVARY);
				producibleUnitTypes.add(UnitType.KNIGHT);
				producibleUnitTypes.add(UnitType.CATAPULT);
				producibleUnitTypes.add(UnitType.BATTERING_RAM);
				producibleUnitTypes.add(UnitType.CANNON);
				result = new Castle(newId, playerId, xco, yco, 100, 100, new StandardProduction( producibleUnitTypes )); // last two args are populationCap and influenceArea 
				break;
			case LUMBER_MILL:
				result = new ResourceBuilding(newId, playerId, BaseStatsEnum.LUMBER_MILL, BaseStatsEnum.LUMBER_MILL.getStats(), 
						BuildingType.LUMBER_MILL, xco, yco, 1, 1, new Resources(0, 20, 0, 0, 0));
				break;
			case STONE_MINE:
				result = new ResourceBuilding(newId, playerId, BaseStatsEnum.STONE_MINE, BaseStatsEnum.STONE_MINE.getStats(), 
						BuildingType.STONE_MINE, xco, yco, 1, 1, new Resources(0, 0, 0, 20, 0));
				break;
			case GOLD_MINE:
				result = new ResourceBuilding(newId, playerId, BaseStatsEnum.GOLD_MINE, BaseStatsEnum.GOLD_MINE.getStats(), 
						BuildingType.GOLD_MINE, xco, yco, 1, 1, new Resources(20, 0, 0, 0, 0));
				break;
			case FARM:
				result = new ResourceBuilding(newId, playerId, BaseStatsEnum.FARM, BaseStatsEnum.FARM.getStats(), 
						BuildingType.FARM, xco, yco, 1, 1, new Resources(0, 0, 20, 0, 0));
				break;
			case UNIVERSITY:
				result = new ResourceBuilding(newId, playerId, BaseStatsEnum.UNIVERSITY, BaseStatsEnum.UNIVERSITY.getStats(), 
						BuildingType.UNIVERSITY, xco, yco, 1, 1, new Resources(0, 0, 0, 0, 20));
				break;
			default:
				result = null;
				break;
				// result = new Barracks(p, 1, 1, uniqueid);
			}
		} else {
				System.out
						.println("Building placement error;  Out of range, or overlap");
				result = null;
		}
		return result;

	}
}
