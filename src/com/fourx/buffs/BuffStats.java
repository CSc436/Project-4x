package com.fourx.buffs;

public class BuffStats {
	public int damage;
	public float range;
	
	public int armor;
	public float health;
	public float movementSpeed;
	
	// Percentage increase modifier.
	public float actionSpeedModifier;
	
	public BuffStats() {
		damage = 0;
		range = 0f;
		armor = 0;
		health = 0f;
		movementSpeed = 0f;
		actionSpeedModifier = 0f;
	}
	
	public void add(BuffStats stats) {
		damage += stats.damage;
		range += stats.range;
		armor += stats.armor;
		health += stats.health;
		movementSpeed += stats.movementSpeed;
		actionSpeedModifier += stats.actionSpeedModifier;
	}
}
