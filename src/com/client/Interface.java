package com.client;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;

public class Interface {
	
	/**
	 * Responsible for registering callbacks that are purely bound to the interface
	 */

	public static void init() {
		initClickHandlers();
	}


	private static void initClickHandlers() {
		// City Menu Button
		$("#city-button").click(new Function() {
			public boolean f(Event e) {
				// Show city menu
				toggleSidebar(false);
				$("#agent-menu").hide();
				$("#city-menu").show();
				// Change content to city menu
				return true; // Default return true
			}
		});

		// Agent Menu Button
		$("#agent-button").click(new Function() {
			public boolean f(Event e) {
				// Show agent menu
				toggleSidebar(false);
				$("#city-menu").hide();
				$("#agent-menu").show();
				// Change content to agent menu
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
}
