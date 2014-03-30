package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import control.BuildingType;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;

public class Barracks extends Building {

	int level;
	int soldierNum;
	int newSoldNum;
	int capacity;
	
	public Barracks(UUID id, int playerId, BaseStatsEnum baseStats, UnitStats new_stats, 
		    BuildingType buildingType, float xco,
			float yco, int height, int width) {
		super(id, playerId, baseStats, new_stats, buildingType, xco, yco,
				height, width);
	}


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
	
	public void setCapacity(){
		switch(level){
		case 1: capacity=1;
		case 2: capacity=5;
		case 3: capacity=10;
		case 4: capacity=17;
		case 5: capacity=25;
		}
	}
	
	@Override
	protected void setActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, String> getActions() {
		// TODO Auto-generated method stub
		return null;
	}
}
