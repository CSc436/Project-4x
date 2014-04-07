package com.client.model;

import java.util.LinkedList;
import java.util.Queue;

import com.client.SimpleSimulator;
import com.client.SimpleSimulatorAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.shared.Request;
import com.shared.SetTargetRequest;
import com.shared.SimpleGameModel;
import com.sksamuel.gwt.websockets.Websocket;
import com.sksamuel.gwt.websockets.WebsocketListener;

public class ClientModel {
	
	private int turnNumber = -1;
	private int playerNumber = -1;
	float[] position = { 0.0F, 0.0F };
	private long lastUpdateTime;
	private final SimpleSimulatorAsync simpleSimulator;
	private SimpleGameModel lastModel;
	private SimpleGameModel nextModel = new SimpleGameModel(5);
	private int averageTurnInterval = 200;
	private boolean readyForNext = true;
	private int cycleTime = 100;
	
	private Websocket socket;
	
	private Queue<Request> requestQueue = new LinkedList<Request>();
	
	public ClientModel() {
		
		simpleSimulator = GWT.create(SimpleSimulator.class);
		
		Window.addWindowClosingHandler(new Window.ClosingHandler() {
			
			@Override
			public void onWindowClosing(ClosingEvent event) {
				simpleSimulator.exitGame(playerNumber, null);

			}
		});
		
		String webSocketURL = GWT.getModuleBaseURL().replace("http", "ws") + "webSocket";
		socket = new Websocket(webSocketURL);
		socket.addListener(new WebsocketListener() {

			@Override
			public void onClose() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onMessage(String msg) {
				// TODO Auto-generated method stub
				AutoBean<SimpleGameModel> bean = AutoBeanCodex.decode(myFactory, SimpleGameModel.class, msg);     
			    nextModel = bean.as(); 
			}

			@Override
			public void onOpen() {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		AutoBean<Request> bean = AutoBeanUtils.getAutoBean(delegate)

	}

	public void setTarget( int unitID, double x, double y ) {

		Request r = new SetTargetRequest( unitID, x, y );
		AutoBean<Request> bean = AutoBeanUtils.getAutoBean(r);
		String jsonRequest = AutoBeanCodex.encode(bean).getPayload();
		socket.send(jsonRequest);
		
		//requestQueue.add(r);
		
		/*
		simpleSimulator.sendRequest(r,
				new AsyncCallback<Request[]>() {
					public void onFailure(Throwable caught) {
						System.out.println("Target request failed");
					}

					public void onSuccess(Request[] result) {
						int entityID = ((SetTargetRequest) result[0]).entityID;
						double x = ((SetTargetRequest) result[0]).x;
						double y = ((SetTargetRequest) result[0]).y;
						nextModel.get(entityID).getMovementBehavior().setTarget(x, y);
						System.out.println("New target set: " + x + " " + y);
					}
				});
		*/
		 
		
	}
	
	public float[] getPosition( int unitID, long currentTime ) {
		
		if (lastModel == null || nextModel == null)
			return position;
		// Interpolation System
		/*
		position[0] = (float) (lastUnit.location.x + (currentTime - lastUpdateTime) * (nextUnit.location.x - lastUnit.location.x) / averageTurnInterval) ; 
		position[1] = (float) (lastUnit.location.y + (currentTime - lastUpdateTime) * (nextUnit.location.y - lastUnit.location.y) / averageTurnInterval) ;
		*/
		
		// Dead-Reckoning System
		double[] positionDouble = nextModel.deadReckonEntityPosition( unitID, currentTime - lastUpdateTime );
		position[0] = (float) positionDouble[0];
		position[1] = (float) positionDouble[1];
		
		//System.out.println("Client >>> " + position[0] + " " + position[1]);
		
		return position;
		
	}
	
	public void run() {
		
		simpleSimulator.joinSimulation(new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println("Failed to join simulation");
			}

			@Override
			public void onSuccess(Integer playerNum) {
				// TODO Auto-generated method stub
				System.out.println("Joined game");
				playerNumber = playerNum;
				startup();
			}

		});
		
	}
	
	public void startup(){
		readyForNext = true;
		
		Timer pollTimer = new Timer() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				final long startTime = System.currentTimeMillis();
				
				if(!readyForNext) {
					System.out.println("Not ready for next simulation state");
					return;
				}
				
				readyForNext = false;
				System.out.println("Requesting simulation state...");
				
				Queue<Request> tempQueue = requestQueue;
				requestQueue = new LinkedList<Request>();
				
				simpleSimulator.getSimulationState( playerNumber, turnNumber, new AsyncCallback<SimpleGameModel>() {

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("    Unable to receive simulation state");
						readyForNext = true;
					}

					@Override
					public void onSuccess(SimpleGameModel result) {
						
						
						
						lastModel = nextModel;
						nextModel = result;
						
						long currTime = System.currentTimeMillis();
						cycleTime = (int) (currTime - lastUpdateTime);
						lastUpdateTime = currTime;
						turnNumber = result.turnNumber;
						System.out.println("    Simulation state received! Cycle time: " + cycleTime + " ms");
						confirmReceipt();
					}

				});
			}

		};

		pollTimer.scheduleRepeating(20);
		
		Timer setTargetTimer = new Timer() {

			@Override
			public void run() {
				//setTarget( (int) (1 + nextModel.numEntities * Math.random()), 16*Math.random(), 16*Math.random() );
			}

		};

		setTargetTimer.scheduleRepeating(2000);
	}
	
	public void confirmReceipt() {
		simpleSimulator.confirmReceipt(playerNumber, turnNumber, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("    Receipt failed, retrying...");
			}

			@Override
			public void onSuccess(String result) {
				System.out.println("    Receipt success!");
				readyForNext = true;
			}

		});
	}
	
	public SimpleGameModel getGameModel() {
		return nextModel;
	}
	
}
