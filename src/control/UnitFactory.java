package control;

import entities.units.Infantry;
import entities.units.Unit;

public class UnitFactory {

	public static Unit buildUnit(UnitType u, Player p) {

		Unit result;

		switch (u) {

		case INFANTRY:
			result = new Infantry(p, 1, 1);
			p.getUnits().addUnit(result);
		default:
			result = new Infantry(p, 1, 1);
		}

		return result;

	}

}
