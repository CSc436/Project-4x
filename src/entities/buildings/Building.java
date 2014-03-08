package entities.buildings;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import control.BuildingType;
import control.Tools;
import entities.GameObject;
import entities.GameObjectType;
import entities.stats.BaseStatsEnum;
import entities.stats.UnitStats;
import entities.units.Unit;

public abstract class Building extends GameObject {

	private int height; // height of the structure
	private int width; // width of the structure
	private long castleId = Tools.generateUniqueId();// this is how a building
														// knows what 'city' it
														// belongs to
	private BuildingType buildingType;
	private Queue<Unit> buildingQ = new LinkedList<Unit>();

	public Building(UUID id, int playerId, BaseStatsEnum baseStats,
			UnitStats new_stats, GameObjectType gameObjectType,
			BuildingType buildingType, float xco, float yco, int height,
			int width) {
		super(id, playerId, baseStats, new_stats, gameObjectType, xco, yco);
		this.buildingType = buildingType;
		this.height = height;
		this.width = width;
		// super(p, baseStats, type, xco, yco);
		this.height = height;
		this.width = width;

	}

	public int getHeight() {
		return height;
	}

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

	public Unit dequeueUnit() {
		return buildingQ.poll();
	}

	public long getCastleId() {
		return castleId;
	}
}