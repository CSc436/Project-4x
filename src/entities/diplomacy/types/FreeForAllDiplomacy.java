package entities.diplomacy.types;

import entities.diplomacy.DiplomacyStandingEnum;

/**
 * DiplomacyTree
 * 
 * A class that defines the diplomatic relationships between all of the players
 * of a game. This class should be initialized after the number of players has
 * been decided.
 * 
 * @author Colin
 * 
 */
public class FreeForAllDiplomacy extends LockedDiplomacy {

	/**
	 * Constructor that sets all players' alliances towards eachother to one
	 * initial value.
	 * 
	 * @param num_players
	 *            - the number of players in the game.
	 * @param initial
	 *            - the initial standing of the players. (most likely neutral)
	 */
	public FreeForAllDiplomacy(int num_players, DiplomacyStandingEnum initial) {
		super(num_players, DiplomacyStandingEnum.Enemy);
	}

	/**
	 * Constructor that sets all players in the game as Enemies to eachother.
	 * 
	 * @param num_players
	 *            - the number of players in the game.
	 */
	public FreeForAllDiplomacy(int num_players) {
		super(num_players, DiplomacyStandingEnum.Enemy);
	}

	/**
	 * FreeForAllDiplomacy does not care about teams, instead all players are
	 * Enemies of eachother.
	 */
	public FreeForAllDiplomacy(int[] players_team) {
		super(players_team.length, DiplomacyStandingEnum.Enemy);
	}
}
