package com.client.gameinterface;

import static com.google.gwt.query.client.GQuery.$;

import java.util.HashMap;
import java.util.List;

import com.client.model.ClientModel;
import com.client.GameCanvas;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;
import com.shared.model.buildings.Building;
import com.shared.model.buildings.ResourceBuilding;
import com.shared.model.commands.BuildingProductionCommand;
import com.shared.model.commands.TradeCommand;
import com.shared.model.commands.SendMessageCommand;
import com.shared.model.control.GameModel;
import com.shared.model.control.Player;
import com.shared.model.diplomacy.trading.IntervalResourceTrade;
import com.shared.model.diplomacy.trading.interfaces.ITrade;
import com.shared.model.resources.Resources;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;

public class GameInterface {

	// ID of panel in sidebar currently showing
	// Or "" if sidebar is hidden
	private static String showing = "";
	private static ClientModel clientModel;
	private static GameModel gameModel;
	private static Player me;

	private static String playerName;
	private static GameCanvas canvas; // canvas of game, so camera can be turned
										// off
	private static int msgCount = 0; // keeps count of number of messages.
	private static boolean chatBoxHidden = true; // if chat box is hidden,
													// changed when toggled.

	/**
	 * Responsible for registering callbacks that are purely bound to the
	 * interface
	 */
	public static void init(ClientModel cm, GameCanvas c) {
		/*
		 * clientModel = cm; int playerID = clientModel.getPlayerID(); gameModel
		 * = clientModel.getGameModel(); // TODO: need to set me, this currently
		 * doesn't work // because playerID is never set in the ClientModel me =
		 * gameModel.getPlayer(playerID);
		 */

		// Change sidebar left value to calculated value
		int width = $("#sidebar").outerWidth(true);
		$("#sidebar").css("left", "-" + width);

		initClickHandlers();

		clientModel = cm;
		gameModel = null;
		// Needed to delay getting gameModel, because it wasn't set to the one
		// actually on the server.
		Timer tim = new Timer() {
			public void run() {
				gameModel = clientModel.getGameModel(); // needs to be delayed?
				int playerID = clientModel.getPlayerID();
				me = gameModel.getPlayer(playerID);
				// Upon init - if go with giant chat log, update chat to reflect
				// log
				initializeChat();
			}
		};
		tim.schedule(10000); // Delay ensures that gameModel is set to the one
								// on the server, not the placeholder in the
								// Client model

		// Schedule update from chat log at fixed rate.
		// Timer
		// TimerTask.
		Timer timer = new Timer() {

			@Override
			public void run() {
				updateChat();
			}

		};
		timer.scheduleRepeating(250); // Check for new messages every second.

		// set canvas to c
		canvas = c;
	}

	/**
	 * Quick hack to set the players name in the chat based on login will need
	 * to implement a better way in the future.
	 */
	public static void setPlayerName(String n) {
		// if no name given, generate a random one
		if (n.equalsIgnoreCase("")) {
			int r = Random.nextInt(10000);
			String numbString = NumberFormat.getFormat("####").format(r);
			playerName = "Guest" + numbString;
		} else {
			playerName = n;
		}
	}
	
	public static void setGameModel(GameModel gm) {
		gameModel = gm;
	}

