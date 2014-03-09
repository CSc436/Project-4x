package entities.resources;

public class Resources {
	// Tentative resource types suggested during planning
	public int Gold;
	private int Wood;
	private int Food;
	private int Stone;
	private int ResearchPts;

	public Resources(int startingGold, int startingWood, int startingFood,
			int startingRPts) {
		Gold = startingGold;
		Wood = startingWood;
		Food = startingFood;
		Stone = 0;
		ResearchPts = startingRPts;
	}

	public boolean spend(int gold, int wood, int food, int rp) {
		if (!have_enough_resources(gold, wood, food, rp))
			return false;
		Gold -= gold;
		Wood -= wood;
		Food -= food;
		ResearchPts -= rp;
		return true;
	}

	public boolean spend(Resources r) {
		if (!have_enough_resources(r.Gold, r.Food, r.Wood, r.ResearchPts))
			return false;
		Gold -= r.Gold;
		Wood -= r.Wood;
		Food -= r.Food;
		ResearchPts -= r.ResearchPts;
		return true;
	}

	public void receive(int gold, int wood, int food, int rp) {
		Gold += gold;
		Wood += wood;
		Food += food;
		ResearchPts += rp;
	}

	public Resources scale(Resources r) {
		return new Resources(Gold * r.Gold, Wood * r.Wood, Food * r.Food,
				ResearchPts * r.ResearchPts);
	}

	public boolean have_enough_resources(int gold, int wood, int food, int rp) {
		if (gold > Gold || wood > Wood || food > Food || rp > ResearchPts)
			return false;
		return true;
	}

	public String toString() {
		return "Gold: " + Gold + "\nWood: " + Wood + "\nFood: " + Food
				+ "\nResearch: " + ResearchPts;
	}
}
