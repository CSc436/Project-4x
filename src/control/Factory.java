package control;

import java.util.UUID;

import entities.buildings.Barracks;
import entities.buildings.Building;
import entities.buildings.Castle;
import entities.gameboard.GameBoard;
import entities.buildings.BuildingType;
import entities.buildings.Farm;
import entities.buildings.GoldMine;
import entities.buildings.LumberMill;
import entities.buildings.StoneMine;
import entities.buildings.University;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;
import entities.units.UnitType;
import entities.units.pawns.Archer;
import entities.units.pawns.Battering_Ram;
import entities.units.pawns.Cannon;
import entities.units.pawns.Catapult;
import entities.units.pawns.Dragoon;
import entities.units.pawns.Infantry;
import entities.units.pawns.Knight;
import entities.units.pawns.Medic;
import entities.units.pawns.Militia;
import entities.units.pawns.Ranged_Calvary;
import entities.units.pawns.Rifleman;
import entities.units.pawns.Skirmisher;
import entities.units.pawns.Transport;

/**
 * Factory
 * 
 * @author NRTop, Ben D, etc. This class allows for easy creation of unit,
 *         building, agent, etc. objects User needs to only pass a the player
 *         who will own the object, type of object, and where object will be
 *         spawned.
 * 
 */
public class Factory {

	/**
	 * getId(): generates a UUID which will be associated with a constructed
	 * object
	 * 
	 * @return a UUID to be associated with a GameObject
	 */
	private static synchronized UUID getId() {
		return UUID.randomUUID();
	}

	/**
	 * buildUnit(): builds a new unit object based on the given UnitType
	 * 
	 * @param p
	 *            - player who will own the object, so object can be added to
	 *            their PlayerUnits objects.
	 * @param playerId
	 *            - id of player
	 * @param unitType
	 *            - type of unit to construct
	 * @param xco
	 *            - x coordinate on gameboard of unit to be constructed
	 * @param yco
	 *            - y coordinate on gameboard of unit to be constructed
	 * @return
	 */
	public static Unit buildUnit(Player p, int playerId, UnitType unitType,
			float xco, float yco) {

		UUID newId = getId();
		Unit result = null;
		switch (unitType) {

		case MILITIA:
			result = new Militia(newId, playerId, xco, yco);
			break;
		case INFANTRY:
			result = new Infantry(newId, playerId, xco, yco);
			break;
		case ARCHER:
			result = new Archer(newId, playerId, xco, yco);
			break;
		case SKIRMISHER:
			result = new Skirmisher(newId, playerId, xco, yco);
			break;
		case KNIGHT:
			result = new Knight(newId, playerId, xco, yco);
			break;
		case RANGED_CALVARY:
			result = new Ranged_Calvary(newId, playerId, xco, yco);
			break;
		case TRANSPORT:
			result = new Transport(newId, playerId, xco, yco);
			break;
		case CATAPULT:
			result = new Catapult(newId, playerId, xco, yco);
			break;
		case BATTERING_RAM:
			result = new Battering_Ram(newId, playerId, xco, yco);
			break;
		case RIFLEMAN:
			result = new Rifleman(newId, playerId, xco, yco);
			break;
		case DRAGOON:
			result = new Dragoon(newId, playerId, xco, yco);
			break;

		case CANNON:
			result = new Cannon(newId, playerId, xco, yco);
			break;

		case MEDIC:
			result = new Medic(newId, playerId, xco, yco);
			break;

		case TRADE_CART:
			result = new Infantry(newId, playerId, xco, yco);
			break;

		default:
			break;
		}
		p.getGameObjects().addUnit(result);
		result.setOwner(p);
		return result;

	}

	// Do not remove Player p arg. It is needed to maintain the collection of
	// buildings for the player

	/**
	 * buildBuilding() builds a new building object based on the specified
	 * BuildingType
	 * 
	 * @param p
	 *            - PLayer who will own constructed building, needed to add to
	 *            player's collection of buildings
	 * @param playerId
	 *            - id of player who will own buildingd
	 * @param buildingType
	 *            - type of building to construct
	 * @param xco
	 *            - x coordinate on gameboard where building will be constructed
	 * @param yco
	 *            - y coordinate on gameboard where building will be constructed
	 * @param gb
	 *            - gameboard on which building will be placed.
	 * @return
	 */
	public static Building buildBuilding(Player p, int playerId,
			BuildingType buildingType, float xco, float yco, GameBoard gb) {
		UUID newId = getId();
		Building result = null;

		String enumString = (buildingType.name());
		Resources buildingCost = BaseStatsEnum.valueOf(enumString)
				.getProductionCost();

		// if the tile is not occupied and the player has the available
		// resources to spend
		if (!gb.getTileAt((int) xco, (int) yco).isOccupiedByBuilding()
				&& p.resources.can_spend(buildingCost)) {

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
				result = new Castle(newId, playerId, xco, yco, 100, 100); // last
																			// two
																			// args
																			// are
																			// populationCap
																			// and
																			// influenceArea
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

			// Deleting the following 3 lines will break test cases. Its
			// probably not a good idea to delete them
			// The building needs a reference to its owner
			// The player needs to know what buildings it has. This is the
			// purpose of the playerUnits class.
			// The player needs to be charged the resources for building the
			// unit
			//
			result.setOwner(p);
			p.getGameObjects().addBuilding(result);
			p.resources.spend(buildingCost);

		} else {
			System.out
					.println("Building placement error;  Out of range, or overlap");
			result = null;
		}
		return result;

	}

}
