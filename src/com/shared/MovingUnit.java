package com.shared;

import java.io.Serializable;

import com.fourx.util.Point;

public class MovingUnit implements Serializable {
	public double velocity; // Movement velocity, in units per second
	public PointDouble location;
	public PointDouble targetLocation;
	public int turnNumber;
	
	public MovingUnit() {
		location = new PointDouble(0,0);
		targetLocation = new PointDouble(0,0);
		velocity = 1;
		turnNumber = 0;
	}
	
	public MovingUnit(double x, double y, double vel) {
		location = new PointDouble(x,y);
		targetLocation = new PointDouble(x,y);
		velocity = vel;
		turnNumber = 0;
	}
	
	public void incrementX() {
		targetLocation.x++;
	}
	
	public void incrementY() {
		targetLocation.y++;
	}
	
	public void decrementX() {
		targetLocation.x--;
	}
	
	public void decrementY() {
		targetLocation.y--;
	}
	
	public void simulateTimeStep(int timeStep) {
		
		double xDiff = targetLocation.x - location.x;
		double yDiff = targetLocation.y - location.y;
		
		double xVelocity = velocity * (xDiff) / Math.hypot(xDiff, yDiff);
		double yVelocity = velocity * (yDiff) / Math.hypot(xDiff, yDiff);
		
		if(targetLocation.x > location.x) {
			location.x += xVelocity * timeStep / 1000.0;
			location.x = location.x > targetLocation.x ? targetLocation.x : location.x ;
		}
		if(targetLocation.x < location.x) {
			location.x -= xVelocity * timeStep / 1000.0;
			location.x = location.x < targetLocation.x ? targetLocation.x : location.x ;
		}
		if(targetLocation.y > location.y) {
			location.y += yVelocity * timeStep / 1000.0;
			location.y = location.y > targetLocation.y ? targetLocation.y : location.y ;
		}
		if(targetLocation.y < location.y) {
			location.y -= yVelocity * timeStep / 1000.0;
			location.y = location.y < targetLocation.y ? targetLocation.y : location.y ;
		}
	}
	
	public void setTarget(double x, double y) {
		targetLocation.x = x;
		targetLocation.y = y;
	}
}
