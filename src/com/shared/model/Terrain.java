package com.shared.model;

/**
 * Terrain 
 * @author NRTop
 * specifies what kind of terrain a Tile object has (water being lowest, then grass, dirt, etc.)
 */
public enum Terrain {
	FOREST(-2, 0), WATER(-1, 0), SAND(0, 100), GRASS(1, 116), MOUNTAIN(2, 164), SNOW(3, 222);
	
	int value, lower;
	
	Terrain(int value, int lower){
		this.value = value;
		this.lower = lower;
	}
	
	public int getLower(){
		return lower;
	}
	
	public int getValue(){
		return value;
	}
}
