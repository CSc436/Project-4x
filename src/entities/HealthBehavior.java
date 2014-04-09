package entities;

public class HealthBehavior implements ObjectBehavior {

	private int hp; // Number of hit points
	private int maxHP; // Number of maximum hit points
	private int damageToTake; // Amount of damage to take during next simulation step
	
	public HealthBehavior() {
		this.maxHP = 10;
		this.hp = maxHP;
	}
	
	public HealthBehavior( int maxHP ) {
		this.maxHP = maxHP;
		this.hp = this.maxHP;
	}
	
	public void takeDamage( int damage ) {
		damageToTake += damage;
	}
	
	@Override
	public void simulateTimeStep(int timeStep) {
		// TODO Auto-generated method stub
		hp -= damageToTake;
		hp = hp < 0 ? 0 : hp ;
		damageToTake = 0;
	}

}
