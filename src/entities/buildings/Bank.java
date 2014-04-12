package entities.buildings;

import java.util.UUID;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;
import entities.units.Unit;

public class Bank extends Building implements ResourceBuilding {

	private static final int BaseGenAmt = 100; // Base amount that banks will
												// generate for the user
	int gold;

	/**
	 * Bank(): Creates a new Bank Building.
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
	public Bank(UUID id, int playerId, float xco, float yco,
			Resources resourceAmount) {
		super(id, playerId, BaseStatsEnum.BANK, BaseStatsEnum.BANK.getStats(),
				BuildingType.BANK, xco, yco, BuildingType.BANK.getX(),
				BuildingType.BANK.getY());
	}

	@Override
	public String toString() {
		return "GameObject type: Bank; Player owner Id: " + this.getPlayerID()
				+ "; Castle id: " + this.getCastleId() + "; GameObject id: "
				+ this.getId();
	}

	/**
	 * widthdraw(): allows user to withdraw an amount of gold from the bank,
	 * only if the bank has that much gold
	 * 
	 * TODO this method DOES NOT WORK, resources was changed to static so all
	 * players cshare the same resources.
	 * 
	 * @param gold
	 *            - amount of gold to withdraw
	 * @return true if we can make the withdraw
	 */
	// TODO Reimplement sans static variable.
	public boolean withdraw(int gold) {
		/*
		 * if(this.gold<gold) return false; Player.resources.Gold+=gold; // WHAT
		 * THE FUCK this.gold-=gold;
		 */
		return true;
	}

	/**
	 * Same as withdraw, DOES NOT WORK.
	 * 
	 * @param gold
	 * @return
	 */
	public boolean deposit(int gold) {
		/*
		 * if(Player.resources.Gold<gold) return false;
		 * Player.resources.Gold-=gold; this.gold+=gold;
		 */
		return true;
	}

	@Override
	public void setGlobalRateModifier(Resources newRate) {
		// TODO Auto-generated method stub

	}

	@Override
	public Resources getGlobalRateModifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resources generateResource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unit advanceResourceProduction(int timestep) {
		// TODO Auto-generated method stub
		return null;
	}

}
