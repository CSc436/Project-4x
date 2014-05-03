package com.shared.utils;

import java.io.Serializable;

import com.shared.utils.Coordinate;

public class PhysicsVector implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1405808061376997684L;
	private Coordinate p;
	
	public PhysicsVector(double x, double y) {
		p = new Coordinate(x,y);
	}
	
	public PhysicsVector(){}
	
	public PhysicsVector(Coordinate p){
		this.p = new Coordinate(p.x,p.y);
	}
	public PhysicsVector add(PhysicsVector other) {
		return new PhysicsVector(p.x + other.p.x, p.y + other.p.y);
	}
	
	public PhysicsVector sub(PhysicsVector other) {
		return new PhysicsVector(p.x - other.p.x, p.y - other.p.y);
	}
	
	/**
	 * 
	 * @param length
	 * @return
	 */
	public PhysicsVector normalize(double length) {
		if (Math.hypot(p.x, p.y) != 0.0) {
			double xTemp = p.x / Math.hypot(p.x, p.y);
			double yTemp = p.y / Math.hypot(p.x, p.y);
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
		double deltaX = target.p.x - p.x;
		double deltaY = target.p.y - p.y;
		double deltaL = Math.hypot(deltaX, deltaY);
		
		if(deltaL <= maxChange) {
			return new PhysicsVector(target.p.x, target.p.y);
		} else if(deltaL != 0.0) {
			double xTemp = p.x + maxChange * (deltaX / deltaL);
			double yTemp = p.y + maxChange * (deltaY / deltaL);
			return new PhysicsVector(xTemp, yTemp);
		}
		return new PhysicsVector(p.x, p.y);
	}
	
	
	public PhysicsVector setToTarget( PhysicsVector target, PhysicsVector changeVector ) {
		
		double tempX = p.x;
		double tempY = p.y;
		
		if( changeVector.p.x > 0 && target.p.x >= p.x ) {
			tempX = Math.min(target.p.x, p.x + changeVector.p.x);
		} else if( changeVector.p.x < 0 && target.p.x <= p.x ) {
			tempX = Math.max(target.p.x, p.x + changeVector.p.x);
		}
		
		if( changeVector.p.y > 0 && target.p.y >= p.y ) {
			tempY = Math.min(target.p.y, p.y + changeVector.p.y);
		} else if( changeVector.p.y < 0 && target.p.y <= p.y ) {
			tempY = Math.max(target.p.y, p.y + changeVector.p.y);
		}
		
		return new PhysicsVector(tempX, tempY);
		
	}
	
	public PhysicsVector multiply( double factor ) {
		return new PhysicsVector(p.x*factor, p.y*factor);
	}
	
	public void set( double x, double y ) {
		this.p.x = x;
		this.p.y = y;
	}
	
	public double magnitude() {
		return Math.hypot(p.x, p.y);
	}
	
	public double getX() {
		return p.x;
	}
	
	public double getY() {
		return p.y;
	}
	
	public PhysicsVector copy() {
		return new PhysicsVector(p);
	}
	
	public Coordinate getCoordinate(){
		return new Coordinate(p.x,p.y);
	}
	public boolean equals( PhysicsVector other ) {
		return other.p.x == p.x && other.p.y == p.y;
	}

	public double[] toArray() {
		return new double[] { p.x, p.y };
	}
}