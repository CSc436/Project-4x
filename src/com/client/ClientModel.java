package com.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.shared.MovingUnit;
import com.shared.Request;
import com.shared.SetTargetRequest;

public class ClientModel {
	
	private int turnNumber = 0;
	float[] position = { 0.0F, 0.0F };
	private long lastUpdateTime;
	private final SimpleSimulatorAsync simpleSimulator;
	private MovingUnit lastUnit;
	private MovingUnit nextUnit;
	private int averageTurnInterval = 200;
	
	public ClientModel() {
		simpleSimulator = GWT.create(SimpleSimulator.class);
	}
	
	public void setTarget( double x, double y ) {
		
		Request r = new SetTargetRequest(x,y);
		
		simpleSimulator.sendRequest(r,
				new AsyncCallback<Request[]>() {
					public void onFailure(Throwable caught) {
						System.out.println("Target request failed");
					}

					public void onSuccess(Request[] result) {
						double x = ((SetTargetRequest) result[0]).x;
						double y = ((SetTargetRequest) result[0]).y;
						System.out.println("New target set: " + x + " " + y);
					}
				});
		
	}
	
	public float[] getPosition( long currentTime ) {
		
		if (lastUnit == null || nextUnit == null)
			return position;
		
		position[0] = (float) (lastUnit.location.x + (currentTime - lastUpdateTime) * (nextUnit.location.x - lastUnit.location.x) / averageTurnInterval) ; 
		position[1] = (float) (lastUnit.location.y + (currentTime - lastUpdateTime) * (nextUnit.location.y - lastUnit.location.y) / averageTurnInterval) ;
		
		return position;
		
	}
	
	public void run() {
		
		simpleSimulator.startSimulation(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println("Simulation failed to start");
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				System.out.println("Simulation started");
				startup();
			}

		});
		
	}
	
	public void startup(){
		Timer pollTimer = new Timer() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				simpleSimulator.getSimulationState( new AsyncCallback<MovingUnit>() {

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Unable to receive simulation state");
					}

					@Override
					public void onSuccess(MovingUnit result) {
						lastUnit = nextUnit;
						nextUnit = result;
						
						lastUpdateTime = System.currentTimeMillis();

					}

				});
			}

		};

		pollTimer.scheduleRepeating(averageTurnInterval);
		
		Timer setTargetTimer = new Timer() {

			@Override
			public void run() {

				setTarget( 16*Math.random(), 16*Math.random() );
			}

		};

		setTargetTimer.scheduleRepeating(2000);
	}
	
}
