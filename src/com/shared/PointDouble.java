package com.shared;

import java.io.Serializable;

public class PointDouble implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1405808061376997684L;
	public double x;
	public double y;

	public PointDouble(){
		
	}
	
	public PointDouble(double y, double x) {
		this.x = x;
		this.y = y;
	}
}