package entities.units;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import control.Player;

public abstract class Agent extends Unit {
	private Map<String, Unit>  underlings;
	
	public Agent(Player p) {
		super(p,100,10,1,0,0);
		underlings = new HashMap<String, Unit>();
	}
	
	
}
