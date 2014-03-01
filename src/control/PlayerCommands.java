package control;

import java.util.ArrayDeque;
import java.util.Queue;

public class PlayerCommands {
	
	private Queue<Integer> commands;
	
	public PlayerCommands() {
		commands = new ArrayDeque<Integer>();
	}
	
	public synchronized void push(Integer command) {
		commands.add(command);
	}
	
	public synchronized Integer pop() {
		return commands.poll();
	}
	
	public synchronized Queue<Integer> dump() {
		Queue<Integer> temp = commands;
		commands.clear();
		return temp;
	}
	
}