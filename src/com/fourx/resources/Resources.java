package com.fourx.resources;

public class Resources {
	// Tentative resource types suggested during planning
	private int Gold, Wood, Food, ResearchPts;

	public Resources(int startingGold, int startingWood, int startingFood,
			int startingRPts) {
		Gold = startingGold;
		Wood = startingWood;
		Food = startingFood;
		ResearchPts = startingRPts;
	}

	/*
	 * The following receive methods increment the associated
	 * resource pool.
	 */
	public void receiveGold(int receiveAmt){
		Gold += receiveAmt;
	}
	
	public void receiveWood(int receiveAmt){
		Wood += receiveAmt;
	}

	public void receiveFood(int receiveAmt){
		Food += receiveAmt;
	}
	
	public void receiveResearchPts(int receiveAmt){
		ResearchPts += receiveAmt;
	}
	
	/*
	 * The spend methods subtract the amount to spend from
	 * the associated resource pool, checks to make sure
	 * the player has enough of the resource to spend.
	 * 
	 * Returns false if there is not enough of the resource to spend
	 * and true if the spend was successful.
	 */
	public boolean spendGold(int spendAmt){
		if (spendAmt < 0){
			System.err.println("Invalid spendAmt in method spendGold!");
			return false;
		}
		if (Gold < spendAmt){
			return false;
		}
		Gold -= spendAmt;
		return true;
	}
	
	public boolean spendWood(int spendAmt){
		if (spendAmt < 0){
			System.err.println("Invalid spendAmt in class Player, method spendWood!");
			return false;
		}
		if (Wood < spendAmt){
			return false;
		}
		Wood -= spendAmt;
		return true;
	}
	
	public boolean spendFood(int spendAmt){
		if (spendAmt < 0){
			System.err.println("Invalid spendAmt in class Player, method spendFood!");
			return false;
		}
		if (Food < spendAmt){
			return false;
		}
		Food -= spendAmt;
		return true;
	}
	
	public boolean spendResourcePts(int spendAmt){
		if (spendAmt < 0){
			System.err.println("Invalid spendAmt in class Player, method spendResourcePts!");
			return false;
		}
		if (ResearchPts < spendAmt){
			return false;
		}
		ResearchPts -= spendAmt;
		return true;
	}
	
	public int getTotalGold(){
		return Gold;
	}
	
	public int getTotalWood(){
		return Wood;
	}
	
	public int getTotalFood(){
		return Food;
	}
	
	public int getTotalResearchPts(){
		return ResearchPts;
	}

	public boolean spend(Resources cost) {
		if (!have_enough_resources(cost)) return false;
		Gold -= cost.Gold;
		Wood -= cost.Wood;
		Food -= cost.Food;
		ResearchPts -= cost.ResearchPts;
		return true;
	}
	
	public boolean have_enough_resources(Resources r) {
		if (r.Gold > Gold || r.Wood > Wood || r.Food > Food || r.ResearchPts > ResearchPts)
			return false;
		return true;
	}
	
	public String toString() {
		return "Gold: "+Gold + "\nWood: "+ Wood + "\nFood: " + Food + "\nResearch: "+ ResearchPts;
	}
}
