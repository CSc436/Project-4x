package control;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import entities.GameObject;

//might break this into two threads. one for input, one for update
public class PlayerTextInput implements Runnable {
	private PlayerCommands instructions;
	private ArrayList<Object> payload;
	Controller controller;
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
			payload.add(count);
			instructions.push(new Command(Actions.STARTUP_CREATE, Targets.PLAYER, payload));
			payload.clear();
		}
		
		System.out.println("How big should the map be? Give height and width. ex:5 5");
		payload.add(keyboard.nextInt());
		payload.add(keyboard.nextInt());
		instructions.push(new Command(Actions.STARTUP_CREATE, Targets.MAP, payload));
		payload.clear();
		
		//start controller in here
		System.out.println("Let's start!");
		controller = new Controller(instructions);
		Thread t = new Thread(controller);
		t.start();		
		
		while(result != -1) {
			System.out.println("Which player is entering commands?");
			int player = keyboard.nextInt();
			System.out.println("What would you like to do?");
			System.out.println("1. gameobject interaction\n2. build gameobject\n" + 
					"3. show game status\n-1. quit");
			result = keyboard.nextInt();
			
			switch (result) {
			case 1:
				gameObjectInteraction(player, keyboard);
				break;
			}
			//instructions.push(result);
		}
		keyboard.close();
		System.out.println("text input thread ended");
	}

	private void gameObjectInteraction(int playerId, Scanner keyboard) {
		System.out.println("Which gameObject would you like to select?");
		Player player = controller.getPlayer(playerId);
		System.out.println(player.toString());
		UUID id = UUID.fromString(keyboard.next());
		GameObject go = player.getGameObjects().getAll().get(id);
		go.getActions();
		//show available actions
		
	}
}