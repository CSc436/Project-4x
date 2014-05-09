package com.shared.model.units;

import java.io.Serializable;


/**
 * UnitType
 * @author NRTop
 * The more specific type of unit 
 */
public enum UnitType implements Serializable {
	// BASE UNITS
	MILITIA,

	INFANTRY,

	SKIRMISHER,

	ARCHER,

	KNIGHT,

	RANGED_CALVARY,

	TRANSPORT,

	CATAPULT,

	BATTERING_RAM,

	RIFLEMAN,

	DRAGOON,

	CANNON,

	MEDIC,

	TRADE_CART,
	
	// AGENTS
	GENERAL,
	
	PROSPECTOR, 
	
	MERCHANT
}
