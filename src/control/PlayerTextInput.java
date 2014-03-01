package control;

import java.util.ArrayList;
import java.util.Scanner;

public class PlayerTextInput implements Runnable {
	private PlayerCommands instructions;
	private ArrayList<Object> payload;
	public PlayerTextInput(PlayerCommands instructions) {
		this.instructions = instructions;
		payload = new ArrayList<Object>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		int result=0;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("How many players?");
		int numPlayers = keyboard.nextInt();
		int count = 1;
		
		while(count <= numPlayers) {
			System.out.println("What is player " + count + "'s name?");
			String name = keyboard.next();
			payload.add(name);
			instructions.push(new Command(Actions.STARTUP_CREATE, Targets.PLAYER, payload));
			payload.clear();
		}
		
		System.out.println("How big should the map be? Give height and width. ex:5 5");
		payload.add(keyboard.nextInt());
		payload.add(keyboard.nextInt());
		payload.add(numPlayers);//this should not be needed
		instructions.push(new Command(Actions.STARTUP_CREATE, Targets.MAP, payload));
		payload.clear();
		
		System.out.println("Let's start!");
		Thread controller = new Thread(new Controller(instructions));
		controller.start();
		
		//start controller in here
		
		while(result != -1) {
			System.out.println("1. create Unit\n2. build building\n-1. quit");
			result = keyboard.nextInt();
			//instructions.push(result);
		}
		keyboard.close();
		System.out.println("text input thread ended");
	}

}