package com.server;

import com.shared.model.entities.Locatable;

public class ModelEvent {
	
	//public final Targets type;
	private Locatable payload;
	
	public ModelEvent(Locatable payload) {
		//this.type = type;
		this.payload = payload;
	}
	
	public Locatable getPayload() {
		return payload;
	}
	
}
