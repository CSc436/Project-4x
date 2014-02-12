package com.fourx.buffs;

public class BuffStats {
	public int damage;
	
	public BuffStats() {
		damage = 0;
	}
	
	public void add(BuffStats stats) {
		this.damage += stats.damage;
	}
}
