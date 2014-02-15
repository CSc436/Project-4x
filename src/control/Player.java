package control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entities.Unit;
import entities.buildings.Building;

public class Player {

	// scoring
	// ints for resource counts

	private String name;
	private int totalGold, totalWood, totalFood, totalStone, totalResearchPts;
	private List<Unit> selectedUnits;
	private List<Building> selectedBuildings;
	private Set<Building> controlledBuildings;
	
	public Player(String alias) {
		name = alias;
		selectedUnits = new ArrayList<Unit>();
		selectedBuildings = new ArrayList<Building>();
		controlledBuildings = new HashSet<Building>();

	}
	
	public Player(String alias, int startingGold, int startingWood, int startingFood, int startingStone, int startingRPts){
		name = alias;
		totalGold = startingGold;
		totalWood = startingWood;
		totalFood = startingFood;
		totalStone = startingStone;
		totalResearchPts = startingRPts;
	}

	public void setName(String newName) {

		name = newName;
	}
	
	public void selectUnit(Unit unit) {
		selectedUnits.add(unit);
	}
	
//	public HashMap<String, String> possibleActions() {
//		HashMap<String, String> temp = new HashMap<String, String>();
//		
//	}
	
	public void selectBuilding(Building building) {
		selectedBuildings.add(building);
	}
	
	public Set<Building> getBuildings() {
		return controlledBuildings;
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
	
	public void receiveStone(int receiveAmt){
		totalStone += receiveAmt;
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
	
	public boolean spendStone(int spendAmt){
		if (spendAmt < 0){
			System.err.println("Invalid spendAmt in class Player, method spendStone");
			return false;
		}
		if (totalStone < spendAmt){
			return false;
		}
		totalStone -= spendAmt;
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
