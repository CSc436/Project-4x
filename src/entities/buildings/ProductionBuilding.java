package entities.buildings;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;
import entities.units.Unit;

public class ProductionBuilding extends Building {

	private Queue<Unit> buildingQ = new LinkedList<Unit>();

	public ProductionBuilding(UUID id, int playerId, BaseStatsEnum baseStats,
			UnitStats new_stats, BuildingType buildingType, float xco,
			float yco, int height, int width) {
		super(id, playerId, baseStats, new_stats, buildingType, xco, yco,
				height, width);
		// TODO Auto-generated constructor stub
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

	/*
	 * public Unit advanceUnitProduction() { System.out.println("running"); if
	 * (buildingQ.size() == 0) return null;
	 * 
	 * if (turnsToExecute > 0) { turnsToExecute--; return null; } else {
	 * turnsToExecute = 5; return dequeueUnit(); }
	 */// In case bellow one breaks....
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

	/*
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
	 */
	public boolean queueUnit(Unit u) {

		boolean b = this.getPlayer().chargePlayerForUnitProduction(
				u.getProductionCost());

		// if the player can afford to build the unit, queue it, otherwise
		// return false;
		if (b) {

			return buildingQ.offer(u);

		} else {
			return false;
		}
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
	 * productionQueueEmpty() returns true if the production queue for this
	 * building is empty
	 * 
	 * @return true if queue is empty
	 */
	public boolean productionQueueEmpty() {

		return buildingQ.isEmpty();
	}

	/**
	 * 
	 * @return
	 */
	public Unit getProducingUnit() {
		return buildingQ.peek();
	}



}
