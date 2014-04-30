package com.shared.model.behaviors;

import java.io.Serializable;

import com.shared.model.resources.Resources;

public interface ResourceGenerator extends Serializable {
	
	public Resources generateResources( int timeStep );
	
}