package com.client.gameinterface;

import static com.google.gwt.query.client.GQuery.$;

import java.util.HashMap;
import java.util.UUID;

import com.client.ClientModel;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;
import com.shared.model.buildings.Building;
import com.shared.model.control.Player;
import com.shared.model.resources.Resources;
import com.shared.model.units.Agent;

public class GameInterface {

	// ID of panel in sidebar currently showing
	// Or "" if sidebar is hidden
	private static String showing = "";
	private static ClientModel clientModel;
	private static Player me;

	/**
	 * Responsible for registering callbacks that are purely bound to the
	 * interface
	 */
	public static void init(ClientModel cm) {
		int playerID = 1;
		clientModel = cm;
		//me = clientModel.getPlayer(playerID);
		// Change sidebar left value to calculated value
		int width = $("#sidebar").outerWidth(true);
		//int closeWidth = $("#sidebar-hide").outerWidth(true);
		$("#sidebar").css("left", "-"+ width);
		initClickHandlers();
		Console.log("hello");
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
				// Clear out table
				$("#buildings-table tbody").empty();
				// Get all cities from client model
				// Populate cities-menu
				HashMap<UUID, Building> buildings = (HashMap<UUID, Building>) me.getGameObjects().getBuildings();
				for (UUID u : buildings.keySet()) {
					Building b = buildings.get(u);
					$("#buildings-table tbody").append("" +
						"<tr>" +
							"<td>" +
								"<div>" + b.getBuildingType().toString() + "</div>" +
								"<button type='button' data-id='" + u + "' class='btn btn-green cities-detail-button'>View</button>" +
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
		// Callback to show agent-menu
		$("#agent-button").click(new Function() {
			public boolean f(Event e) {
				// Show agent menu
				changeSidebarContent("agent-menu");
				// Clear out agents table
				$("#agents-table tbody").empty();
				// Get all agents from shallow model
				HashMap<UUID, Agent> agents = (HashMap<UUID, Agent>) me.getGameObjects().getAgents();
				for (UUID u : agents.keySet()) {
					Agent a = agents.get(u);
					$("#agents-table tbody").append("" +
						"<tr>" +
							"<td>" +
								"<div>" + a.getUnitType().toString() + "</div>" +
								"<button type='button' data-id='" + u + "' class='btn btn-green agent-detail-button'>View</button>" +
							"</td>" +
							"<td>" +
								"<button type='button' class='btn btn-green'>Goto</button>" +
							"</td>" +
						"</tr>"
					);
				}
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
				// Get all resource info from player
				Resources r = me.getResources();
				$("#economies-resource-info").html("" +
					"<div>Gold: " + r.getGold() + "</div>" +
					"<div>Wood: " + r.getWood() + "</div>" +
					"<div>Stone: " + r.getStone() + "</div>" +
					"<div>Food :" + r.getFood() + "</div>" +
					"<div>Research: " + r.getResearchPts() + "</div>"
				);
				// TODO: is there resource information for a building?
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
				// Get agent ID from btn
				UUID id = UUID.fromString($(this).attr("data-id"));
				// TODO: how to get agent by ID?
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
				// Get city ID from btn
				UUID id = UUID.fromString($(this).attr("data-id"));
				Building b = me.getBuilding(id);
				// Populate cities-menu-detail with info
				$("#cities-menu-detail #building-name").html("" +
						"<h2>" + b.getBuildingType().toString() + "</h2>"
				);
				$("#cities-menu-detail #building-data").html("" +
						"<div>Health: " + b.getHealth() + "</div>"
				);
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
		
		// Callback when 'Create' button is clicked on Agent Create Menu
		// Sends data to game model to create this agent
		$("#agent-create-confirm").click(new Function() {
			public boolean f(Event e) {
				// Collect all data from form
				String agentType = $("#agent-type").val();
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
