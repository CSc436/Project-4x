package entities.buildings;

import control.BuildingType;
import entities.behaviors.ResourceGenerator;
import entities.behaviors.StandardResourceGenerator;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;


// TODO for construction, need to add check to make sure resource building is 
// being placed on correct tile type (food on food, gold on gold, etc.)

public class ResourceBuilding extends Building implements ResourceGenerator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -214411844510536374L;
	protected final Resources baseResourceAmount;
	protected Resources resourceAmount;
	protected ResourceGenerator resourceGenerationBehavior;

	// Global resource rate modifier that affects all resource buildings
	private static Resources globalRateModifier = new Resources(1, 1, 1, 1, 1);

	/**
	 * ResourceBuidling():
	 * Base constructor for Resource Type buildings, calls super building constructor and sets base amount of 
	 * resources. 
	 * 
	 * Parameters
	 * @param UUID id - the unique identifier for this Resource Building 
	 * @param int playerId - the id of the player who owns this resource building 
	 * @param baseStats - the basic stats for this building (ie health, capacity, etc.)
	 * @param new_stats - the current stats of this unit (less than or equal to baseStats generally)
	 * @param buildingType Type of building - 
	 * @param float xco - initial x coordinate
	 * @param float yco - initial y coordinate
	 * @param int height - how many tiles high building takes up 
	 * @param int width - how many tiles wide the building takes up
	 * @param Reosources resourceAmount - base amount of resource building generates
	 * 
	 */
	public ResourceBuilding(int id, int playerId, BaseStatsEnum baseStats, UnitStats new_stats, 
		    BuildingType buildingType, float xco,
			float yco, int height, int width, Resources resourceAmount) {
		super(id,playerId, baseStats, new_stats, 
				buildingType, xco,
				yco, height, width);
		baseResourceAmount = resourceAmount;
		this.resourceAmount = resourceAmount;
		resourceGenerationBehavior = new StandardResourceGenerator(resourceAmount);
	}
	/* Setters and Getters */
	public void setGlobalRateModifier(Resources newRate) {
		globalRateModifier = newRate;
		resourceAmount = baseResourceAmount.scale(globalRateModifier);
	}

	public Resources getGlobalRateModifier() {
		return globalRateModifier;
	}

	public Resources generateResource()
	{
		return resourceAmount;
	}

	@Override
	public Resources generateResources(int timeStep) {
		return resourceGenerationBehavior.generateResources(timeStep);
	}
}
