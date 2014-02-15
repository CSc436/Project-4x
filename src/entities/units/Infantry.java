package entities.units;

import entities.Player;

/*
 * Programmer:  Benjamin Deininger
 * 
 * Purpose:   This class extends units to test a tentative unit.
 */

public class Infantry extends Unit {

	public Infantry(Player p, int x, int y) {
		super(p, 100, 2, 1, x, y);
	}

}
