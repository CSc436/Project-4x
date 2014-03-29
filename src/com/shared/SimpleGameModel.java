package com.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimpleGameModel implements Serializable {
	
	Map<Integer, MovingUnitModel> entityMap = new HashMap<Integer, MovingUnitModel>();
	public int turnNumber = 0;
	public int numEntities;
	
	public SimpleGameModel() {
		numEntities = 5;
		for(int i = 0; i < numEntities; i++) {
			entityMap.put(i, new MovingUnitModel( i, 0, 5, 5 ));
		}
	}
	
	public SimpleGameModel( int numUnits ) {
		numEntities = numUnits;
		for(int i = 0; i < numEntities; i++) {
			entityMap.put(i, new MovingUnitModel( i, 0, 5, 5 ));
		}
	}
	
	public void simulateTimeStep(int timeStep) {
		Set<Integer> keySet = entityMap.keySet();
		for(Integer i : keySet) {
			entityMap.get(i).getMovementBehavior().simulateTimeStep(timeStep);
		}
		turnNumber++;
	}
	
	public double[] deadReckonEntityPosition( int unitID, long timeSinceUpdate ) {
		return entityMap.get(unitID).deadReckonPosition(timeSinceUpdate);
	}
	
	public EntityModel get(int entityID) {
		return entityMap.get(entityID);
	}
	
}
