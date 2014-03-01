package com.fourx.buffs;

import control.Player;

public enum UnitType {
	ALL, BUILDING, INFANTRY;
	
	public BuffStats getStats(Player p) {
		return p.upgrades.mapping.get(name());
	}
}

