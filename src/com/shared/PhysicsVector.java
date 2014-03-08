package com.shared;

import java.io.Serializable;

public class PhysicsVector implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1405808061376997684L;
	private double x;
	private double y;

	public PhysicsVector(){
		
	}
	
	public PhysicsVector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public PhysicsVector add(PhysicsVector other) {
		return new PhysicsVector(x + other.x, y + other.y);
	}
	
	public PhysicsVector sub(PhysicsVector other) {
		return new PhysicsVector(x - other.x, y - other.y);
	}
	
	/**
	 * 
	 * @param length
	 * @return
	 */
	public PhysicsVector normalize(double length) {
		if (Math.hypot(x, y) != 0.0) {
			double xTemp = x / Math.hypot(x, y);
			double yTemp = y / Math.hypot(x, y);
			return new PhysicsVector(xTemp * length, yTemp * length);
		} else {
			return new PhysicsVector(0, 0);
		}
	}
	
	/**
	 * 
	 * @param target - Target vector to move towards
	 * @param maxChange - Maximum change allowable to move towards target
	 */
	public PhysicsVector setToTarget( PhysicsVector target, double maxChange ) {
		double deltaX = target.x - x;
		double deltaY = target.y - y;
		double deltaL = Math.hypot(deltaX, deltaY);
		
		if(deltaL <= maxChange) {
			return new PhysicsVector(target.x, target.y);
		} else if(deltaL != 0.0) {
			double xTemp = x + maxChange * (deltaX / deltaL);
			double yTemp = y + maxChange * (deltaY / deltaL);
			return new PhysicsVector(xTemp, yTemp);
		}
		return new PhysicsVector(x, y);
	}
	
	
	public PhysicsVector setToTarget( PhysicsVector target, PhysicsVector changeVector ) {
		
		double tempX = x;
		double tempY = y;
		
		if( changeVector.x > 0 && target.x >= x ) {
			tempX = Math.min(target.x, x + changeVector.x);
		} else if( changeVector.x < 0 && target.x <= x ) {
			tempX = Math.max(target.x, x + changeVector.x);
		}
		
		if( changeVector.y > 0 && target.y >= y ) {
			tempY = Math.min(target.y, y + changeVector.y);
		} else if( changeVector.y < 0 && target.y <= y ) {
			tempY = Math.max(target.y, y + changeVector.y);
		}
		
		return new PhysicsVector(tempX, tempY);
		
	}
	
	public PhysicsVector multiply( double factor ) {
		return new PhysicsVector(x*factor, y*factor);
	}
	
	public void set( double x, double y ) {
		this.x = x;
		this.y = y;
	}
	
	public double magnitude() {
		return Math.hypot(x, y);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
}