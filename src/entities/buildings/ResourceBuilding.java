package entities.buildings;

import control.Player;
import entities.gameboard.Resource;

public abstract class ResourceBuilding extends Building{
	
	private final Resource resourceType;
	private int baseGenerateAmt;
	// Global resource rate modifier that affects all resource buildings
	private static double globalRateModifier = 0.0;

	public ResourceBuilding(Player p, int h, int w, Resource resource, int baseGenAmt) {
		super(p, h, w, 100);
		resourceType = resource;
		baseGenerateAmt = baseGenAmt;
	}
	
	/* Setters and Getters */
	public void setGlobalRateModifier(double newRate){
		globalRateModifier = newRate;
	}

	public Resource getResourceType(){
		return resourceType;
	}
	
	public int getBaseGenerateAmt(){
		return baseGenerateAmt;
	}
	
	public double getGlobalRateModifier(){
		return globalRateModifier;
	}
	
	/* Calculates the how much is in each "bundle" of resource
	 * that the player receives from this resource building.
	 * 
	 * "Bundles" are calculated by taking the base generation amount
	 * and adding to it a percentage of the base amount. The percentage
	 * is determined by summing the globalRateModifier and localModifier
	*/
	protected int calculateBundleAmount(double localModifier){
		int amount = (int) (baseGenerateAmt + Math.floor(baseGenerateAmt * (globalRateModifier + localModifier)));
		return amount;
	}
	
	public abstract void generateResource();
}
