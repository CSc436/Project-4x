package entities.stats;

public class UnitStats {
	public int damage;
	public float range;
	public int armor;

	public float health_regen;
	public float health;
	public float max_health;

	public float movementSpeed;
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

	public UnitStats clone() {
		return new UnitStats(damage, range, armor, health_regen, max_health,
				movementSpeed, actionSpeed);
	}
}
