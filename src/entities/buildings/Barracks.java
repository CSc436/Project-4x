package entities.buildings;

import control.Player;
import entities.UnitType;
import entities.stats.BaseStatsEnum;

public class Barracks extends Building {

	private static final int barrackHeight = 10;
	private static final int barrackWidth = 10;
	private static int soldierAmount = 20;
	static int newSoldiers;

	public Barracks(Player p, int h, int w, int hp, int lv) {
		super(p, h, w, hp, 1);
		this.maxLevel = 5;
	}

	public Barracks(Player p, int xco, int yco, int idno) {
		super(p, BaseStatsEnum.CASTLE, UnitType.BUILDING, xco, yco, idno);
	}

	protected static int setSoldiers(int i) {
		switch (i) {
		case (1):
			return 50;
		case (2):
			return 80;
		case (3):
			return 100;
		case (4):
			return 150;
		case (5):
			return 200;
		default:
			return 0;
		}
	}

	protected int getSoldiers() {
		soldierAmount += Barracks.setSoldiers(Barracks.getLevel());
		return soldierAmount;
	}
}
