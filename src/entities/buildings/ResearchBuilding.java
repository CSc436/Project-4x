package entities.buildings;

import java.util.ArrayList;

import control.Player;
import entities.research.TechnologyEnum;

public interface ResearchBuilding {

	/**
	 * getTechnologyList(): will return a reference to the building's list of
	 * researchable technologies. For example, a Barracks will have a list of
	 * Technologies that the building can research.
	 * 
	 * @return
	 */
	public ArrayList<TechnologyEnum> getTechnologyList();;

	/**
	 * addTechnology(): will add a technology to the building's tech list based
	 * on the specified TechnologyEnum t given.
	 * 
	 * @return
	 */
	public void addTechnology(TechnologyEnum t);

	/**
	 * advanceResearchProduction(): reduces the time to complete research by
	 * timestep
	 * 
	 * @return
	 */
	

	public boolean hasTechnologyInList(TechnologyEnum t);

	public Player getPlayer();

}
