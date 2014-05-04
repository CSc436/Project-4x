package com.client.gameinterface;

import static com.google.gwt.query.client.GQuery.$;

import com.client.GameCanvas;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;

public class GameInterface {

	// ID of panel in sidebar currently showing
	// Or "" if sidebar is hidden
	private static String showing = "";

	private static String playerName; 
	private static GameCanvas canvas; // canvas of game, so camera can be turned off 
	private static int msgCount = 0; // keeps count of number of messages.
	private static boolean chatBoxHidden = true; // if chat box is hidden, changed when toggled. 
	
	/**
	 * Responsible for registering callbacks that are purely bound to the
	 * interface
	 */
	public static void init(GameCanvas c) {
		// Change sidebar left value to calculated value
		int width = $("#sidebar").outerWidth(true);
		//int closeWidth = $("#sidebar-hide").outerWidth(true);
		$("#sidebar").css("left", "-"+ width);
		initClickHandlers();
		Console.log("hello");
		
		// Upon init - if go with giant chat log 
		
		// set canvas to c
		canvas = c; 
	}
	
	/**
	 * Quick hack to set the players name in the chat based on login
	 * will need to implement a better way in the future.
	 */
	public static void setPlayerName(String n)
	{
		// if no name given, generate a random one
		if (n.equalsIgnoreCase(""))
		{
			int r = Random.nextInt(10000); 
			String numbString = NumberFormat.getFormat("####").format(r);
			playerName = "Guest" + numbString;
		} else
		{
			playerName = n; 
		}
	}

	/**
	 * Initializes all click handlers for the interface
	 */
	private static void initClickHandlers() {

		// City Menu Button
		// Callback to show cities-menu
		$("#cities-button").click(new Function() {
			public boolean f(Event e) {
				changeSidebarContent("cities-menu");
				// TODO: actual functionality
				// Get all cities from shallow model
				// Populate cities-menu
				return true; // Default return true
			}
		});

		// Agent Menu Button
		// Callback to show agent-menu
		$("#agent-button").click(new Function() {
			public boolean f(Event e) {
				// Show agent menu
				changeSidebarContent("agent-menu");
				// TODO: actual functionality
				// Get all agents from shallow model
				// Populate agent-menu
				return true; // Default return true
			}
		});

		// Diplomacy Menu Button
		// Callback to show diplomacy-menu
		$("#diplomacy-button").click(new Function() {
			public boolean f(Event e) {
				// Show diplomacy menu
				changeSidebarContent("diplomacy-menu");
				// TODO: actual functionality
				// Get all agreements from shallow model (or would this be a
				// call to server?)
				// Populate diplomacy-menu
				return true; // Default return true
			}
		});

		// Economies Menu Button
		// Callback to show economies-menu
		$("#economies-button").click(new Function() {
			public boolean f(Event e) {
				// Show economies menu
				changeSidebarContent("economies-menu");
				// TODO: actual functionality
				// Get all resource info from shallow model
				// Populate economies-menu
				return true; // Default return true
			}
		});

		// Agent Detail Button
		// Callback to show agent-menu-detail
		$(".agent-detail-button").click(new Function() {
			public boolean f(Event e) {
				// Show agent detail menu
				changeSidebarContent("agent-menu-detail");
				// TODO: actual functionality
				// Get agent ID from btn
				// Populate agent-menu-detail with info
				return true; // Default return true
			}
		});

		// Cities Detail Button
		// Callback to show cities-menu-detail
		$(".cities-detail-button").click(new Function() {
			public boolean f(Event e) {
				// Show cities detail menu
				changeSidebarContent("cities-menu-detail");
				// TODO: actual functionality
				// Get city ID from btn
				// Populate cities-menu-detail with info
				return true; // Default return true
			}
		});

		// Diplomacy Detail Button
		// Callback to show diplomacy-menu-detail
		$(".diplomacy-detail-button").click(new Function() {
			public boolean f(Event e) {
				// Show diplomacy detail menu
				changeSidebarContent("diplomacy-menu-detail");
				// TODO: actual functionality
				// Get agreement ID from btn
				// Populate diplomacy-menu-detail
				return true;
			}
		});
		
		// Economies Detail Button
		// Callback to show economies-menu-detail
		$(".economies-detail-button").click(new Function() {
			public boolean f(Event e) {
				// Show diplomacy detail menu
				changeSidebarContent("economies-menu-detail");
				// TODO: actual functionality
				// Get agreement ID from btn
				// Populate diplomacy-menu-detail
				return true;
			}
		});

		// Detail Return Button
		// Callback to 'return' from a detail or create panel
		// Name of panel to return to is in button's 'data-return' attribute
		// NOTE: $(this).data("return") didn't seem to work, even though it should,
		//	but this works just as well
		$(".detail-return").click(new Function() {
			public boolean f(Event e) {
				String returnTo = $(this).attr("data-return");
				changeSidebarContent(returnTo);
				return true;
			}
		});

		// Agent Create Button
		// Callback to show agent-menu-create
		$("#agent-create").click(new Function() {
			public boolean f(Event e) {
				// Show agent create menu
				changeSidebarContent("agent-menu-create");
				return true;
			}
		});

		// Diplomacy Create button
		// Callback to show diplomacy-menu-create
		$("#diplomacy-create").click(new Function() {
			public boolean f(Event e) {
				// Show diplomacy create menu
				changeSidebarContent("diplomacy-menu-create");
				return true;
			}
		});

		// Sidebar close/open
		/*
		$("#sidebar-hide").click(new Function() {
			public boolean f(Event e) {
				// Hide sidebar
				toggleSidebar(true);
				showing = "";
				return true; // Default return true
			}
		});
		*/

		// Chat Trigger Button
		// Callback to show/hide chat box
		$("#chat-trigger").click(new Function() {
			public boolean f(Event e) {
				// Hide/show chat
				$("#chat-box").slideToggle(500);
				chatBoxHidden = !chatBoxHidden; // toggle chatBoxHidden
				return true; // Default return true
			}
		});
		
		// Send Button
		// Currently writes message to the console, clears contents of 
		// chat- send box. 
		$("#send-message").click(new Function(){
			public boolean f(Event e)
			{
				return sendMessage();
			}
		});
		// Support pressing enter too
		$("#message").keypress(new Function(){
			public boolean f(Event e)
			{
				// If key pressed is enter, attempt to send message
				if (e.getKeyCode() == 13)
				{
					return sendMessage();
				}
				return true;
			}
		});
			
		// When message is in focus, disable input to game 
		$("#message").focus(new Function(){
			public boolean f(Event e)
			{
				Console.log("Message in Focus");
				canvas.turnOnChatFlag();
				return true;
			}
		});
		
		// When message loses focus, enable input to game
		$("#message").blur(new Function(){
			public boolean f(Event e)
			{
				Console.log("Message out of Focus");
				canvas.turnOffChatFlag();
				return true;
			}
		});
		
		// When scroll in messages if scroll to top, turn off new message text
		$("#messages").scroll(new Function()
		{
			public boolean f(Event e)
			{
				if ($("#messages").scrollTop() == 0)
				{
					Console.log("At the top of messages!");
					$("#chat-trigger").text("Chat");
					return true; 
				}
				Console.log("Scrolling in messages...");
				return false;
			}
		});
		
		// Menu Button
		// Callback to show/hide game menu
		$("#menu-button").click(new Function() {
			public boolean f(Event e) {
				// Hide/show menu
				$("#game-menu").fadeToggle(400);
				return true; // Default return true
			}
		});
		
		// Close Menu Button
		// Callback to hide game menu when it's open
		$("#close-menu").click(new Function() {
			public boolean f(Event e) {
				$("#game-menu").fadeToggle(400);
				return true; // Default return true
			}
		});
	}

