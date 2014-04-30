package com.shared.model.gameboard;

import java.io.Serializable;

/**
 * Resource
 * @author NRTop
 * enum that specifies the type of resource a tile can have.
 */
public enum Resource implements Serializable {

	//  weighted none resources
	// independent weights per resource
	// gold < wood, etc.
	GOLD, WOOD, FOOD, STONE, RESEARCH, NONE;
}
