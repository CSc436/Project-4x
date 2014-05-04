package com.client.gameinterface;

import static com.google.gwt.query.client.GQuery.$;

import java.util.HashMap;

import com.client.model.ClientModel;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;
import com.shared.model.buildings.Building;
import com.shared.model.buildings.ResourceBuilding;
import com.shared.model.control.GameModel;
import com.shared.model.control.Player;
import com.shared.model.resources.Resources;
import com.shared.model.units.Agent;
import com.shared.model.units.Unit;

public class GameInterface {

	// ID of panel in sidebar currently showing
	// Or "" if sidebar is hidden
	private static String showing = "";
	private static GameModel gameModel;
	private static Player me;

	/**
	 * Responsible for registering callbacks that are purely bound to the
	 * interface
	 */
	public static void init(ClientModel cm) {
		int playerID = cm.getPlayerID();
		gameModel = cm.getGameModel();
		// TODO: need to set me
		me = gameModel.getPlayer(playerID);
		// Change sidebar left value to calculated value
		int width = $("#sidebar").outerWidth(true);
		// int closeWidth = $("#sidebar-hide").outerWidth(true);
		$("#sidebar").css("left", "-" + width);
		initClickHandlers();
		Console.log("hello");
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
				HashMap<Integer, Building> buildings = (HashMap<Integer, Building>) me.getGameObjects().getBuildings();
				for (int u : buildings.keySet()) {
					Building b = buildings.get(u);
					$("#buildings-table tbody").append("" +
						"<tr>" +
							"<td>" +
								"<div>" + b.getBuildingType().toString() + "</div>" +
								"<button type='button' data-id='" + u + "' class='btn btn-green buildings-detail-button'>View</button>" +
							"</td>" +
							"<td>" +
								"<button type='button' class='btn btn-green'>Goto</button>" +
							"</td>" +
						"</tr>"
					);
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
				HashMap<Integer, Unit> units = (HashMap<Integer, Unit>) me.getGameObjects().getUnits();
				for (int u : units.keySet()) {
					Unit a = units.get(u);
					$("#units-table tbody").append("" +
						"<tr>" +
							"<td>" +
								"<div>" + a.getUnitType().toString() + "</div>" +
								"<button type='button' data-id='" + u + "' class='btn btn-green units-detail-button'>View</button>" +
							"</td>" +
							"<td>" +
								"<button type='button' class='btn btn-green'>Goto</button>" +
							"</td>" +
						"</tr>"
					);
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
				// Get all resource info from player
				Resources r = me.getResources();
				// Populate economies-menu
				$("#economies-resource-info").html("" +
					"<div>Gold: " + r.getGold() + "</div>" +
					"<div>Wood: " + r.getWood() + "</div>" +
					"<div>Stone: " + r.getStone() + "</div>" +
					"<div>Food :" + r.getFood() + "</div>" +
					"<div>Research: " + r.getResearchPts() + "</div>"
				);
				// Clear out buildings table
				$("#economies-building-info tbody").empty();
				// Get all buildings and populate with only resource info
				HashMap<Integer, Building> buildings = me.getGameObjects().getBuildings();
				for(int i : buildings.keySet()) {
					Building b = buildings.get(i);
					// We only want to display a building if it generates resources
					if (b instanceof ResourceBuilding) {
						$("#economies-building-info tbody").append("" + 
							"<tr>" +
								"<td>" +
									"<div>" + b.getBuildingType().toString() + "</div>" +
									"<button type='button' class='btn btn-green'>Goto</button>" +
								"</td>" +
								"<td>" +
									"<div>Gold: " + ((ResourceBuilding) b).generateResource().getGold() + "</div>" +
									"<div>Wood: " + ((ResourceBuilding) b).generateResource().getWood() + "</div>" +
									"<div>Stone: " + ((ResourceBuilding) b).generateResource().getStone() + "</div>" +
									"<div>Food: " + ((ResourceBuilding) b).generateResource().getFood() + "</div>" +
									"<div>Research: " + ((ResourceBuilding) b).generateResource().getResearchPts() + "</div>" +
								"</td>" +
							"</tr>");
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
				$("#unit-info").append("" +
						"<div>Type: " + u.getUnitType().toString() + "</div>" +
						"<div>Health: " + u.getHealth() + "</div>" +
						"<div>Position: " + u.getPosition().getX()  + ", " + u.getPosition().getY() + "</div>"
				);
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
				$("#buildings-menu-detail #building-name").html("" +
						"<h2>" + b.getBuildingType().toString() + "</h2>"
				);
				$("#buildings-menu-detail #building-data").html("" +
						"<div>Health: " + b.getHealth() + "</div>" +
						"<div>Position: " + (int) b.getPosition().getX() + ", " + (int) b.getPosition().getY() + "</div>"
				);
				if (b instanceof ResourceBuilding) {
					$("#buildings-menu-detail #building-data").append("" +
						"<div>Resource Generation:</div>" +
						"<div>Food: " + ((ResourceBuilding) b).generateResource().getFood() + "</div>" +
						"<div>Gold: " + ((ResourceBuilding) b).generateResource().getGold() + "</div>" +
						"<div>Stone: " + ((ResourceBuilding) b).generateResource().getStone() + "</div>" +
						"<div>Wood: " + ((ResourceBuilding) b).generateResource().getWood() + "</div>" +
						"<div>Research: " + ((ResourceBuilding) b).generateResource().getResearchPts() + "</div>"
					);
				}
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
		// Callback to show units-menu-create
		$("#units-create").click(new Function() {
			public boolean f(Event e) {
				// Show unit create menu
				changeSidebarContent("units-menu-create");
				return true;
			}
		});
		
		// Callback when 'Create' button is clicked on Agent Create Menu
		// Sends data to game model to create this unit
		$("#units-create-confirm").click(new Function() {
			public boolean f(Event e) {
				// Collect all data from form
				String unitType = $("#unit-type").val();
				// Create command
				// Send command to Controller
				// Clear out form
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

		// Chat Trigger Button
		// Callback to show/hide chat box
		$("#chat-trigger").click(new Function() {
			public boolean f(Event e) {
				// Hide/show chat
				$("#chat-box").slideToggle(500);
				return true; // Default return true
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
		
	} // End initClickHandlers

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
