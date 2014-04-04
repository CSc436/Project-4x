package entities.units;

import java.util.PriorityQueue;
import java.util.UUID;

import entities.Action;
import entities.stats.BaseStatsEnum;
import entities.GameObject;
import entities.GameObjectType;
import entities.stats.UnitStats;

/*
 * 
 * 
 * Purpose:  This abstract class defines the concept of a unit.  Every unit will atleast have the following 
 * information known about itself.  
 */

// TODO add A* path finding, use diagonals to make nice looking paths
// returns a queue/list of tiles that it needs to go to, at each turn pop one off and move player there. 

public abstract class Unit extends GameObject {

	private UnitType unitType;
	private int creationTime;

	public Unit(UUID id, int playerId, BaseStatsEnum baseStats,
			UnitStats new_stats, UnitType unitType,
			float xco, float yco) {
		super(id, playerId, baseStats, new_stats, GameObjectType.UNIT, xco, yco);
		this.unitType = unitType;
		this.creationTime = baseStats.getCreationTime();

	}

	// not sure if needed
	public void performActions() {
		while (!actionQueue.isEmpty()) {
			Action a = actionQueue.poll();
			switch (a) {
			case PLAYER_ATTACK_MOVE:
				System.out.println("player attack move");
				break;
			case PLAYER_ATTACK:
				System.out.println("player attack");
				break;
			case DEATH:
				// I DIED
				System.out
						.println("death at :" + position.x + " " + position.y);
				// getOwner().getUnits().removeUnit(this);
				while (!actionQueue.isEmpty())
					actionQueue.poll();
				break;
			default:
				break;
			}
			// TODO: do stuff
		}
	}

	/**
	 * getActionQueue()
	 * returns the list of actions this unit is in process of doing. 
	 * @return
	 */
	public PriorityQueue<Action> getActionQueue() {
		return actionQueue;
	}

	/**
	 * getCreationTime():
	 * returns the creation time for this unit.
	 * @return
	 */
	public int getCreationTime() {
		return this.baseStats.getCreationTime();
	}
	
	public UnitType getUnitType() {
		return unitType;
	}
	
	/**
	 * decrementCreationTime()
	 * decrements remaining time for unit production
	 * @param int timestep - how much to decrement by
	 */
	public void decrementCreationTime(int timestep)
	{
		this.creationTime -= timestep;
	}
}
