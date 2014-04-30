package com.shared.model.gameboard;

import java.io.Serializable;

public enum Resource implements Serializable {
	//  weighted none resources
	// independent weights per resource
	// gold < wood, etc.
	GOLD, WOOD, FOOD, STONE, RESEARCH, NONE;
}
