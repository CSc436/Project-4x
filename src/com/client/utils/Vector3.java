package com.client.utils;

public class Vector3 {
	public float x;
	public float y;
	public float z;
	
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void normalize() {
		float length = (float) Math.sqrt(x*x + y*y + z*z);
		x /= length;
		y /= length;
		z /= length;
	}
	
	public void multScalar(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;
	}
	
	public void mult(Vector3 vector) {
		
	}
	
	public void left() {
		float temp = x;
		x = -y;
		y = temp;
	}
	
	public void right() {
		float temp = x;
		x = y;
		y = -temp;
	}
	
	public String toString() {
		return "[" + x + ", " + y + ", " + z + "]";
	}
	
	public static float dist(float x, float y) {
		return (float)Math.sqrt(x*x + y*y);
	}

	public static Vector3 getVectorBetween(Vector3 from, Vector3 to) {
		// TODO Auto-generated method stub
		Vector3 deltaVector = new Vector3(to.x - from.x, to.y - from.y, to.z - from.z);
		return deltaVector;
	}
}
