package entities.buildings;

import com.fourx.buffs.UnitType;
import com.fourx.resources.Resources;

import control.Player;
import entities.BaseStatsEnum;

public abstract class ResourceBuilding extends Building{
	
	public ResourceBuilding(Player p, BaseStatsEnum baseStats, UnitType type,
			int xco, int yco, int height, int width, Resources resourceAmount) {
		super(p, baseStats, type, xco, yco, height, width);
		// resources
		this.resourceAmount = resourceAmount;
		baseResourceAmount = resourceAmount;
	}

	protected final Resources baseResourceAmount;
	protected Resources resourceAmount;

	// Global resource rate modifier that affects all resource buildings
	private static Resources globalRateModifier = new Resources(1,1,1,1);


	
	/* Setters and Getters */
	public void setGlobalRateModifier(Resources newRate){
		globalRateModifier = newRate;
		resourceAmount = baseResourceAmount.scale(globalRateModifier);
	}
	
	public Resources getGlobalRateModifier(){
		return globalRateModifier;
	}
	
	public void generateResource() {
		getOwner().getResources().receive(resourceAmount);
	}
}
