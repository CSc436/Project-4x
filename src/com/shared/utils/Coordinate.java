package com.shared.utils;

public class Coordinate {

	public double x, y;
	
	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return "Coord(" + x + ", " + y + ")";	
	}
	
	public float fx(){
		return (float) x;
	}
	
	public float fy(){
		return (float) y;
	}
}
