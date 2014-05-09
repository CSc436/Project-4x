package com.shared.utils;

import java.io.Serializable;

public class Coordinate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5175771450663765989L;
	public double x, y;
	
	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Coordinate() {}
	
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
