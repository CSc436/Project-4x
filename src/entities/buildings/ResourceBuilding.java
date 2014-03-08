package entities.buildings;

import java.util.UUID;

import control.BuildingType;
import entities.GameObjectType;
import entities.resources.Resources;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;

public abstract class ResourceBuilding extends Building {

	protected final Resources baseResourceAmount;
	protected Resources resourceAmount;

	// Global resource rate modifier that affects all resource buildings
	private static Resources globalRateModifier = new Resources(1, 1, 1, 1);

	public ResourceBuilding(UUID id, int playerId, BaseStatsEnum baseStats, UnitStats new_stats, 
			GameObjectType gameObjectType, BuildingType buildingType, float xco,
			float yco, int height, int width, Resources resourceAmount) {
		super(id,playerId, baseStats, new_stats, 
				gameObjectType, buildingType, xco,
				yco, height, width);
		baseResourceAmount = resourceAmount;
		this.resourceAmount = resourceAmount;
		
	}
	/* Setters and Getters */
	public void setGlobalRateModifier(Resources newRate) {
		globalRateModifier = newRate;
		resourceAmount = baseResourceAmount.scale(globalRateModifier);
	}

	public Resources getGlobalRateModifier() {
		return globalRateModifier;
	}

	public final Resources generateResource()
	{
		return resourceAmount; 
	}
}
