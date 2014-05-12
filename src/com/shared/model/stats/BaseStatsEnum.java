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
	// Resources are (GOLD, WOOD, FOOD, STONE, RESEARCH)
	
	INFANTRY       (new UnitStats(10, 1, 2, .2f, 40f, 2, 1.0f, new Resources(40, 40, 30, 0, 0)), 10),

	MILITIA        (new UnitStats(8, 1, 1, .2f, 30f, 1, 1.0f, new Resources(30, 30, 20, 0, 0)), 7),

	SKIRMISHER     (new UnitStats(6, 1, 0, .2f, 20f, 1, 1.0f, new Resources(20, 20, 20, 0, 0)), 5),
			
	ARCHER         (new UnitStats(4, 4, 0, .2f, 20f, 2, 1.0f, new Resources(30, 20, 20, 0, 0)), 6),
	
	KNIGHT         (new UnitStats(15, 1, 3, .4f, 60f, 4, 1.0f, new Resources(80, 0, 50, 0, 0)), 15),

	RANGED_CALVARY (new UnitStats(6, 5, 2, .4f, 50f, 4, 1.0f, new Resources(40, 20, 50, 0, 0)), 12),

	CATAPULT       (new UnitStats(20, 3, 1, .0f, 20f, 1, 1.5f, new Resources(100, 100, 0, 0, 0)), 20),

	BATTERING_RAM  (new UnitStats(50, 1, 1, .0f, 30f, 1, 2.0f, new Resources(150, 100, 0, 0, 0)), 20),

	CANNON         (new UnitStats(30, 5, 2, .0f, 30f, 1, 1.5f, new Resources(200, 50, 0, 0, 0)), 20),

	// Units not included

	TRANSPORT      (new UnitStats(0, 0, 2, .2f, 100f, 2, 1.0f, new Resources(100, 50, 0, 0, 0)), 10),
	
	TRADE_CART     (new UnitStats(0, 1, 1, .4f, 40f, 4, 1.0f, new Resources(100, 0, 0, 0, 0)), 30),
	
	RIFLEMAN       (new UnitStats(10, 3, 1, .2f, 50f, 4, 2.0f, new Resources(100, 0, 0, 0, 0)), 30),
	
	DRAGOON        (new UnitStats(7, 2, 2, .4f, 40f, 6, 2.0f, new Resources(100, 0, 0, 0, 0)), 30),
	
	MEDIC          (new UnitStats(-2, 5, 2, .0f, 10f, 2, 2.0f, new Resources(100, 0, 0, 0, 0)), 30),

	// AGENTS
	GENERAL        (new UnitStats(9, 1, 3, 60f, 10, 6, 3.0f, new Resources(100, 0, 0, 0, 0)), 30),

	PROSPECTOR     (new UnitStats(1, 1, 2, .5f, 6, 6, 3.0f, new Resources(100, 0, 0, 0, 0)), 30),

	MERCHANT       (new UnitStats(1, 1, 2, .5f, 6, 6, 3.0f, new Resources(100, 0, 0, 0, 0)), 30),

	// BUILDINGS

	CASTLE         (new UnitStats(0, 2, 0, 0, 1000, 0, 0, new Resources(100, 200, 0, 200, 0)), 30),

	BARRACKS       (new UnitStats(0, 2, 0, 0, 500, 0, 0, new Resources(50, 100, 0, 100, 0)), 30),

	BANK           (new UnitStats(0, 2, 0, 0, 500, 0, 0, new Resources(100, 0, 0, 0, 0)), 30),

	TOWN_HALL      (new UnitStats(0, 2, 0, 0, 500, 0, 0, new Resources(100, 0, 0, 0, 0)), 30),

	// RESOURCE BUILDINGS
	LUMBER_MILL    (new UnitStats(0, 2, 0, 0, 300, 0, 0, new Resources(30, 50, 0, 20, 0)), 30),

	STONE_MINE     (new UnitStats(0, 2, 0, 0, 300, 0, 0, new Resources(50, 30, 0, 20, 0)), 30), 
	
	GOLD_MINE      (new UnitStats(0, 2, 0, 0, 300, 0, 0, new Resources(50, 30, 0, 20, 0)), 30),
			
	FARM           (new UnitStats(0, 2, 0, 0, 300, 0, 0, new Resources(30, 50, 20, 0, 0)), 30), 
			
	UNIVERSITY     (new UnitStats(0, 2, 0, 0, 300, 0, 0, new Resources(30, 50, 0, 50, 0)), 30),

	// RESEARCH BUILDINGS

	BLACKSMITH     (new UnitStats(0, 0, 1, 0, 300, 0, 0, new Resources(100, 0, 0, 0, 0)), 30);

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
