package entities.units;

import java.util.Collection;
import java.util.LinkedList;

import controller.GameBoard;
import entities.Player;

/**
 * @author Nicholas R Topping
 * @created on February 15, 2014
 * @class CSC 436 Software engineering - 4X
 * @summary
 * This abstract class outlines the basic functionality that all Agents will share. 
 * Agents are an extension of Unit, thus share all the same features of a unit (i.e.
 * moveable, have defined hp, etc.)
 * 
 */
public abstract class Agent extends Unit{

	// Add influence level? Longer they are alive more influence they have, if they have more influence
	// they can control more units, maybe more units of different kinds
	
	// Add DS of units they currently control
	
	// Add happiness level - happier means more influence, less happy might not issue commands? 
	
	// need other functionality, so that player can tell agent they want to defend, or that they want to attack, or explore, or trade
	
	// Based on above, if command issued to agent, agent needs to decide what to do - if unhappy will do nothing, or do opposite - 
	// 
	
	
		// General Agent happiness defined by number of units - if units taken away become less happy, if units added become more happy.
			// will start off relatively happy \
				/// if very unhappy will be less likely to issue attack commands, doesn't want to lose more troops
				// if very happy more likely to attack since he has more troops yaaaay
	// Prospector/explorer type agent
	
	// Getting control over more units makes everyone happy, 
	
	
	private Collection<Unit> underlings = null; 	// Stores all units this Agent controls
	private GameBoard board; 						// GameBoard this agent is associated with 
	private int loyalty; 							// the current loyalty of this agent, determines how they will behave
	private final int maxLoyalty   = 100; 			// max Loyalty of an agent 
	private final int minLoyalty   = 0; 			// min loyalty of an agent 
	private final int startLoyalty = 80; 			// start loyalty of agents - relatively happy, but not overly so. 
	
	
	// TODO add reference to gameboard, to allow for easier AI 
	
	/*
	 * Agent():
	 * Description:
	 * Base constructor for an agent object, currently calls the super constructor 
	 * (for Unit). 
	 * 
	 * Parameters:
	 * @param Player p    - player this agent belongs to 
	 * @param GameBoard g - GameBoard this agent is a member of, allows for more insightful AI decisions.
	 * @param int hp      - max hp this unit can have
	 * @param int bDmg    - amount of damage this unit can attack with
	 * @param int mR      - Movement range, what kind of tiles this agent can walk across/ how fast they travel
	 * @param int xco     - the x-coordinate of their spawn point
	 * @param int yco     - the y-coordinate of their spawn point
	 * 
	 * Return Value:
	 * @return new Agent object
	 */
	public Agent(Player p, GameBoard g, int hp, int bDmg, int mR, int xco, int yco) {
		super(p, hp, bDmg, mR, xco, yco);

		underlings = new LinkedList<Unit>();
		loyalty    = startLoyalty;
		board      = g; 
	}
	
	/*
	 * addUnderling()
	 * Description:
	 * Adds another unit to the control of this agent.
	 * TODO possibly implement limit to number of units an underling can control. 
	 * 
	 * Paramters:
	 * @param Unit u - unit to add to agent's control
	 * 
	 * Return value:
	 * @return boolean; true if unit added, false if not
	 */
	public boolean addUnderling(Unit u)
	{
		underlings.add(u);
		if (this.loyalty++ > maxLoyalty)
		{
			this.loyalty = maxLoyalty;
		}
		return true;
	}

	/*
	 * removeUnderling():
	 * Description:
	 * removes underling from control of agent (by either death, assignment to another agent, etc.(
	 * 
	 * Parameters:
	 * @param Unit u - unit to remove
	 * 
	 * Return Value:
	 * @return boolean; true if removed, false if not.
	 */
	public boolean removeUnderling(Unit u)
	{
		if (this.loyalty-- < minLoyalty)
		{
			this.loyalty = minLoyalty;
		}
		return underlings.remove(u);
	}
	
	/*
	 * commandUnit():
	 * Description:
	 * Issues command to a given unit? TODO 
	 */
	private void commandUnit(Unit u)
	{
		
	}
	
	/*
	 * decideAction():
	 * Based on current state of AI, decide what to do.
	 */
	abstract void decideAction();
	
}
