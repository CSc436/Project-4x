package control;

import java.io.Serializable;
import java.util.UUID;

import entities.buildings.Barracks;
import entities.buildings.Building;
import entities.buildings.Castle;
import entities.gameboard.GameBoard;
import entities.buildings.Farm;
import entities.buildings.GoldMine;
import entities.buildings.LumberMill;
import entities.buildings.StoneMine;
import entities.buildings.University;
import entities.units.Unit;
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

public class Factory implements Serializable {
	
	private static int id = 1;

	private static synchronized UUID getId() {
		return UUID.randomUUID();
	}
	
	private static int getUniqueInt() {
		return id++;
	}

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
		return result;

	}

	// Do not remove Player p arg. It is needed to maintain the collection of
	// buildings for the player

	public static Building buildBuilding(Player p, int playerId,
			BuildingType buildingType, float xco, float yco, GameBoard gb) {
		UUID newId = getId();
		Building result = null;

		// if the tile is not occupied
		if (!gb.getTileAt((int) xco, (int) yco).isOccupiedByBuilding()) {

			switch (buildingType) {
			case TOWN_HALL:
				result = new Barracks(newId, playerId, xco, yco);
				break;
				
			case BARRACKS:
				result = new Barracks(newId, playerId, xco,
						yco);
				break;
			case BANK:
				result = new Barracks(newId, playerId,
						 xco, yco);
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
