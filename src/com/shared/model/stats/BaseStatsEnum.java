package com.shared.model.stats;


import java.io.Serializable;

import com.shared.model.resources.Resources;

/**
 * Base Stats Enum
 * 
 * @author NRTop
 * @summary Provides the base statistics for a given unit, agent, or building.
 *          Specifies things such as damage, range, armor, health regenerations,
 *          max health, movememnt speed, and action speed.
 * 
 */
public enum BaseStatsEnum implements Serializable {

	/*
	 * Each enumeration contains a UnitsStats object, which defines the stats
	 * for the each type of unit
	 * UnitsStats( damage, range, armor, health_regen, health ,movementSpeed, actionSpeed, ProductionCost)
	 */

	// UNITS

	INFANTRY(new UnitStats(4, 1, 2, .5f, 40f, 3, 1.5f, new Resources(100, 0, 0,
			0, 0))),

	MILITIA(new UnitStats(1, 1, 0, .2f, 20f, 2, 1.0f, new Resources(100, 0, 0,
			0, 0))),

	SKIRMISHER(new UnitStats(2, 1, 1, .1f, 10f, 2, 1.7f, new Resources(100, 0,
			0, 0, 0))),

	ARCHER(new UnitStats(2, 4, 0, 0, 20f, 3, 1.0f, new Resources(100, 0, 0, 0,
			0))),

	KNIGHT(new UnitStats(5, 1, 3, .5f, 50f, 6, 2.0f, new Resources(100, 0, 0,
			0, 0))),

	RANGED_CALVARY(new UnitStats(4, 5, 2, .4f, 30f, 6, 2.0f, new Resources(100,
			0, 0, 0, 0))),

	TRANSPORT(new UnitStats(0, 0, 2, .2f, 100, 4, 1.0f, new Resources(100, 0,
			0, 0, 0))),

	CATAPULT(new UnitStats(6, 8, 1, 0, 20f, 1, 1.0f, new Resources(100, 0, 0,
			0, 0))),

	BATTERING_RAM(new UnitStats(6, 1, 1, 0, 20f, 1, 1.0f, new Resources(100, 0,
			0, 0, 0))),

	RIFLEMAN(new UnitStats(8, 4, 2, .4f, 50f, 4, 2.0f, new Resources(100, 0, 0,
			0, 0))),

	DRAGOON(new UnitStats(7, 2, 2, .4f, 40f, 6, 2.0f, new Resources(100, 0, 0,
			0, 0))),

	CANNON(new UnitStats(10, 5, 2, 0, 30f, 1, 2.0f, new Resources(100, 0, 0, 0,
			0))),

	MEDIC(new UnitStats(-2, 5, 2, 0, 10f, 2, 2.0f, new Resources(100, 0, 0, 0,
			0))),

	TRADE_CART(new UnitStats(0, 1, 1, .4f, 40f, 4, 1.0f, new Resources(100, 0,
			0, 0, 0))),

	// AGENTS
	GENERAL(new UnitStats(9, 1, 3, 60f, 10, 6, 3.0f, new Resources(100, 0, 0,
			0, 0))),

	PROSPECTOR(new UnitStats(1, 1, 2, .5f, 6, 6, 3.0f, new Resources(100, 0, 0,
			0, 0))),

	MERCHANT(new UnitStats(1, 1, 2, .5f, 6, 6, 3.0f, new Resources(100, 0, 0,
			0, 0))),

	// BUILDINGS

	CASTLE(new UnitStats(0, 2, 0, 0, 2000, 0, 0, new Resources(100, 0, 0, 0,
			0))),

	BARRACKS(new UnitStats(0, 2, 0, 0, 1000, 0, 0, new Resources(100, 0, 0,
			0, 0))),

	BANK( new UnitStats(0, 2, 0, 0, 1000, 0, 0, new Resources(100, 0, 0, 0,
					0))),

	TOWN_HALL(new UnitStats(0, 2, 0, 0, 1000, 0, 0, new Resources(100, 0, 0,
			0, 0))),

	// RESOURCE BUILDINGS
	LUMBER_MILL(new UnitStats(0, 2, 0, 0, 1000, 0, 0, new Resources(100, 0, 0,
			0, 0))),

	STONE_MINE(new UnitStats(0, 2, 0, 0, 1000, 0, 0, new Resources(100, 0, 0,
			0, 0))), 
	
	GOLD_MINE( new UnitStats(0, 2, 0, 0, 1000, 0, 0, new Resources(100, 0, 0,
			0, 0))),
			
	FARM( new UnitStats(0, 2, 0, 0, 1000, 0, 0, new Resources(100, 0, 0, 0,
			0))), 
			
	UNIVERSITY( new UnitStats(0, 2, 0, 0, 1000, 0, 0, new Resources(100, 0, 0,
			0, 0))),

	// RESEARCH BUILDINGS

	BLACKSMITH(new UnitStats(0, 0, 1, 0, 100, 0, 0, new Resources(100, 0, 0, 0,
			0)));

	private UnitStats stats;
	private int baseCreationTime;

	private BaseStatsEnum(UnitStats stats) {
		this(stats, 100);
	}
	
	BaseStatsEnum() {}

	private BaseStatsEnum(UnitStats stats, int creationTime) {
		this.stats = stats;
		this.baseCreationTime = creationTime;
	}

	public UnitStats getStats() {
		return stats;
	}

	public UnitStats augment(UnitStats s, BuffStats t) {
		float current_max = 1f;
		float current_health = 1f;
		if (s != null) {
			current_max = s.max_health;
			current_health = s.health;
		}

		// when we research an upgrade, the new stats should be based off the
		// basestats and tech learned.
		s = t.augment(stats.clone());
		// keep the percentage life the same
		s.health = s.max_health * (current_health / current_max);

		return s;
	}

	public int getCreationTime() {
		return baseCreationTime;
	}

	public Resources getProductionCost() {

		return stats.productionCost;
	}
}
