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
	
	/**
	 * Bank():
	 * Creates a new Bank Building. 
	 * 
	 * @param id
	 * @param playerId
	 * @param baseStats
	 * @param new_stats
	 * @param gameObjectType
	 * @param buildingType
	 * @param xco
	 * @param yco
	 * @param height
	 * @param width
	 * @param resourceAmount
	 */
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

	/**
	 * widthdraw():
	 * allows user to withdraw an amount of gold from the bank, 
	 * only if the bank has that much gold 
	 * 
	 * TODO this method DOES NOT WORK, resources was changed to static so all players cshare the same resources.
	 * 
	 * @param gold - amount of gold to withdraw 
	 * @return true if we can make the withdraw 
	 */
	// TODO Reimplement sans static variable. 
	public boolean withdraw(int gold) {
	/*	if(this.gold<gold)
			return false;
		Player.resources.Gold+=gold; // WHAT THE FUCK
		this.gold-=gold;*/
		return true; 
	}
		
	/**
	 * Same as withdraw, DOES NOT WORK.
	 * @param gold
	 * @return
	 */
	public boolean deposit(int gold) {
		/*if(Player.resources.Gold<gold)
			return false;
		Player.resources.Gold-=gold;
		this.gold+=gold;*/
		return true;
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