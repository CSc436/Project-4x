package com.server;

import deprecated.Targets;
import entities.Locatable;

public class ModelEvent {
	
	public final Targets type;
	private Locatable payload;
	
	public ModelEvent(Targets type, Locatable payload) {
		this.type = type;
		this.payload = payload;
	}
	
	public Locatable getPayload() {
		return payload;
	}
	
}
