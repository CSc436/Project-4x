package control;

import java.util.UUID;

import entities.GameObjectType;
import entities.buildings.Barracks;
import entities.buildings.Building;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;
import entities.units.Unit;
import entities.units.pawns.Archer;
import entities.units.pawns.Battering_Ram;
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

public class Factory {

	private static synchronized UUID getId() {
		return UUID.randomUUID();
	}

	public static Unit buildUnit(int playerId, UnitType unitType, float xco,
			float yco) {

		UUID newId = getId();
		Unit result = null;
		switch (unitType) {

		case MILITIA:
			result = new Militia(newId, playerId, BaseStatsEnum.MILITIA,
					BaseStatsEnum.MILITIA.getStats(), GameObjectType.UNIT,
					UnitType.MILITIA, xco, yco);
		case INFANTRY:
			result = new Infantry(newId, playerId, BaseStatsEnum.INFANTRY,
					BaseStatsEnum.INFANTRY.getStats(), GameObjectType.UNIT,
					UnitType.INFANTRY, xco, yco);
		case ARCHER:
			result = new Archer(newId, playerId, BaseStatsEnum.ARCHER,
					BaseStatsEnum.ARCHER.getStats(), GameObjectType.UNIT,
					UnitType.ARCHER, xco, yco);
		case SKIRMISHER:
			result = new Skirmisher(newId, playerId, BaseStatsEnum.SKIRMISHER,
					BaseStatsEnum.SKIRMISHER.getStats(), GameObjectType.UNIT,
					UnitType.SKIRMISHER, xco, yco);
		case KNIGHT:
			result = new Knight(newId, playerId, BaseStatsEnum.KNIGHT,
					BaseStatsEnum.KNIGHT.getStats(), GameObjectType.UNIT,
					UnitType.KNIGHT, xco, yco);
		case RANGED_CALVARY:
			result = new Ranged_Calvary(newId, playerId,
					BaseStatsEnum.RANGED_CALVARY,
					BaseStatsEnum.RANGED_CALVARY.getStats(),
					GameObjectType.UNIT, UnitType.RANGED_CALVARY, xco, yco);
		case TRANSPORT:
			result = new Transport(newId, playerId, BaseStatsEnum.TRANSPORT,
					BaseStatsEnum.TRANSPORT.getStats(), GameObjectType.UNIT,
					UnitType.TRANSPORT, xco, yco);
		case CATAPULT:
			result = new Catapult(newId, playerId, BaseStatsEnum.CATAPULT,
					BaseStatsEnum.CATAPULT.getStats(), GameObjectType.UNIT,
					UnitType.CATAPULT, xco, yco);
		case BATTERING_RAM:
			result = new Battering_Ram(newId, playerId, BaseStatsEnum.BATTERING_RAM,
					BaseStatsEnum.BATTERING_RAM.getStats(),
					GameObjectType.UNIT, UnitType.BATTERING_RAM, xco, yco);
		case RIFLEMAN:
			result = new Rifleman(newId, playerId, BaseStatsEnum.RIFLEMAN,
					BaseStatsEnum.RIFLEMAN.getStats(), GameObjectType.UNIT,
					UnitType.RIFLEMAN, xco, yco);
		case DRAGOON:
			result = new Dragoon(newId, playerId, BaseStatsEnum.DRAGOON,
					BaseStatsEnum.DRAGOON.getStats(), GameObjectType.UNIT,
					UnitType.DRAGOON, xco, yco);

		case CANNON:
			result = new Cannon(newId, playerId, BaseStatsEnum.CANNON,
					BaseStatsEnum.CANNON.getStats(), GameObjectType.UNIT,
					UnitType.CANNON, xco, yco);

		case MEDIC:
			result = new Medic(newId, playerId, BaseStatsEnum.MEDIC,
					BaseStatsEnum.MEDIC.getStats(), GameObjectType.UNIT,
					UnitType.MEDIC, xco, yco);

		case TRADE_CART:
			result = new Infantry(newId, playerId, BaseStatsEnum.TRADE_CART,
					BaseStatsEnum.TRADE_CART.getStats(), GameObjectType.UNIT,
					UnitType.TRADE_CART, xco, yco);

		default:
			// result = new Infantry(p, 1, 1, uniqueid);
		}

		return result;

	}

	public static Building buildBuilding(int playerId,
			BuildingType buildingType, float xco, float yco, int height,
			int width) {
		UUID newId = getId();
		Building result = null;

		switch (buildingType) {

		case BARRACKS:
			result = new Barracks(newId, playerId, BaseStatsEnum.BARRACKS,
					BaseStatsEnum.BARRACKS.getStats(), GameObjectType.BUILDING,
					BuildingType.BARRACKS, xco, yco, height, width);
		default:
			// result = new Barracks(p, 1, 1, uniqueid);
		}
		return result;

	}

}
