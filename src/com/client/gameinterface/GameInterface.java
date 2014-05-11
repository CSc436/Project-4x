package com.client.gameinterface;

import static com.google.gwt.query.client.GQuery.$;

import java.util.HashMap;
import java.util.List;

import com.client.GameCanvas;
import com.client.model.ClientController;
import com.google.gwt.dom.client.Document;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.shared.model.buildings.Building;
import com.shared.model.buildings.ResourceBuilding;
import com.shared.model.commands.AddPlayerCommand;
import com.shared.model.commands.BuildingProductionCommand;
import com.shared.model.commands.SendMessageCommand;
import com.shared.model.commands.TradeCommand;
import com.shared.model.control.GameModel;
import com.shared.model.control.Player;
import com.shared.model.diplomacy.trading.IntervalResourceTrade;
import com.shared.model.diplomacy.trading.interfaces.ITrade;
import com.shared.model.entities.GameObject;
import com.shared.model.resources.Resources;
import com.shared.model.units.Unit;
import com.shared.model.units.UnitType;

public class GameInterface {

	// ID of panel in sidebar currently showing
	// Or "" if sidebar is hidden
	private static String showing = "";
	private static ClientController clientModel;
	private static GameModel gameModel;

	private static Player me;
	private static String playerName;
	private static int playerID;

	private static GameCanvas canvas; // canvas of game, so camera can be turned
										// off
	private static int msgCount = 0; // keeps count of number of messages.
	private static boolean chatBoxHidden = true; // if chat box is hidden,
													// changed when toggled.

	/**
	 * Responsible for registering callbacks that are purely bound to the
	 * interface
	 */
	public static void init(ClientController cm, GameCanvas c) {
		// Change sidebar left value to calculated value
		int width = $("#sidebar").outerWidth(true);
		$("#sidebar").css("left", "-" + width);

		initClickHandlers();

		clientModel = cm;
		gameModel = null;

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
		// Put name in toolbar
		$("#username-space span").html("Logged in as: " + playerName);
	}

	/**
	 * Sets the player ID for this class, which was generated in the Simple
	 * Simulator and is also for the Game Model
	 * 
	 * @param id
	 *            - the ID for this player
	 */
	public static void setPlayerID(int id) {
		playerID = id;
	}

	public static String getInfo(int id) {
		if (me.getGameObjects().getUnits().containsKey(id))
			return getUnitInfo(me.getGameObjects().getUnits().get(id));
		else
			return getBuildingInfo(me.getGameObjects().getBuildings().get(id));
	}

	private static String getUnitInfo(Unit u) {
		return "" + "<div>Type: " + u.getUnitType().toString() + "</div>"
				+ "<div>Health: " + u.getHealth() + "</div>"
				+ "<div>Position: " + u.getPosition().getX() + ", "
				+ u.getPosition().getY() + "</div>";
	}

	private static String getBuildingInfo(Building b) {
		String info = "";

		info += "" + "<h2>" + b.getBuildingType().toString() + "</h2>";
		info += "<div>Health: " + b.getHealth() + "</div>" + "<div>Position: "
				+ (int) b.getPosition().getX() + ", "
				+ (int) b.getPosition().getY() + "</div>";

		return info;
	}

	/**
	 * Sets the game model for this class
	 * 
	 * This is called from ClientModel.java when the GameModel is first loaded
	 * 
	 * Using this eliminates the need for a delay to wait; the loading screen
	 * doesn't disappear until the game model is loaded anyways
	 * 
	 * @param gm
	 *            - The GameModel object retrieved from the server
	 */
	public static void setGameModel(GameModel gm) {
		Console.log("setting game model");
		gameModel = gm;

		// Schedule update from chat log at fixed rate
		Console.log("starting timer for chat update");
		Timer timer = new Timer() {

			public void run() {
				updateChat();
			}

		};
		timer.scheduleRepeating(250); // Check for new messages every 1/4th
										// second.

		// Add the Player object to the game model
		Console.log(clientModel.toString());
		clientModel.sendCommand(new AddPlayerCommand(playerName, playerID));
		
		Console.log("getting current players...");
		HashMap<Integer, Player> tempPlayers = gameModel.getPlayers();
		for (Integer i : tempPlayers.keySet()) {
			Console.log("Player: " + tempPlayers.get(i).getAlias());
		}

		Timer playerTimer = new Timer() {

			public void run() {
				// TODO: this could just be getPlayer(id)
				me = gameModel.getPlayerByUsername(playerName);
				if (me == null) {
					// Failed to retrieve Player object
					Console.log("me was null");
					Console.log("getting current players...");
					HashMap<Integer, Player> tempPlayers = gameModel
							.getPlayers();
					for (Integer i : tempPlayers.keySet()) {
						Console.log("Player: " + tempPlayers.get(i).getAlias());
					}
					this.schedule(1000);
				} else {
					Console.log("got player instance for " + playerName);
					// Share player ID with the game canvas
					canvas.setPlayerID(playerID);
				}
			}

		};
		playerTimer.schedule(1000); // Keep trying to get the player every
									// second
		startResourcesTimer();

	}

