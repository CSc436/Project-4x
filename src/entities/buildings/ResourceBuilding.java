package entities.buildings;

import entities.resources.Resources;

public interface ResourceBuilding {

	/**
	 * generateResource(): returns a Resource object that contains the amount of
	 * resources for one timestep of the building's life. The baseResourceAmount
	 * should be decremented by this value in this method.
	 * 
	 * @return the resource amount that is to be added to the building owner
	 */
	public Resources generateResource();

	/**
	 * advanceResourceProduction(): adds the amount of resources for 1 tick to
	 * the building owner's resource pool
	 * 
	 * @return
	 */
	public void advanceResourceProduction();

}
