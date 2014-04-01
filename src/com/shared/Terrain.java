package com.shared;

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
