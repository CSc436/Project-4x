package entities.buildings;

import java.util.HashMap;
import java.util.UUID;

import control.BuildingType;
import control.Player;
import entities.GameObjectType;
import entities.gameboard.Resource;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;

public class Bank extends ResourceBuilding {
	// Placeholder bank building dimensions
	private static final int Height = 4;
	private static final int Width = 4;
	private static final int BaseGenAmt = 100; // Base amount that banks will generate for the user
	int gold;
	
	public Bank(UUID id, int playerId, BaseStatsEnum baseStats, UnitStats new_stats, 
			GameObjectType gameObjectType, BuildingType buildingType, float xco,
			float yco, int height, int width, Resources resourceAmount) {
		super(id, playerId, baseStats, new_stats, gameObjectType, buildingType, xco, yco, height, width, resourceAmount);
	}
	
	@Override
	public String toString() {
		return "GameObject type: Bank; Player owner Id: " + this.getPlayerId() + "; Castle id: " + this.getCastleId() 
				+ "; GameObject id: " + this.getId();
	}

	public void withdraw(int gold) {
		Player.resources.Gold+=gold;
		this.gold-=gold;
	}
		
	public void deposit(int gold) {
		Player.resources.Gold-=gold;
		this.gold+=gold;
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

	@Override
	public void generateResource() {
		// TODO Auto-generated method stub
	}
}
