package com.client;

import static com.google.gwt.query.client.GQuery.$;

import com.client.gameinterface.Console;
import com.client.gameinterface.GameInterface;
import com.client.model.ClientController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.query.client.Function;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class PlayGame implements EntryPoint {

	private static ClientController theModel = new ClientController();
	private static String username;
	private static String password;

	public void onModuleLoad() {

		// Set actions on loading screen
		$("#loading-actions").html("INITIALIZING GAME");

		// Callback for login button
		$("#login-button").click(new Function() {
			public boolean f(Event e) {
				username = $("#login-username").val();
				password = $("#login-password").val();

				// Check if username or password are blank
				// FINDBUG - Changed == to .equals
				if (username.equals("")|| password.equals("")) {
					Window.alert("Enter things pleeze");
				} else {

					// Validate user through the client model
					theModel.login(username, password);
				}

				return true; // Default return true for click event
			}
		});
		
		// Allow use to press enter to loggin, that way we can test slightly faster
		$("#login-username").keypress(new Function() {
			public boolean f (Event e)
			{
				if (e.getKeyCode() == 13) 
				{
					$("#login-button").click();
				}
				return true;
			}
		});
		// Allow use to press enter to loggin, that way we can test slightly faster
		$("#login-password").keypress(new Function() {
			public boolean f (Event e)
			{
				if (e.getKeyCode() == 13) 
				{
					$("#login-button").click();
				}
				return true;
			}
		});
		
	}
	
	/**
	 * This method gets called if the client model deems the login valid
	 */
	public static void startGame() {

		Console.log("user is valid, start game");
		// Remove LOGIN SCREEN, NOT THE LOADING SCREEN KELSEY
		$("#login-screen").remove();

		// Give the username to the Game Interface
		GameInterface.setPlayerName(username);

		// Init game in 1 second
		// (This is purely to see the loading screen)
		Timer t = new Timer() {
			@Override
			public void run() {
				// Set actions on loading screen
				$("#loading-actions").html("INITIALIZING MODEL");
				theModel.run(); // Start the client model
				// Set actions on loading screen
				$("#loading-actions").html("SETTING CANVAS");
//				GameCanvas canvas = new GameCanvas(theModel); // Create game
																// canvas
//				GameInterface.init(theModel, canvas); // Init game interface
				// Set actions on loading screen
				$("#loading-actions").html("RETRIEVING GAME STATE");
				/*
				 * NOTE: loading screen now gets removed once the game model is
				 * set Which happens in ClientController.java
				 */
			}
		};
		// Schedule the silly timer
		t.schedule(1000);
	}

}
