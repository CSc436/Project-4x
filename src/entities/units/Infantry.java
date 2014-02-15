package entities.units;

import java.util.HashMap;

import control.Player;


/*
 * Programmer:  Benjamin Deininger
 * 
 * Purpose:   This class extends units to test a tentative unit.
 */

public class Infantry extends Unit {

	public Infantry(Player p, int x, int y) {
		super(p, 100, 2, 1, x, y);
	}

	@Override
	protected void setActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, String> getActions() {
		// TODO Auto-generated method stub
		return null;
	}

}
