package com.server;

import com.shared.Request;

public class Model {
	public double number;
	public boolean isBlack;
	
	public Model() {
		number = 0.0;
		isBlack = false;
	}

	public void increment() {
		number++;
	}

	public boolean canIncrement() {
		return number <= 10.0;
	}
	
	public boolean canDecrement() {
		return number >= -10.0;
	}

	public void decrement() {
		number--;
	}
	
	
}
