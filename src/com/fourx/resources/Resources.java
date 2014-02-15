package com.fourx.resources;

public class Resources {
	// Tentative resource types suggested during planning
	private int Gold, Wood, Food, Stone, ResearchPts;

	public Resources(int startingGold, int startingWood, int startingFood,
			int startingRPts) {
		Gold = startingGold;
		Wood = startingWood;
		Food = startingFood;
		Stone = 0;
		ResearchPts = startingRPts;
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
