package com.shared.model.control;

import java.util.ArrayDeque;
import java.util.Queue;

import com.shared.model.entities.Action;
import com.shared.model.units.Unit;

public class CommandQueue {

	// per player
	private Queue<Unit> units;

	public CommandQueue() {
		units = new ArrayDeque<Unit>();
	}

	public void push(Action a, Unit u) {
		units.add(u);
	}

	public Unit pop() {
		
		return units.poll();
	}

}
