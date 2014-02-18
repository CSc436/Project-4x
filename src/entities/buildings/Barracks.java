package entities.buildings;

import java.util.HashMap;

import control.Player;


public class Barracks extends Building {

	public Barracks(Player p, int h, int w, int hp) {
		super(p, h, w, hp,1);

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
