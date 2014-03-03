package com.client;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.query.client.Function;
import com.google.gwt.user.client.Event;

public class Interface {

	/**
	 * Responsible for registering callbacks that are purely bound to the
	 * interface
	 */
	public static void init() {
		initClickHandlers();
		Console.log("hello");
	}

	/**
	 * Initializes all click handlers for the interface
	 */
	private static void initClickHandlers() {
		// City Menu Button
		$("#city-button").click(new Function() {
			public boolean f(Event e) {
				// Show city menu
				toggleSidebar(false);
				$("#agent-menu").hide();
				$("#economies-menu").hide();
				$("#diplomacy-menu").hide();
				$("#city-menu").show();
				// TODO: Change content to city menu
				return true; // Default return true
			}
		});

		// Agent Menu Button
		$("#agent-button").click(new Function() {
			public boolean f(Event e) {
				// Show agent menu
				toggleSidebar(false);
				$("#city-menu").hide();
				$("#economies-menu").hide();
				$("#diplomacy-menu").hide();
				$("#agent-menu").show();
				// TODO: Change content to agent menu
				return true; // Default return true
			}
		});

		// Diplomacy Menu Button
		$("#diplomacy-button").click(new Function() {
			public boolean f(Event e) {
				// Show diplomacy menu
				toggleSidebar(false);
				$("#agent-menu").hide();
				$("#city-menu").hide();
				$("#economies-menu").hide();
				$("#diplomacy-menu").show();
				// TODO: Change content to diplomacy menu
				return true; // Default return true
			}
		});

		// Economies Menu Button
		$("#economies-button").click(new Function() {
			public boolean f(Event e) {
				// Show economies menu
				toggleSidebar(false);
				$("#agent-menu").hide();
				$("#city-menu").hide();
				$("#diplomacy-menu").hide();
				$("#economies-menu").show();
				// TODO: Change content to economies menu
				return true; // Default return true
			}
		});

		// Sidebar close/open
		$("#sidebar-hide").click(new Function() {
			public boolean f(Event e) {
				// Hide sidebar
				toggleSidebar(true);
				return true; // Default return true
			}
		});
	}

	/**
	 * Will show/hide sidebar with animation
	 * hideIfShowing: whether or not close the sidebar if it's showing
	 */
	private static void toggleSidebar(boolean hideIfShowing) {
		String left = $("#sidebar").css("left");
		if (left.equals("0px")) {
			// Hide only if param is true
			if (hideIfShowing) {
				int width = $("#sidebar").outerWidth(true);
				int closeWidth = $("#sidebar-hide").outerWidth(true);
				$("#sidebar").animate("left:-" + (width + closeWidth));
			}
		} else {
			// Show sidebar
			$("#sidebar").animate("left:0");
		}
	}
	
	/**
	 * Manages which content is being shown/hidden in sidebar
	 * toShow: which element with this ID to show, and hide the rest
	 */
	private static void changeSidebarContent(String toShow) {
		// 
	}
}