	/**
	 * Method used to send messages from #message
	 * @return true if message sent, false if not
	 */
	private static boolean sendMessage()
	{
		// if Empty, don't send
		if ($("#message").val().equals(""))
		{
			return false; 
		}
		// Log to Console 
		Console.log(playerName + ": " + $("#message").val());

		// Send message - currently just local
		updateMessages(playerName + ": " + $("#message").val());
		
		// Scroll back up to the top of messages, wait a couple of ms
		Timer timer = new Timer()
		{
			@Override
			public void run() {
				$("#messages").scrollTop(0);
			}
			
		};
		timer.schedule(500); // wait a little bit to scroll up, wait for messages to be updated
		
		// Clear message line. 
		$("#message").val("");
		return true;
	}
	
	/**
	 * Appends a new message from server/local to the messages window 
	 * @param mssg - message to append, should ONLY contain message with users name with Users name. i.e. "Bob: gg" 
	 *
	 */
	public static void updateMessages(String mssg)
	{
		// Log to messages. 
		// based on message count, pick a color
		if (msgCount % 2 == 0)
		{
			$("#messages").prepend("<br />"+ mssg);
		} else
		{
			$("#messages").prepend("<br />");
			$("#messages").prepend("<font color=\"red\">"+ mssg + "</font>");
		}
		
		msgCount++; // increment message count, only matters locally. 
		
		// if not already at the top, don't indicate new message. Indicate it if chatBoxHidden is true though.
		if ($("#messages").scrollTop() != 0 || chatBoxHidden)
		{
			$("#chat-trigger").text("Chat - New Message!"); // Don't want for message you send yourself, get rid of when at scrollTop() = 0. 
		}
	}
	
	/**
	 * Will show/hide sidebar with animation
	 * @param	hideIfShowing	true/false if we want to hide the sidebar and it's showing
	 */
	private static void toggleSidebar(boolean hideIfShowing) {
		String left = $("#sidebar").css("left");
		int width = $("#sidebar").outerWidth(true);
		//int closeWidth = $("#sidebar-hide").outerWidth(true);
		if (left.equals("0px")) {
			// Hide only if param is true
			if (hideIfShowing) {
				$("#sidebar").stop().animate("left:-" + width);
				$("#chat-container").stop().animate("left:0");
			}
		} else {
			// Show sidebar
			$("#sidebar").stop().animate("left:0");
			$("#chat-container").stop().animate("left:" + width);
		}
	}

	/**
	 * Manages which content is being shown/hidden in sidebar
	 * Also handles sidebar toggling and sets the showing string
	 * @param	toShow 	which element with this ID to show, and hide the rest
	 */
	private static void changeSidebarContent(String toShow) {
		// If 'toShow' is already showing, hide the sidebar
		if (showing.equals(toShow)) {
			toggleSidebar(true);
			showing = "";

		} else {
			// Content is not showing, show it and hide everything else
			toggleSidebar(false);
			GQuery children = $("#sidebar").children();
			for (int i = 0; i < children.length(); i++) {
				String id = children.get(i).getAttribute("id");
				if (id.equals(toShow)) {
					// Always show sidebar-hide and the menu to show
					$("#" + id).show();
				} else {
					$("#" + id).hide();
				}
			}
			showing = toShow; // Change showing variable
		}
	}
}
