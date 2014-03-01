package entities;

import control.Player;
import entities.stats.BuffStats;

public enum UnitType {
	ALL, BUILDING, INFANTRY;
	
	public BuffStats getStats(Player p) {
		return p.upgrades.mapping.get(name());
	}
}

