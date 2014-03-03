package control;

import java.util.UUID;

import entities.GameObjectType;
import entities.buildings.Barracks;
import entities.buildings.Building;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;
import entities.units.Infantry;
import entities.units.Unit;

public class Factory {

	private static synchronized UUID getId() {
		return UUID.randomUUID();
	}

	public static Unit buildUnit(int playerId, UnitType unitType, float xco,
			float yco) {

		UUID newId = getId();
		Unit result = null;
		switch (unitType) {

		case INFANTRY:
			result = new Infantry(newId, playerId, BaseStatsEnum.INFANTRY, BaseStatsEnum.INFANTRY.getStats(), GameObjectType.UNIT,
					UnitType.INFANTRY, xco, yco);
		default:
			//result = new Infantry(p, 1, 1, uniqueid);
		}
		
		return result;

	}


	public static Building buildBuilding(int playerId, BuildingType buildingType,
			float xco, float yco, int height, int width) {
		UUID newId = getId();
		Building result = null;

		switch (buildingType) {

		case BARRACKS:
			result = new Barracks(newId, playerId, BaseStatsEnum.BARRACKS, 
					BaseStatsEnum.BARRACKS.getStats(), GameObjectType.BUILDING, BuildingType.BARRACKS, 
					xco, yco, height, width);
		default:
			//result = new Barracks(p, 1, 1, uniqueid);
		}
		return result;

	}

}
