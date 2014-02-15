package control;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import entities.Action;
import entities.units.Unit;

public class CommandQueue {
	
	
	private Queue<Unit> units;
	
	public CommandQueue() {
		units = new ArrayDeque<Unit>();
	}
	
	public void push(Action a, Unit u) {
		u.addAction(a);
		units.add(u);
	}
	
	public Unit pop() {
		return units.poll();
	}
	
	
	
	

}
