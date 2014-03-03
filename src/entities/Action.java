package entities;
/*
 * 
 * 
 * 
 */
// aggressive, passive 
// attack move, agent move, 
public enum Action implements Comparable<Action> {
	DEATH(10),HALT(9),PLAYER_ATTACK_MOVE(7),PLAYER_ATTACK(1), AGENTMOVEMENT(2);
	
	private final int priority;

	
	private Action(int priority) {
		this.priority = priority;
	}
	
	public int compareTo(Action a, Action b) {
		return a.priority - b.priority;
	}
}
