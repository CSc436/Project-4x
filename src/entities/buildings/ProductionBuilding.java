package entities.buildings;

import entities.units.Unit;

public interface ProductionBuilding {

	/**
	 * advanceUnitProduction(): increments how far along current unit production
	 * is.
	 * 
	 * @param timestep
	 *            - amount to increment player production
	 * @param class1
	 * 
	 * @return if not null add unit to player, if null, do nothing.
	 */

	public Unit advanceUnitProduction(int timestep);

	/**
	 * queueUnit(): queues unit u on the building production queue
	 * 
	 * Holding the Queue for the units that the building is responsible for
	 * producing. It can add units to its queue or remove finished units from
	 * its queue
	 * 
	 * When a unit is ordered to be produced, the player is charged the
	 * production costs. If the player decides to cancel the order, he is
	 * refunded.
	 * 
	 * If the unit costs more resources than the player has, the unit is not
	 * queued, and false is returned
	 * 
	 * @return true if the unit was queued, false on failure
	 */
	public boolean queueUnit(Unit u);

	/**
	 * dequeueUnit(): takes a unit off of the production queue for this
	 * building, and returns said unit
	 * 
	 * @return Unit that building finished creating
	 */
	public Unit dequeueUnit();

	/**
	 * productionQueueEmpty() returns true if the production queue for this
	 * building is empty
	 * 
	 * @return true if queue is empty
	 */
	public boolean productionQueueEmpty();

	/**
	 * getProducingUnit() returns a reference to the Unit on the top of the
	 * queue if it is there [queue.peek]
	 * 
	 * @return first unit on the building queue or null if no units are queued
	 */
	public Unit getProducingUnit();

}
