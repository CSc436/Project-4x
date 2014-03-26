package control;

import java.util.ArrayList;
import java.util.Scanner;

import control.commands.Command;

//might break this into two threads. one for input, one for update
public class PlayerTextInput implements Runnable {
	private PlayerCommands instructions;
	private ArrayList<Object> payload;
	private GameState gs;

	public PlayerTextInput(PlayerCommands instructions) {
		this.instructions = instructions;
		payload = new ArrayList<Object>();
		gs = new GameState();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		int result = 0;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("How many players?");
		int numPlayers = keyboard.nextInt();
		int count = 1;

		while (count <= numPlayers) {
			System.out.println("What is player " + count + "'s name?");
			String name = keyboard.next();
			payload.add(name);
			payload.add(count);
			instructions.push(new Command(Actions.STARTUP_CREATE,
					Targets.PLAYER, payload));
			payload.clear();
		}

		System.out
				.println("How big should the map be? Give height and width. ex:5 5");
		payload.add(keyboard.nextInt());
		payload.add(keyboard.nextInt());
		instructions.push(new Command(Actions.STARTUP_CREATE, Targets.MAP,
				payload));
		payload.clear();

		// start controller in here
		System.out.println("Let's start!");
		Thread controller = new Thread(new Controller(instructions, gs));
		controller.start();

		while (result != -1) {
			System.out.println("Which player is entering commands?");
			int player = keyboard.nextInt();
			System.out.println("What would you like to do?");
			System.out
					.println("1. select gameobject\n2. build building\n-1. quit");
			result = keyboard.nextInt();

			switch (result) {
			case 1:
				gameObjectSelection(player, keyboard);
				break;
			}
			// instructions.push(result);
		}
		keyboard.close();
		System.out.println("text input thread ended");
	}

	private void gameObjectSelection(int player, Scanner keyboard) {
		System.out.println("Which gameObject would you like to select?");
		System.out.println(gs.getPlayerSelectables(player));
		long choice = keyboard.nextLong();// will need to input id
	}

}