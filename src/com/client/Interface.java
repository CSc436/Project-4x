package com.client;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;

public class Interface {

	private static String showing = "";

	/**
	 * Responsible for registering callbacks that are purely bound to the
	 * interface
	 */
	public static void init() {
		// Change sidebar left value to calculated value
		int width = $("#sidebar").outerWidth(true);
		int closeWidth = $("#sidebar-hide").outerWidth(true);
		$("#sidebar").css("left", "-"+ (width + closeWidth));
		initClickHandlers();
		Console.log("hello");
	}

	/**
	 * Initializes all click handlers for the interface
	 */
	private static void initClickHandlers() {

		// City Menu Button
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

		$(".agent-view-button").click(new Function() {
			public boolean f(Event e) {
				// Show agent detail menu
				changeSidebarContent("agent-menu-detail");
				// TODO: actual functionality
				// Get agent ID from btn
				// Populate agent-menu-detail with info
				return true; // Default return true
			}
		});

		$(".cities-view-button").click(new Function() {
			public boolean f(Event e) {
				// Show cities detail menu
				changeSidebarContent("cities-menu-detail");
				// TODO: actual functionality
				// Get city ID from btn
				// Populate cities-menu-detail with info
				return true; // Default return true
			}
		});

		$(".diplomacy-view-button").click(new Function() {
			public boolean f(Event e) {
				// Show diplomacy detail menu
				changeSidebarContent("diplomacy-menu-detail");
				// TODO: actual functionality
				// Get agreement ID from btn
				// Populate diplomacy-menu-detail
				return true;
			}
		});

		$(".detail-return").click(new Function() {
			public boolean f(Event e) {
				// Responsible for returning from ALL detail menus to list menus
				// Gets name of menu to return to from it's data-return attribue
				/*
				if (showing.equals("agent-menu-detail") || showing.equals("agent-menu-create")) {
					changeSidebarContent("agent-menu");
				} else if (showing.equals("diplomacy-menu-detail") || showing.equals("diplomacy-menu-create")) {
					changeSidebarContent("diplomacy-menu");
				} else if (showing.equals("cities-menu-detail")) {
					changeSidebarContent("cities-menu");
				} else if (showing.equals("economies-menu-detail")) {
					changeSidebarContent("economies-menu");
				}
				*/
				String returnTo = $(this).attr("data-return");
				changeSidebarContent(returnTo);
				return true;
			}
		});

		$("#agent-create").click(new Function() {
			public boolean f(Event e) {
				// Show agent create menu
				changeSidebarContent("agent-menu-create");
				return true;
			}
		});

		$("#diplomacy-create").click(new Function() {
			public boolean f(Event e) {
				// Show diplomacy create menu
				changeSidebarContent("diplomacy-menu-create");
				return true;
			}
		});

		// Sidebar close/open
		$("#sidebar-hide").click(new Function() {
			public boolean f(Event e) {
				// Hide sidebar
				toggleSidebar(true);
				showing = "";
				return true; // Default return true
			}
		});

		$("#chat-trigger").click(new Function() {
			public boolean f(Event e) {
				// Hide/show chat
				$("#chat-box").slideToggle(100);
				return true; // Default return true;
			}
		});
		
		$("#menu-button").click(new Function() {
			public boolean f(Event e) {
				// Hide/show menu
				$("#game-menu").fadeToggle(400);
				return true;
			}
		});
		
		$("#close-menu").click(new Function() {
			public boolean f(Event e) {
				$("#game-menu").fadeToggle(400);
				return true;
			}
		});
		
	}

	/**
	 * Will show/hide sidebar with animation hideIfShowing: whether or not close
	 * the sidebar if it's showing
	 */
	private static void toggleSidebar(boolean hideIfShowing) {
		String left = $("#sidebar").css("left");
		int width = $("#sidebar").outerWidth(true);
		int closeWidth = $("#sidebar-hide").outerWidth(true);
		if (left.equals("0px")) {
			// Hide only if param is true
			if (hideIfShowing) {
				$("#sidebar").stop().animate("left:-" + (width + closeWidth));
				$("#chat-container").stop().animate("left:0");
			}
		} else {
			// Show sidebar
			$("#sidebar").stop().animate("left:0");
			$("#chat-container").stop().animate("left:" + width);
		}
	}

	/**
	 * Manages which content is being shown/hidden in sidebar toShow: which
	 * element with this ID to show, and hide the rest Also manages sidebar
	 * toggling
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
				if (id.equals(toShow) || id.equals("sidebar-hide")) {
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
