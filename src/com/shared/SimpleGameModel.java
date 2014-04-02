package com.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimpleGameModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8880929617458008388L;
	Map<Integer, EntityModel> entityMap = new HashMap<Integer, EntityModel>();
	public int turnNumber = 0;
	public int numEntities;
	
	public SimpleGameModel() {
		numEntities = 5;
		for(int i = 1; i <= numEntities; i++) {
			entityMap.put(i, new MovingUnitModel( i, 0, 5, 10 ));
		}
	}
	
	public SimpleGameModel( int numUnits ) {
		numEntities = numUnits;
		for(int i = 1; i <= numEntities; i++) {
			entityMap.put(i, new MovingUnitModel( i, 0, 5, 10 ));
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
		return entityMap.get(unitID).getMovementBehavior().deadReckonPosition(timeSinceUpdate);
	}
	
	public EntityModel get(int entityID) {
		return entityMap.get(entityID);
	}
	
	public Map<Integer,EntityModel> getEntities() {
		return entityMap;
	}
	
}
