package com.client;

import java.text.DecimalFormat;

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
import com.google.gwt.i18n.client.NumberFormat;
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
	
	private MovingNumber nextNumber = new MovingNumber();
	private long lastUpdateTime;
	private MovingNumber lastNumber = new MovingNumber();
	private double interpVal;
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

				// Then, we send the input to the server.
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
				System.out.println("Simulation started");
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
						System.out.println("Unable to receive simulation state");
					}

					@Override
					public void onSuccess(MovingNumber result) {
						lastNumber = nextNumber;
						nextNumber = result;
						lastUpdateTime = System.currentTimeMillis();

					}
					
				});
			}
			
		};
		
		pollTimer.scheduleRepeating(200);
		
		Timer renderTimer = new Timer() {

			@Override
			public void run() {
				interpVal = lastNumber.value + (System.currentTimeMillis() - lastUpdateTime) * (nextNumber.value - lastNumber.value) / 200.0;
				String numString = NumberFormat.getFormat("###0.0000").format(interpVal);
				numberLabel.setText(numString);
			}
			
		};
		
		renderTimer.scheduleRepeating(33);
	}
}
