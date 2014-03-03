package control;

import java.util.ArrayDeque;
import java.util.Queue;
import entities.Action;
import entities.units.Unit;

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
