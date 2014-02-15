package com.client;

import com.shared.DecrementRequest;
import com.shared.FieldVerifier;
import com.shared.IncrementRequest;
import com.shared.MovingNumber;
import com.shared.Request;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class _x implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	
	private MovingNumber theNumber;
	private int turnNumber = 0;
	private int lastTurn = 0;

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final SimpleSimulatorAsync simpleSimulator = GWT
			.create(SimpleSimulator.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button incButton = new Button("Increment");
		final Button decButton = new Button("Decrement");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		final Label errorLabel = new Label();
		final Label numberLabel = new Label();

		// We can add style names to widgets
		incButton.addStyleName("sendButton");
		decButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(incButton);
		RootPanel.get("sendButtonContainer").add(decButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		RootPanel.get("numberContainer").add(numberLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				incButton.setEnabled(true);
				decButton.setEnabled(true);
				incButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyRequestHandler implements ClickHandler, KeyUpHandler {
			
			Request request;
			
			public MyRequestHandler(Request newRequest) {
				request = newRequest;
			}
			
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendSimulationRequest(request);
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendSimulationRequest(request);
				}
			}

			/**
			 * Send a request to alter the state of the server-side simulation
			 */
			private void sendSimulationRequest(Request r) {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				r.setScheduledTurn(turnNumber+1);
				r.setLastTurnReceived(lastTurn);
				
				simpleSimulator.sendRequest(r,
						new AsyncCallback<Request[]>() {
							public void onFailure(Throwable caught) {

							}

							public void onSuccess(Request[] result) {

							}
						});
			}
		}

		// Add a handler to send the name to the server
		MyRequestHandler incHandler = new MyRequestHandler(IncrementRequest.generateRequest(0, 0));
		MyRequestHandler decHandler = new MyRequestHandler(DecrementRequest.generateRequest(0, 0));
		incButton.addClickHandler(incHandler);
		decButton.addClickHandler(decHandler);
		
		simpleSimulator.startSimulation(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
			}
			
		});
		
		// Now need to implement scheduled polling of server
		Timer pollTimer = new Timer() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				simpleSimulator.getSimulationState(new AsyncCallback<MovingNumber>() {

					@Override
					public void onFailure(Throwable caught) {
						
					}

					@Override
					public void onSuccess(MovingNumber result) {
						theNumber = result;
					}
					
				});
			}
			
		};
		
		pollTimer.scheduleRepeating(200);
	}
}
