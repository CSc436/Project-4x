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

		$("#login-button").click(new Function() {
			public boolean f(Event e) {
				Console.log($("#login-username").val());
				Console.log($("#login-password").val());
				// TODO: check if username or password are blank?
				username = $("#login-username").val();
				password = $("#login-password").val();
				
				// Validate user through the client model
				theModel.login(username, password);

				return true; // Default return true for click event
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
		
		// Added quick hack to get loggin name to be chat name -
		// Nick
		// Possibly move to run method?
		GameInterface.setPlayerName(username);

		//Window.alert("Login?!");
		// Init game in 1 second
		// (This is purely to see the loading screen)
		Timer t = new Timer() {
			@Override
			public void run() {
				// Set actions on loading screen
				$("#loading-actions").html("INITIALIZING MODEL");
				theModel.run();
				// Set actions on loading screen
				$("#loading-actions").html("SETTING CANVAS");
				GameCanvas canvas = new GameCanvas(theModel);
				GameInterface.init(theModel, canvas);
				// Set actions on loading screen
				$("#loading-actions").html("RETRIEVING GAME STATE");
				/**
				 * NOTE: loading screen now gets removed once the game model is set
				 * Which happens in ClientController.java **/
			}
		};
		// Schedule the dummy timer
		t.schedule(1000);
	}

}
