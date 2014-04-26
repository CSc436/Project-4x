package entities.stats;

import java.io.Serializable;

public class UnitStats implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5798939504989341306L;
	public int damage;		// how much damage this unit can deal out
	public float range;		// range of attack for this unit (archers have greater range than knights)
	public int armor;		// How much armor defends this unit 

	public float health_regen;	// how quickly the health of this unit regenerates
	public float health;		// the current health of this unit
	public float max_health;	// the max_health of this unit

	public float movementSpeed;	// the movement speed of this unit (how many tiles per tick they can cross)
	// like resource generation / attack speed.
	public float actionSpeed;

	/**
	 * UnitStats():
	 * 
	 * @param damage
	 *            - how much damage unit does
	 * @param range
	 *            - attack range NOT LOS.
	 * @param armor
	 *            - armor defense, impedes attacks
	 * @param health_regen
	 *            - how quickly they can regenerate health (i.e. how much health
	 *            is generated per turn
	 * @param health
	 *            - the current health of this unit
	 * @param movementSpeed
	 *            - how quickly this unit can move
	 * @param actionSpeed
	 *            - how quickly they action
	 */
	public UnitStats(int damage, float range, int armor, float health_regen,
			float health, float movementSpeed, float actionSpeed) {
		this.damage = damage;
		this.range = range;
		this.armor = armor;

		this.health_regen = health_regen;
		this.health = health;
		this.max_health = health;

		this.movementSpeed = movementSpeed;
		this.actionSpeed = actionSpeed;
	}
	
	public UnitStats() {}

	public UnitStats clone() {
		return new UnitStats(damage, range, armor, health_regen, max_health,
				movementSpeed, actionSpeed);
	}
}
