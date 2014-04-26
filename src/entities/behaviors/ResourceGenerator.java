package entities.behaviors;

import java.io.Serializable;

import entities.resources.Resources;

public interface ResourceGenerator extends Serializable {
	
	public Resources generateResources( int timeStep );
	
}