	/**
	 * Creates a timer to continually update the resources in the toolbar
	 * Updates every second
	 */
	public static void startResourcesTimer() {
		Timer resourcesTimer = new Timer() {

			public void run() {
				Resources r = me.getResources();
				$("#toolbar-gold").html(" " + r.getGold() + " ");
				$("#toolbar-wood").html(" " + r.getWood() + " ");
				$("#toolbar-food").html(" " + r.getFood() + " ");
				$("#toolbar-stone").html(" " + r.getStone() + " ");
				$("#toolbar-research").html(" " + r.getResearchPts() + " ");
			}
		};
		resourcesTimer.scheduleRepeating(1000);
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
					Console.log(b.getBuildingType().toString());
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

		// Units Menu Button
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
									+ sentAgreements.get(i)
											.getReceivingPlayer()
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
									+ receivedAgreements.get(i)
											.getCreatingPlayer()
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
									+ acceptedAgreements.get(i)
											.getCreatingPlayer()
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
								+ "<div>Food: " + r.getFood() + "</div>"
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
		// $(".units-detail-button").click(new Function() {
		$(Document.get()).on("click", ".units-detail-button", new Function() {
			public boolean f(Event e) {
				Console.log("units-detail-button");
				// Show unit detail menu
				changeSidebarContent("units-menu-detail");
				// Get unit ID from btn
				int id = Integer.parseInt($(this).attr("data-id"));
				// Clear out unit-info
				$("#unit-info").empty();
				// Populate units-menu-detail with info
				$("#unit-info").append(GameInterface.getInfo(id));
				return true; // Default return true
			}
		});

		// Buildings Detail Button
		// Callback to show buildings-menu-detail
		$(Document.get()).on("click", ".buildings-detail-button",
				new Function() {
					public boolean f(Event e) {
						// Show buildings detail menu
						changeSidebarContent("buildings-menu-detail");
						// Get building ID from btn
						int id = Integer.parseInt($(this).attr("data-id"));
						Building b = me.getGameObjects().getBuildings().get(id);
						// Populate buildings-menu-detail with info
						$("#buildings-menu-detail #building-name").html(
								"" + "<h2>" + b.getBuildingType().toString()
										+ "</h2>");
						$("#buildings-menu-detail #building-data").html(
								"" + "<div>Health: " + b.getHealth() + "</div>"
										+ "<div>Position: "
										+ (int) b.getPosition().getX() + ", "
										+ (int) b.getPosition().getY()
										+ "</div>");
						// Set building-id value
						$("#buildings-menu-detail #building-id").val("" + id);
						if (b instanceof ResourceBuilding) {
							$("#buildings-menu-detail #building-data").append(
									""
											+ "<div>Resource Generation:</div>"
											+ "<div>Food: "
											+ ((ResourceBuilding) b)
													.generateResource()
													.getFood()
											+ "</div>"
											+ "<div>Gold: "
											+ ((ResourceBuilding) b)
													.generateResource()
													.getGold()
											+ "</div>"
											+ "<div>Stone: "
											+ ((ResourceBuilding) b)
													.generateResource()
													.getStone()
											+ "</div>"
											+ "<div>Wood: "
											+ ((ResourceBuilding) b)
													.generateResource()
													.getWood()
											+ "</div>"
											+ "<div>Research: "
											+ ((ResourceBuilding) b)
													.generateResource()
													.getResearchPts()
											+ "</div>");
						}
						return true; // Default return true
					}
				});

		// Diplomacy Detail Button
		// Callback to show diplomacy-menu-detail
		$(Document.get()).on("click", ".diplomacy-detail-button",
				new Function() {
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
							// someone else created the trade, either received
							// or
							// accepted
							tradingWith = t.getCreatingPlayer();
							theyGet = t.getCreatingPlayerResources();
							youGet = t.getReceivingPlayerResources();
						}
						// Re-populate info
						$("#diplomacy-detail-append").append(
								"" + "<div>Trade With " + tradingWith
										+ "</div>" + "<div>They Receive: "
										+ theyGet.toStringOneLine() + "</div>"
										+ "<div>You Receive: "
										+ youGet.toStringOneLine() + "</div>"
										+ "<div>Time Remaining: "
										+ t.getTimeRemaining()
										+ " minutes</div>");
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
				// See if other player exists
				Player otherPlayerObj = gameModel.getPlayerByUsername(toUser);
				if (otherPlayerObj != null) {
					// Create and send command
					clientModel.sendCommand(new TradeCommand(tradeExpire, me
							.getId(), otherPlayerObj.getId(), sending,
							receiving));
				}
				return true; // Return true for click callback
			}
		});
		
		// Toggle camera flag when typing for diplomacy-send-user
		$("#diplomacy-send-user").focus(new Function() {
			public boolean f(Event e) {
				canvas.turnOnChatFlag();
				return true;
			}
		});
		
		// Toggle camera flag when not typing in diplomacy-send-user
		$("#diplomacy-send-user").blur(new Function() {
			public boolean f(Event e) {
				canvas.turnOffChatFlag();
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
				if (!chatBoxHidden) // if chat box no longer hidden, makes sure
									// chat-trigger text is just "Chat"
				{
					Console.log("Chat Box is not hidden, setting text");
					$("#chat-trigger").text("Chat");
				}
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
					$("#chat-trigger").text("Chat");
					return true;
				}
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
				+ $("#message").val(), clientModel.getPlayerID()));
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
	 * @param id
	 *            Used to pick a color for the player
	 * 
	 */
	public static void updateMessages(String mssg, int id) {
		// Log to messages.

		int numberOfColors = 10; // number of colors supported
		switch (id % numberOfColors) {
		case 0:
			// Use default color
			$("#messages").prepend("<br />" + mssg);
			break;
		case 1:
			$("#messages").prepend("<br />");
			$("#messages").prepend(
					"<font color=\"#F0F8FF\">" + mssg + "</font>");
			break;
		case 2:
			$("#messages").prepend("<br />");
			$("#messages").prepend(
					"<font color=\"#7FFF00\">" + mssg + "</font>");
			break;
		case 3:
			$("#messages").prepend("<br />");
			$("#messages").prepend(
					"<font color=\"#00FFFF\">" + mssg + "</font>");
			break;
		case 4:
			$("#messages").prepend("<br />");
			$("#messages").prepend(
					"<font color=\"#FF1493\">" + mssg + "</font>");
			break;
		case 5:
			$("#messages").prepend("<br />");
			$("#messages").prepend(
					"<font color=\"#FFD700\">" + mssg + "</font>");
			break;
		case 6:
			$("#messages").prepend("<br />");
			$("#messages").prepend(
					"<font color=\"#FF0000\">" + mssg + "</font>");
			break;
		case 7:
			$("#messages").prepend("<br />");
			$("#messages").prepend(
					"<font color=\"#F5DEB3\">" + mssg + "</font>");
			break;
		case 8:
			$("#messages").prepend("<br />");
			$("#messages").prepend(
					"<font color=\"#00FF7F\">" + mssg + "</font>");
			break;
		case 9:
			$("#messages").prepend("<br />");
			$("#messages").prepend(
					"<font color=\"#87CEEB\">" + mssg + "</font>");
			break;
		default:
			// Use default color
			$("#messages").prepend("<br />" + mssg);
			break;
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
		if (gameModel == null)
			return;

		int chatLogLength = gameModel.getChatLog().size();
		if (chatLogLength > msgCount) {
			// update chat with new messages - msgCount is updated by
			// updateMessages call.
			for (int i = msgCount; i < chatLogLength; i++) {
				updateMessages(gameModel.getChatLog().get(i).getMessage(),
						gameModel.getChatLog().get(i).getPlayerID());
			}
		} else if (msgCount > chatLogLength) {
			// msgCount is greater, chatLog has been destroyed, reinitialize
			// chat.
			msgCount = 0; // reset message count
			initializeChat(); // initialize chat again.
		}
	}

	/**
	 * initializes the chat based on contents of chat log.
	 */
	public static void initializeChat() {
		if (gameModel.getChatLog().size() == 0)
			Console.log("No chats in the chat log!");

		for (SendMessageCommand msg : gameModel.getChatLog()) {
			updateMessages(msg.getMessage(), msg.getPlayerID());
		}
	}

	/**
	 * Will show/hide sidebar with animation
	 * 
	 * @param hideIfShowing
	 *            true/false if we want to hide the sidebar and it's showing
	 */
	public static void toggleSidebar(boolean hideIfShowing) {
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
