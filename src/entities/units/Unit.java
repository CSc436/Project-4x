package entities.units;

import java.util.Date;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import com.shared.PhysicsVector;

import control.GameModel;
import entities.Action;
import entities.resources.Resources;
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

	public PhysicsVector unitPosition;
	public PhysicsVector velocity = new PhysicsVector(0,0);
	
	private UnitType unitType;
	private int creationTime; // this is used...
	private UnitBehavior currentBehavior;
	public  GameObject target;
	
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
						.println("death at :" + position.getX() + " " + position.getY());
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
	
	public double distance(GameObject u){
		return Math.sqrt(Math.pow(u.getX()+this.getX(),2)-Math.pow(u.getY()-this.getY(),2));
	}
	
	public void setTarget(GameObject u){
		this.target=u;
	}
	
	public void clearTarget(GameObject u){
		this.target=null;
	}
	
	public void attack(){
		if(this.target.getHealth()>0){
			this.target.getStats().health-=this.getStats().damage;
			System.out.println(this.target.getHealth());
		}
		else
			System.out.println("The enemy is dead");
	}
	
	//attack & heal (medic will heal in an attack way, with damage below 0)

	public void canAttack(){
		if(this.target==null)
			System.out.println("You must select a target");
		else if(this.getStats().range<this.distance(this.target))
			System.out.println("Out of range");
		else if(this.getUnitType()!=unitType.MEDIC && this==this.target)
			System.out.println("You cannot attack yourself...");
		else if(this.getUnitType()!=unitType.MEDIC && this.getPlayerID()==this.target.getPlayerID())
			System.out.println("You cannot attack your teammate!");
		else if(this.getUnitType()==unitType.MEDIC && this.getPlayerID()!=this.target.getPlayerID())
			System.out.println("You cannot heal your enemies!");
		else{
			new Timer().schedule(new TimerTask(){
				@Override
				public void run() {
					attack();
				}
			}, 1000);
		}
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

	@Override
	public void tick(int timeScale, GameModel model) {
		// TODO Auto-generated method stub
		
	}
	
	/*=============================
	 * Getters for physicsVectors req for Movent of Units
	 ==============================*/
	public PhysicsVector getUnitPosition(){
		if (unitPosition == null){
			unitPosition = new PhysicsVector();
		}
		return this.unitPosition;
	}

	public PhysicsVector getUnitVelocity(){
		return this.velocity;
	}


	public void setUnitPosition(double x, double y){
		this.unitPosition.set(x, y);
	}

	public void setUnitVelocity(PhysicsVector p){
		this.velocity.set(p.getX(), p.getY());
	}
	/**
	 * decrementCreationTime() decrements remaining time for unit production
	 * 
	 * @param int timestep - how much to decrement by
	 */
	public void decrementCreationTime(int timestep) {
		this.creationTime -= timestep;
	}

	public Resources getProductionCost() {

		return this.baseStats.getProductionCost();
	}

}
