package com.shared.model.buildings;

import com.shared.model.stats.BaseStatsEnum;

public class Barracks extends Building {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6343058549434572522L;
	int level;
	int soldierNum;
	int newSoldNum;
	int capacity;
	
	public Barracks(int id, int playerId, float xco,
			float yco) {
		super(id, playerId, BaseStatsEnum.BARRACKS, BaseStatsEnum.BARRACKS.getStats(),
				BuildingType.BARRACKS, xco, yco,
				2, 4);
	}
	
	public Barracks() {}

// ???????
	public int createSoldiers(){
		switch(level){
		case(1): return 50;
		case(2): return 80;
		case(3): return 100;
		case(4): return 150;
		case(5): return 200;
		default: return 0;
		}
	}
	
	public int getSoldiers(){
		soldierNum+=createSoldiers();
		return soldierNum;
	}
	
	public void upgrade(){
		level++;
	}
	
	// FINDBUGS - Added break statements for each case.
	public void setCapacity(){
		switch(level){
		case 1: capacity=1; break;
		case 2: capacity=5; break;
		case 3: capacity=10; break;
		case 4: capacity=17; break;
		case 5: capacity=25; break;
		}
	}


}
