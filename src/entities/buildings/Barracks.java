package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import control.BuildingType;
import entities.GameObjectType;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;

public class Barracks extends Building {



	public Barracks(UUID id, int playerId, BaseStatsEnum baseStats, UnitStats new_stats, 
			GameObjectType gameObjectType, BuildingType buildingType, float xco,
			float yco, int height, int width) {
		super(id, playerId, baseStats, new_stats, gameObjectType, buildingType, xco, yco,
				height, width);
	}

	protected static int setSoldiers(int i) {
		switch (i) {
		case (1):
			return 50;
		case (2):
			return 80;
		case (3):
			return 100;
		case (4):
			return 150;
		case (5):
			return 200;
		default:
			return 0;
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
