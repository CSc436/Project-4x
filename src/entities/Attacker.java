package entities;

public interface Attacker extends Simulatable {
	
	public void setTarget( GameObject target );
	
	public void startAttack();
	
	public void stopAttack();
	
}
