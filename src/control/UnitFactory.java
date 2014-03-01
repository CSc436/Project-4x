package control;

import entities.buildings.Barracks;
import entities.buildings.Building;
import entities.units.Infantry;
import entities.units.Unit;

public class UnitFactory {

	static int uniqueid = 0;

	public static Unit buildUnit(UnitType u, Player p) {

		Unit result;

		switch (u) {

		case INFANTRY:
			result = new Infantry(p, 1, 1, uniqueid);
			p.getUnits().addUnit(result);
		default:
			result = new Infantry(p, 1, 1, uniqueid);
		}
		uniqueid++;
		return result;

	}

	public static Unit buildStructure(BuildingType b, Player p) {

		Building result;

		switch (b) {

		case BARRACKS:
			result = new Barracks(p, 1, 1, uniqueid);
			p.getUnits().addBuilding(result);
		default:
			result = new Barracks(p, 1, 1, uniqueid);
		}
		uniqueid++;
		return result;

	}

}
