package entities.units;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.UUID;

import control.UnitType;
import entities.Action;
import entities.stats.BaseStatsEnum;
import entities.GameObject;
import entities.GameObjectType;
import entities.stats.UnitStats;
import entities.units.behavior.UnitBehavior;

/*
 * 
 * 
 * Purpose:  This abstract class defines the concept of a unit.  Every unit will atleast have the following 
 * information known about itself.  
 */

// TODO add A* path finding, use diagonals to make nice looking paths
// returns a queue/list of tiles that it needs to go to, at each turn pop one off and move player there. 

public class Unit extends GameObject {

	private UnitType unitType;
	private int creationTime; // this is used...
	private UnitBehavior currentBehavior;

	public Unit(UUID id, int playerId, BaseStatsEnum baseStats,
			UnitStats new_stats, GameObjectType type, UnitType unitType,
			float xco, float yco) {
		super(id, playerId, baseStats, new_stats, type, xco, yco);
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
	
	public double distance(Unit u){
		return Math.sqrt(Math.pow(u.getX()+this.getX(),2)-Math.pow(u.getY()-this.getY(),2));
	}
	
	//attack & heal (medic will heal in an attack way, with damage below 0)
	public void attack(Unit u){
		if(this.getStats().range<this.distance(u))
			System.out.println("Out of range");
		else if(this.getUnitType()!=unitType.MEDIC && this==u)
			System.out.println("You cannot attack yourself...");
		else if(this.getUnitType()!=unitType.MEDIC && this.getPlayerID()==u.getPlayerID())
			System.out.println("You cannot attack your teammate!");
		else if(this.getUnitType()==unitType.MEDIC && this.getPlayerID()!=u.getPlayerID())
			System.out.println("You cannot heal your enemies!");
		else
			u.getStats().health-=this.getStats().damage;
	}
	
	public PriorityQueue<Action> getActionQueue() {
		return actionQueue;
	}

	public int getCreationTime() {
		return this.baseStats.getCreationTime();
	}
	
	public UnitType getUnitType() {
		return unitType;
	}

	@Override
	protected void setActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, String> getActions() {
		// TODO Auto-generated method stub
		return null;
	}

}
