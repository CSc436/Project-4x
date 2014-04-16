package entities.buildings;

import java.util.LinkedList;
import java.util.Queue;

import control.BuildingType;
import control.Tools;
import entities.GameObject;
import entities.GameObjectType;
import entities.Producer;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;
import entities.units.Unit;
import entities.util.Point;

public abstract class Building extends GameObject implements Producer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3938726121964982851L;
	private int height; // height of the structure
	private int width; // width of the structure
	private long castleId = Tools.generateUniqueId();// this is how a building
														// knows what 'city' it
														// belongs to
	private BuildingType buildingType;
	
	private Queue<Unit> buildingQ = new LinkedList<Unit>();
//	private int turnsToExecute = 5; // Incase new implementation does not work. 
	
	/**
	 * ResourceBuidling(): Base constructor for Resource Type buildings, calls
	 * super building constructor and sets base amount of resources.
	 * 
	 * Parameters
	 * 
	 * @param UUID
	 *            id - the unique identifier for this Resource Building
	 * @param int playerId - the id of the player who owns this resource
	 *        building
	 * @param baseStats
	 *            - the basic stats for this building (ie health, capacity,
	 *            etc.)
	 * @param new_stats
	 *            - the current stats of this unit (less than or equal to
	 *            baseStats generally)
	 * @param buildingType
	 *            Type of building -
	 * @param float xco - initial x coordinate
	 * @param float yco - initial y coordinate
	 * @param int height - how many tiles high building takes up
	 * @param int width - how many tiles wide the building takes up
	 */
	public Building(int id, int playerId, BaseStatsEnum baseStats,
			UnitStats new_stats,
			BuildingType buildingType, float xco, float yco, int height,
			int width) {
		super(id, playerId, baseStats, new_stats, GameObjectType.BUILDING, xco, yco);
		this.buildingType = buildingType;
		this.height = height;
		this.width = width;
	}

	/**
	 * getHeight() returns the height of the building
	 * 
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * getWidth(): returns the widthe of the building
	 * 
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/*
	 * Holding the Queue for the units that the building is responsible for
	 * producing. It can add units to its queue or remove finished units from
	 * its queue
	 */
	public boolean queueUnit(Unit u) {
		return buildingQ.offer(u);
	}

	/**
	 * dequeueUnit(): takes a unit off of the production queue for this
	 * building, and returns said unit
	 * 
	 * @return Unit that building finished creating
	 */
	public Unit dequeueUnit() {
		return buildingQ.poll();
	}

	/**
	 * getCastleID() Returns the castle ID that is associated with this building
	 * 
	 * @return ong for castle id
	 */
	public long getCastleId() {
		return castleId;
	}

	/**
	 * productionQueueEmpty() returns true if the production queue for this
	 * building is empty
	 * 
	 * @return true if queue is empty
	 */
	public boolean productionQueueEmpty() {

		return buildingQ.isEmpty();
	}

	/*public Unit advanceUnitProduction() {
		System.out.println("running");
		if (buildingQ.size() == 0)
			return null;
		
		if (turnsToExecute > 0) {
			turnsToExecute--;
			return null;
		}
		else {
			turnsToExecute = 5;
			return dequeueUnit();
		}*/ // In case bellow one breaks....
	/**
	 * advanceUnitProduction(): increments how far along current unit production
	 * is.
	 * 
	 * @param timestep
	 *            - amount to increment player production
	 * 
	 * @return if not null add unit to player, if null, do nothing.
	 */
	public Unit advanceUnitProduction(int timestep) {
		// add timestep to each unit
		if (!productionQueueEmpty()) {
			Unit u = buildingQ.peek();
			u.decrementCreationTime(timestep);
			if (u.getCreationTime() <= 0) {
				return buildingQ.poll();
			}
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public Unit getProducingUnit() {
		return buildingQ.peek();
	}

	//will have to pla
	public BuildingType getBuildingType() {

		return buildingType;
	}

	public Point getPosition() {
		float x = (float) moveBehavior.position.getX();
		float y = (float) moveBehavior.position.getY();
		return new Point(x,y);
	}
}