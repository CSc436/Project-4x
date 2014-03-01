package entities.stats;


public class BuffStats extends UnitStats {
	
	public BuffStats() {
		super(0, 0, 0, 0, 0, 0, 0);
	}
	
	public void add(BuffStats stats) {
		damage += stats.damage;
		range += stats.range;
		armor += stats.armor;
		health_regen += stats.health_regen;
		max_health += stats.max_health;
		movementSpeed += stats.movementSpeed;
		actionSpeed += stats.actionSpeed;
	}
	
	public void subtract(BuffStats stats) {
		damage -= stats.damage;
		range -= stats.range;
		armor -= stats.armor;
		health_regen -= stats.health_regen;
		max_health -= stats.max_health;
		movementSpeed -= stats.movementSpeed;
		
		actionSpeed -= stats.actionSpeed;
	}

	public UnitStats augment(UnitStats s) {
		s.damage += damage;
		s.range += range;
		s.armor += armor;
		s.health_regen += health_regen;
		s.max_health += max_health;
		s.movementSpeed += movementSpeed;
		s.actionSpeed += actionSpeed;
		return s;
	}
}
