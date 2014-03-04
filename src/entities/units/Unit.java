package entities.units;

import java.util.PriorityQueue;
import java.util.UUID;

import com.fourx.buffs.UnitType;

import entities.Action;
import entities.BaseStatsEnum;
import entities.GameObject;
import entities.Locatable;
import entities.UnitStats;

/*
 * 
 * 
 * Purpose:  This abstract class defines the concept of a unit.  Every unit will atleast have the following 
 * information known about itself.  
 */

// infantry
// calvary
// ranged
// water?
// transport?
// trade cart
// settler / worker
// healing unit

public abstract class Unit extends GameObject  {
	
	private UnitType unitType;

	public Unit(UUID id, int playerId, BaseStatsEnum baseStats, UnitStats new_stats, GameObjectType type, UnitType unitType, float xco,
			float yco) {
		super(id, playerId,  baseStats, new_stats, type, xco, yco);
		this.unitType = unitType;
	}

	//not sure if needed
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
				System.out.println("death at :" + position.x + " " + position.y);
				//getOwner().getUnits().removeUnit(this);
				while (!actionQueue.isEmpty())
					actionQueue.poll();
				break;
			default:
				break;
			}
			// TODO: do stuff
		}
	}

	public PriorityQueue<Action> getActionQueue() {
		return actionQueue;
	}
}
