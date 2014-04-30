package com.server;

import com.shared.model.entities.Locatable;

import deprecated.Targets;

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
