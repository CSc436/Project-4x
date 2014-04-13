package control;

import java.io.Serializable;

import entities.buildings.Barracks;
import entities.buildings.Building;
import entities.buildings.Castle;
import entities.gameboard.GameBoard;
import entities.buildings.Farm;
import entities.buildings.GoldMine;
import entities.buildings.LumberMill;
import entities.buildings.StoneMine;
import entities.buildings.University;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;

public class Factory implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8480151288632582040L;
	private static int nextID = 1;
	
	private static int getUniqueID() {
		return nextID++;
	}

	public static Unit buildUnit(Player p, int playerId, UnitType unitType,
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
		p.getGameObjects().addUnit(result);
		return result;

	}

	// Do not remove Player p arg. It is needed to maintain the collection of
	// buildings for the player

	public static Building buildBuilding(Player p, int playerId,
			BuildingType buildingType, float xco, float yco, GameBoard gb) {
		int newId = getUniqueID();
		Building result = null;

		// if the tile is not occupied
		if (!gb.getTileAt((int) xco, (int) yco).isOccupiedByBuilding()) {

			switch (buildingType) {
			case TOWN_HALL:
				result = new Barracks(newId, playerId, xco, yco);
				break;
				
			case BARRACKS:
				result = new Barracks(newId, playerId, xco, yco);
				break;
			case BANK:
				result = new Barracks(newId, playerId, xco, yco);
				break;
			case CASTLE:
				result = new Castle(newId, playerId, xco, yco, 100, 100); // last two args are populationCap and influenceArea 
				break;
			case LUMBER_MILL:
				result = new LumberMill(newId, playerId, xco, yco);
				break;
			case STONE_MINE:
				result = new StoneMine(newId, playerId, xco, yco);
				break;
			case GOLD_MINE:
				result = new GoldMine(newId, playerId, xco, yco);
				break;
			case FARM:
				result = new Farm(newId, playerId, xco, yco);
				break;
			case UNIVERSITY:
				result = new University(newId, playerId, xco, yco);
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
