public class Player {

	private final String name;
//	Tentative resource types suggested during planning
	private int totalGold, totalWood, totalFood, totalResearchPts;
	
//	Bare constructor
	public Player(){
		name = "";
		totalGold = 0;
		totalWood = 0;
		totalFood = 0;
		totalResearchPts = 0;
	}
	
	public Player(String alias) {
		name = alias;
		totalGold = 0;
		totalWood = 0;
		totalFood = 0;
		totalResearchPts = 0;
	}
	
	public Player(String alias, int startingGold, int startingWood, int startingFood, int startingRPts){
		name = alias;
		totalGold = startingGold;
		totalWood = startingWood;
		totalFood = startingFood;
		totalResearchPts = startingRPts;
	}
	/*
	 * The following receive methods increment the associated
	 * resource pool.
	 */
	public void receiveGold(int receiveAmt){
		totalGold += receiveAmt;
	}
	
	public void receiveWood(int receiveAmt){
		totalWood += receiveAmt;
	}

	public void receiveFood(int receiveAmt){
		totalFood += receiveAmt;
	}
	
	public void receiveResearchPts(int receiveAmt){
		totalResearchPts += receiveAmt;
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
		if (totalGold < spendAmt){
			return false;
		}
		totalGold -= spendAmt;
		return true;
	}
	
	public boolean spendWood(int spendAmt){
		if (spendAmt < 0){
			System.err.println("Invalid spendAmt in class Player, method spendWood!");
			return false;
		}
		if (totalWood < spendAmt){
			return false;
		}
		totalWood -= spendAmt;
		return true;
	}
	
	public boolean spendFood(int spendAmt){
		if (spendAmt < 0){
			System.err.println("Invalid spendAmt in class Player, method spendFood!");
			return false;
		}
		if (totalFood < spendAmt){
			return false;
		}
		totalFood -= spendAmt;
		return true;
	}
	
	public boolean spendResourcePts(int spendAmt){
		if (spendAmt < 0){
			System.err.println("Invalid spendAmt in class Player, method spendResourcePts!");
			return false;
		}
		if (totalResearchPts < spendAmt){
			return false;
		}
		totalResearchPts -= spendAmt;
		return true;
	}
	
	public String getAlias(){
		return name;
	}
	
	public int getTotalGold(){
		return totalGold;
	}
	
	public int getTotalWood(){
		return totalWood;
	}
	
	public int getTotalFood(){
		return totalFood;
	}
	
	public int getTotalResearchPts(){
		return totalResearchPts;
	}
}


















