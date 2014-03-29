package entities.resources;

/**
 * Resources
 * @author NRTop
 * This class keeps track of how many resource the player currently has, 
 * also allows resource buildings to generate a resource. 
 */
public class Resources {
	// Tentative resource types suggested during planning
	public int Gold;
	private int Wood;
	private int Food;
	private int Stone;
	private int ResearchPts;

	public Resources(int startingGold, int startingWood, int startingFood, int startingStone, 
			int startingRPts) {
		Gold = startingGold;
		Stone = startingStone;
		Wood = startingWood;
		Food = startingFood;
		Stone = 0;
		ResearchPts = startingRPts;
	}

	public boolean spend(int gold, int wood, int food, int stone, int rp) {
		if (!have_enough_resources(gold, wood, food, stone,  rp))
			return false;
		Gold  -= gold;
		Wood  -= wood;
		Food  -= food;
		Stone -= stone;
		ResearchPts -= rp;
		return true;
	}

	public boolean spend(Resources r) {
		if (!have_enough_resources(r.Gold, r.Food, r.Wood, r.Stone, r.ResearchPts))
			return false;
		Gold  -= r.Gold;
		Wood  -= r.Wood;
		Food  -= r.Food;
		Stone -= r.Stone;
		ResearchPts -= r.ResearchPts;
		return true;
	}

	public void receive(int gold, int wood, int food, int stone, int rp) {
		Gold  += gold;
		Wood  += wood;
		Food  += food;
		Stone += stone; 
		ResearchPts += rp;
	}

	public Resources scale(Resources r) {
		return new Resources(Gold * r.Gold, Wood * r.Wood, Food * r.Food, Stone * r.Stone,
				ResearchPts * r.ResearchPts);
	}

	public boolean have_enough_resources(int gold, int wood, int food, int stone, int rp) {
		if (gold > Gold || wood > Wood || food > Food || stone > Stone ||  rp > ResearchPts)
			return false;
		return true;
	}

	public String toString() {
		return "Gold: " + Gold + "\nWood: " + Wood + "\nFood: " + Food + "\nStone: " + Stone 
				+ "\nResearch: " + ResearchPts;
	}
}