	/**
	 * Initializes all click handlers for the interface
	 */
	private static void initClickHandlers() {

		// Building Menu Button
		// Callback to show buildings-menu
		$("#buildings-button").click(new Function() {
			public boolean f(Event e) {
				changeSidebarContent("buildings-menu");
				// Clear out table
				$("#buildings-table tbody").empty();
				// Get all buildings from client model
				// Populate buildings-menu
				HashMap<Integer, Building> buildings = (HashMap<Integer, Building>) me
						.getGameObjects().getBuildings();
				for (int u : buildings.keySet()) {
					Building b = buildings.get(u);
					$("#buildings-table tbody")
							.append(""
									+ "<tr>"
									+ "<td>"
									+ "<div>"
									+ b.getBuildingType().toString()
									+ "</div>"
									+ "<button type='button' data-id='"
									+ u
									+ "' class='btn btn-green buildings-detail-button'>View</button>"
									+ "</td>"
									+ "<td>"
									+ "<button type='button' class='btn btn-green'>Goto</button>"
									+ "</td>" + "</tr>");
				}

				return true; // Default return true
			}
		});

		// Agent Menu Button
		// Callback to show units-menu
		$("#units-button").click(new Function() {
			public boolean f(Event e) {
				// Show unit menu
				changeSidebarContent("units-menu");
				// Clear out units table
				$("#units-table tbody").empty();
				// Get all units from shallow model
				HashMap<Integer, Unit> units = (HashMap<Integer, Unit>) me
						.getGameObjects().getUnits();
				for (int u : units.keySet()) {
					Unit a = units.get(u);
					$("#units-table tbody")
							.append(""
									+ "<tr>"
									+ "<td>"
									+ "<div>"
									+ a.getUnitType().toString()
									+ "</div>"
									+ "<button type='button' data-id='"
									+ u
									+ "' class='btn btn-green units-detail-button'>View</button>"
									+ "</td>"
									+ "<td>"
									+ "<button type='button' class='btn btn-green'>Goto</button>"
									+ "</td>" + "</tr>");
				}
				// Populate units-menu
				return true; // Default return true
			}
		});

		// Diplomacy Menu Button
		// Callback to show diplomacy-menu
		$("#diplomacy-button").click(new Function() {
			public boolean f(Event e) {
				// Show diplomacy menu
				changeSidebarContent("diplomacy-menu");
				// Get all sent agreements
				List<ITrade> sentAgreements = gameModel.getTradeManager()
						.getSentTrades(me.getId());
				// Get all received agreements
				List<ITrade> receivedAgreements = gameModel.getTradeManager()
						.getReceivedTrades(me.getId());
				// Get all proposed agreements
				List<ITrade> acceptedAgreements = gameModel.getTradeManager()
						.getAcceptedTrades(me.getId());
				// Clear out tables
				$("#sent-agreements-table tbody").empty();
				$("#received-agreements-table tbody").empty();
				$("#accepted-agreements-table tbody").empty();
				// Re-populate sent
				for (int i = 0; i < sentAgreements.size(); i++) {
					$("#sent-agreements-table tbody")
							.append(""
									+ "<tr>"
									+ "<td>"
									+ "<div>To: "
									+ sentAgreements.get(i).getPlayer2()
									+ "</div>"
									+ "</td>"
									+ "<td>"
									+ "<button type='button' data-id='"
									+ sentAgreements.get(i).getId()
									+ "' data-type='sent' class='btn btn-green diplomacy-detail-button'>View</button>"
									+ "</td>" + "</tr>");
				}
				// Re-populate received
				for (int i = 0; i < receivedAgreements.size(); i++) {
					$("#received-agreements-table tbody")
							.append(""
									+ "<tr>"
									+ "<td>"
									+ "<div>From: "
									+ receivedAgreements.get(i).getPlayer1()
									+ "</div>"
									+ "</td>"
									+ "<td>"
									+ "<button type='button' data-id='"
									+ receivedAgreements.get(i).getId()
									+ "' data-type='received' class='btn btn-green diplomacy-detail-button'>View</button>"
									+ "</td>" + "</tr>");
				}
				// Re-populate proposed
				for (int i = 0; i < acceptedAgreements.size(); i++) {
					$("#sent-agreements-table tbody")
							.append(""
									+ "<tr>"
									+ "<td>"
									+ "<div>From: "
									+ acceptedAgreements.get(i).getPlayer1()
									+ "</div>"
									+ "</td>"
									+ "<td>"
									+ "<button type='button' data-id='"
									+ acceptedAgreements.get(i).getId()
									+ "' data-type='accepted' class='btn btn-green diplomacy-detail-button'>View</button>"
									+ "</td>" + "</tr>");
				}
				return true; // Default return true
			}
		});

		// Economies Menu Button
		// Callback to show economies-menu
		$("#economies-button").click(new Function() {
			public boolean f(Event e) {
				// Show economies menu
				changeSidebarContent("economies-menu");
				// Get all resource info from player
				Resources r = me.getResources();
				// Populate economies-menu
				$("#economies-resource-info").html(
						"" + "<div>Gold: " + r.getGold() + "</div>"
								+ "<div>Wood: " + r.getWood() + "</div>"
								+ "<div>Stone: " + r.getStone() + "</div>"
								+ "<div>Food :" + r.getFood() + "</div>"
								+ "<div>Research: " + r.getResearchPts()
								+ "</div>");
				// Clear out buildings table
				$("#economies-building-info tbody").empty();
				// Get all buildings and populate with only resource info
				HashMap<Integer, Building> buildings = me.getGameObjects()
						.getBuildings();
				for (int i : buildings.keySet()) {
					Building b = buildings.get(i);
					// We only want to display a building if it generates
					// resources
					if (b instanceof ResourceBuilding) {
						$("#economies-building-info tbody")
								.append("" + "<tr>" + "<td>" + "<div>"
										+ b.getBuildingType().toString()
										+ "</div>"
										+ "<button type='button' class='btn btn-green'>Goto</button>"
										+ "</td>"
										+ "<td>"
										+ "<div>Gold: "
										+ ((ResourceBuilding) b)
												.generateResource().getGold()
										+ "</div>"
										+ "<div>Wood: "
										+ ((ResourceBuilding) b)
												.generateResource().getWood()
										+ "</div>"
										+ "<div>Stone: "
										+ ((ResourceBuilding) b)
												.generateResource().getStone()
										+ "</div>"
										+ "<div>Food: "
										+ ((ResourceBuilding) b)
												.generateResource().getFood()
										+ "</div>"
										+ "<div>Research: "
										+ ((ResourceBuilding) b)
												.generateResource()
												.getResearchPts() + "</div>"
										+ "</td>" + "</tr>");
					}
				}

				return true; // Default return true
			}
		});

		// Unit Detail Button
		// Callback to show units-menu-detail
		$(".units-detail-button").click(new Function() {
			public boolean f(Event e) {
				// Show unit detail menu
				changeSidebarContent("units-menu-detail");
				// Get unit ID from btn
				int id = Integer.parseInt($(this).attr("data-id"));
				Unit u = me.getGameObjects().getUnits().get(id);
				// Clear out unit-info
				$("#unit-info").empty();
				// Populate units-menu-detail with info
				$("#unit-info").append(
						"" + "<div>Type: " + u.getUnitType().toString()
								+ "</div>" + "<div>Health: " + u.getHealth()
								+ "</div>" + "<div>Position: "
								+ u.getPosition().getX() + ", "
								+ u.getPosition().getY() + "</div>");
				return true; // Default return true
			}
		});

		// Buildings Detail Button
		// Callback to show buildings-menu-detail
		$(".buildings-detail-button").click(new Function() {
			public boolean f(Event e) {
				// Show buildings detail menu
				changeSidebarContent("buildings-menu-detail");
				// Get building ID from btn
				int id = Integer.parseInt($(this).attr("data-id"));
				Building b = me.getGameObjects().getBuildings().get(id);
				// Populate buildings-menu-detail with info
				$("#buildings-menu-detail #building-name").html(
						"" + "<h2>" + b.getBuildingType().toString() + "</h2>");
				$("#buildings-menu-detail #building-data").html(
						"" + "<div>Health: " + b.getHealth() + "</div>"
								+ "<div>Position: "
								+ (int) b.getPosition().getX() + ", "
								+ (int) b.getPosition().getY() + "</div>");
				if (b instanceof ResourceBuilding) {
					$("#buildings-menu-detail #building-data").append(
							""
									+ "<div>Resource Generation:</div>"
									+ "<div>Food: "
									+ ((ResourceBuilding) b).generateResource()
											.getFood()
									+ "</div>"
									+ "<div>Gold: "
									+ ((ResourceBuilding) b).generateResource()
											.getGold()
									+ "</div>"
									+ "<div>Stone: "
									+ ((ResourceBuilding) b).generateResource()
											.getStone()
									+ "</div>"
									+ "<div>Wood: "
									+ ((ResourceBuilding) b).generateResource()
											.getWood()
									+ "</div>"
									+ "<div>Research: "
									+ ((ResourceBuilding) b).generateResource()
											.getResearchPts() + "</div>");
				}
				// Set building-id value
				$("#buildings-menu-detail #building-id").val("" + id);
				return true; // Default return true
			}
		});

		// Diplomacy Detail Button
		// Callback to show diplomacy-menu-detail
		$(".diplomacy-detail-button").click(new Function() {
			public boolean f(Event e) {
				// Show diplomacy detail menu
				changeSidebarContent("diplomacy-menu-detail");
				// Get agreement ID and type from btn
				int id = Integer.parseInt($(this).attr("data-id"));
				String type = $(this).attr("data-type");
				// Get Trade info (we're assuming right now it's a
				// IntervalResourceTrade
				IntervalResourceTrade t = (IntervalResourceTrade) gameModel
						.getTradeManager().getTrade(id);
				// Clear out old info
				$("#diplomacy-detail-info").empty();
				// Figure out what is going where
				int tradingWith;
				Resources theyGet, youGet;
				if (t.getCreatingPlayer() == me.getId()) {
					// 'me' created the trade, either sent or accepted
					tradingWith = t.getReceivingPlayer();
					theyGet = t.getReceivingPlayerResources();
					youGet = t.getCreatingPlayerResources();
				} else {
					// someone else created the trade, either received or
					// accepted
					tradingWith = t.getCreatingPlayer();
					theyGet = t.getCreatingPlayerResources();
					youGet = t.getReceivingPlayerResources();
				}
				// Re-populate info
				$("#diplomacy-detail-append").append(
						"" + "<div>Trade With " + tradingWith + "</div>"
								+ "<div>They Receive: "
								+ theyGet.toStringOneLine() + "</div>"
								+ "<div>You Receive: "
								+ youGet.toStringOneLine() + "</div>"
								+ "<div>Time Remaining: "
								+ t.getTimeRemaining() + " minutes</div>");
				if (type.equals("sent") || type.equals("accepted")) {
					// Hide accept/decline controls
					$("#diplomacy-accept-trade").hide();
					$("#diplomacy-decline-trade").hide();
				} else {
					// Is a received trade, show accept/decline controls
					$("#diplomacy-accept-trade").show();
					$("#diplomacy-decline-trade").show();
				}

				return true; // Default return true
			}
		});

		// Detail Return Button
		// Callback to 'return' from a detail or create panel
		// Name of panel to return to is in button's 'data-return' attribute
		// NOTE: $(this).data("return") didn't seem to work, even though it
		// should,
		// but this works just as well
		$(".detail-return").click(new Function() {
			public boolean f(Event e) {
				String returnTo = $(this).attr("data-return");
				changeSidebarContent(returnTo);
				return true;
			}
		});

		// Agent Create Button
		// Callback to show units-menu-create
		/*
		 * $("#units-create").click(new Function() { public boolean f(Event e) {
		 * // Show unit create menu changeSidebarContent("units-menu-create");
		 * return true; } });
		 */

		/*
		 * // Callback when 'Create' button is clicked on Agent Create Menu //
		 * Sends data to game model to create this unit
		 * $("#units-create-confirm").click(new Function() { public boolean
		 * f(Event e) { // Collect all data from form String unitType =
		 * $("#unit-type").val(); // Create command // Send command to
		 * Controller // Clear out form return true; } });
		 */

		// Callback to create a unit from a buildings-menu-detail
		// Sends data through client model to create this unit
		$("#buildings-detail-create-unit").click(new Function() {
			public boolean f(Event e) {
				String unitType = $("#buildings-menu-detail #unit-type").val();
				int buildingID = Integer.parseInt($(
						"#buildings-menu-detail #building-id").val());
				// Send command through clientModel
				clientModel.sendCommand(new BuildingProductionCommand(
						buildingID, UnitType.valueOf(unitType.toUpperCase())));
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

		// Callback to send a trade agreement
		$("#diplomacy-create-confirm").click(new Function() {
			public boolean f(Event e) {
				String toUser = $("#diplomacy-send-user").val();
				String resourceSend = $("#diplomacy-send-type").val();
				int resourceSendQuantity = Integer.parseInt($(
						"#diplomacy-send-quantity").val());
				String resourceReceive = $("#diplomacy-receive-type").val();
				int resourceReceiveQuantity = Integer.parseInt($(
						"#diplomacy-receive-quantity").val());
				int tradeExpire = Integer
						.parseInt($("#diplomacy-expire").val());
				// TODO: error check entered values (not important right now)
				// Create the Resources objects
				Resources sending = new Resources();
				Resources receiving = new Resources();
				switch (resourceSend) {
				case "Food":
					sending.setFood(resourceSendQuantity);
					break;
				case "Stone":
					sending.setStone(resourceSendQuantity);
					break;
				case "Wood":
					sending.setWood(resourceSendQuantity);
					break;
				case "Gold":
					sending.setGold(resourceSendQuantity);
					break;
				}
				switch (resourceReceive) {
				case "Food":
					receiving.setFood(resourceReceiveQuantity);
					break;
				case "Stone":
					receiving.setStone(resourceReceiveQuantity);
					break;
				case "Wood":
					receiving.setWood(resourceReceiveQuantity);
					break;
				case "Gold":
					receiving.setGold(resourceReceiveQuantity);
					break;
				}
				// Create and send command
				// TODO: need to get ID of other player
				clientModel.sendCommand(new TradeCommand(tradeExpire, me
						.getId(), 0, sending, receiving));
				return true;
			}
		});

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
		$("#send-message").click(new Function() {
			public boolean f(Event e) {
				return sendMessage();
			}
		});
		// Support pressing enter too
		$("#message").keypress(new Function() {
			public boolean f(Event e) {
				// If key pressed is enter, attempt to send message
				if (e.getKeyCode() == 13) {
					return sendMessage();
				}
				return true;
			}
		});

		// When message is in focus, disable input to game
		$("#message").focus(new Function() {
			public boolean f(Event e) {
				Console.log("Message in Focus");
				canvas.turnOnChatFlag();
				return true;
			}
		});

		// When message loses focus, enable input to game
		$("#message").blur(new Function() {
			public boolean f(Event e) {
				Console.log("Message out of Focus");
				canvas.turnOffChatFlag();
				return true;
			}
		});

		// When scroll in messages if scroll to top, turn off new message text
		$("#messages").scroll(new Function() {
			public boolean f(Event e) {
				if ($("#messages").scrollTop() == 0) {
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

	}// End initClickHandlers

	/**
	 * Method used to send messages from #message
	 * 
	 * @return true if message sent, false if not
	 */
	private static boolean sendMessage() {
		// if Empty, don't send
		if ($("#message").val().equals("")) {
			return false;
		}
		// Log to Console
		Console.log(playerName + ": " + $("#message").val());

		// Send message - currently just local
		// updateMessages(playerName + ": " + $("#message").val());
		clientModel.sendCommand(new SendMessageCommand(playerName + ": "
				+ $("#message").val()));
		// Console.log("Chat Command sent to server ");

		// Scroll back up to the top of messages, wait a couple of ms
		Timer timer = new Timer() {
			@Override
			public void run() {
				$("#messages").scrollTop(0);
			}

		};
		timer.schedule(500); // wait a little bit to scroll up, wait for
								// messages to be updated

		// Clear message line.
		$("#message").val("");
		return true;
	}

	/**
	 * Appends a new message from server/local to the messages window
	 * 
	 * @param mssg
	 *            - message to append, should ONLY contain message with users
	 *            name with Users name. i.e. "Bob: gg"
	 * 
	 */
	public static void updateMessages(String mssg) {
		// Log to messages.
		// based on message count, pick a color
		if (msgCount % 2 == 0) {
			$("#messages").prepend("<br />" + mssg);
		} else {
			$("#messages").prepend("<br />");
			$("#messages").prepend("<font color=\"red\">" + mssg + "</font>");
		}

		msgCount++; // increment message count, only matters locally.

		// if not already at the top, don't indicate new message. Indicate it if
		// chatBoxHidden is true though.
		if ($("#messages").scrollTop() != 0 || chatBoxHidden) {
			$("#chat-trigger").text("Chat - New Message!"); // Don't want for
															// message you send
															// yourself, get rid
															// of when at
															// scrollTop() = 0.
		}
	}

	/**
	 * After chat has been initialized, need to update it to see new messages,
	 * prepend any new messages to messages.
	 */
	public static void updateChat() {
		// TODO add call, if length is even, and greater than size, reset chat
		// log in gameModel.
		// But for now who cares, ALL TEH MEMORY IS MINE.
		Console.log(gameModel.toString());
		if (gameModel == null)
			return;

		int chatLogLength = gameModel.getChatLog().size();
		if (chatLogLength > msgCount) {
			// update chat with new messages - msgCount is updated by
			// updateMessages call.
			for (int i = msgCount; i < chatLogLength; i++) {
				// Console.log("msgCount: "+ msgCount + "\tchatLogLength: "+
				// chatLogLength + "i: " + i);
				updateMessages(gameModel.getChatLog().get(i));
			}
		} else {
			// Console.log("No new Messages to prepend");
		}
	}

	/**
	 * initializes the chat based on contents of chat log.
	 */
	public static void initializeChat() {
		if (gameModel.getChatLog().size() == 0)
			Console.log("No chats in the chat log!");

		for (String msg : gameModel.getChatLog()) {
			updateMessages(msg);
		}
	}

	/**
	 * Will show/hide sidebar with animation
	 * 
	 * @param hideIfShowing
	 *            true/false if we want to hide the sidebar and it's showing
	 */
	private static void toggleSidebar(boolean hideIfShowing) {
		String left = $("#sidebar").css("left");
		int width = $("#sidebar").outerWidth(true);
		// int closeWidth = $("#sidebar-hide").outerWidth(true);
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
	 * Manages which content is being shown/hidden in sidebar Also handles
	 * sidebar toggling and sets the showing string
	 * 
	 * @param toShow
	 *            which element with this ID to show, and hide the rest
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
