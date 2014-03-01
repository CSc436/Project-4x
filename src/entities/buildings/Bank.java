package entities.buildings;

import control.Player;
import entities.BaseStatsEnum;
import entities.UnitType;
import entities.resources.Resources;

public class Bank extends ResourceBuilding {
	// Placeholder bank building dimensions
	private static final int bank_Height = 4;
	private static final int bank_Width = 4;
	private static final int bank_BaseGenAmt = 100; // Base amount that banks
													// will generate for the
													// user

	public Bank(Player p, BaseStatsEnum baseStats, UnitType type, int xco,
			int yco, int height, int width) {
		super(p, baseStats, type, xco, yco, bank_Height, bank_Width, new Resources(0,bank_BaseGenAmt,0,0));
	}
}
