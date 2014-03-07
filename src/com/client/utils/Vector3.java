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
}
