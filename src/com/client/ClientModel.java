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

					}

					public void onSuccess(Request[] result) {

					}
				});
		
	}
	
	public float[] getPosition( long currentTime ) {
		
		long currTime = System.currentTimeMillis();
		position[0] = (float) (lastUnit.location.x + (currTime - lastUpdateTime) * (nextUnit.location.x - lastUnit.location.x) / averageTurnInterval) ; 
		position[1] = (float) (lastUnit.location.y + (currTime - lastUpdateTime) * (nextUnit.location.y - lastUnit.location.y) / averageTurnInterval) ;
		
		return position;
		
	}
	
	public void run() {
		
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

						long currTime = System.currentTimeMillis();
						lastUpdateTime = currTime;

					}

				});
			}

		};

		pollTimer.scheduleRepeating(averageTurnInterval);
		
		/*
		Timer renderTimer = new Timer() {

			@Override
			public void run() {
				currUnit.location.x = lastUnit.location.x + (System.currentTimeMillis() - lastUpdateTime) * (nextUnit.location.x - lastUnit.location.x) / averageTurnInterval ; 
				currUnit.location.y = lastUnit.location.y + (System.currentTimeMillis() - lastUpdateTime) * (nextUnit.location.y - lastUnit.location.y) / averageTurnInterval ;
			}

		};

		renderTimer.scheduleRepeating(33);
		*/
		
		Timer setTargetTimer = new Timer() {

			@Override
			public void run() {

				setTarget( -10 + 20*Math.random(), -10 + 20*Math.random() );
			}

		};

		setTargetTimer.scheduleRepeating(1000);
	}
	
	
	
}
