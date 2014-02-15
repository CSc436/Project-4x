package entities.units;

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
	
	// 
	
	/*
	 * Agent():
	 * Description:
	 * Base constructor for an agent object, currently calls the super constructor 
	 * (for Unit). 
	 * 
	 * Parameters:
	 * @param Player p - player this agent belongs to 
	 * @param int hp   - max hp this unit can have
	 * @param int bDmg - amount of damage this unit can attack with
	 * @param int mR   - Movement range, what kind of tiles this agent can walk across/ how fast they travel
	 * @param int xco  - the x-coordinate of their spawn point
	 * @param int yco  - the y-coordinate of their spawn point
	 * 
	 * Return Value:
	 * @return new Agent object
	 */
	public Agent(Player p, int hp, int bDmg, int mR, int xco, int yco) {
		super(p, hp, bDmg, mR, xco, yco);
		// TODO Auto-generated constructor stub
	}

}
