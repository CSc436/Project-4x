package com.shared.model.diplomacy.types;

import com.shared.model.diplomacy.AbstractDiplomacy;
import com.shared.model.diplomacy.DiplomacyStandingEnum;

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
public class UnlockedDiplomacy extends AbstractDiplomacy {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5961732500559487007L;

	/**
	 * Constructor that sets all players' alliances towards eachother to one
	 * initial value.
	 * 
	 * @param num_players
	 *            - the number of players in the game.
	 * @param initial
	 *            - the initial standing of the players. (most likely neutral)
	 */
	public UnlockedDiplomacy(int num_players, DiplomacyStandingEnum initial) {
		super(num_players, initial);
	}
	
	public UnlockedDiplomacy() {}

	/**
	 * Constructor that takes an array of player's team numbers. All players
	 * with the same number are allied, all other players are enemies to them.
	 * 
	 * @param players_team
	 *            - An int[] of team numbers for the players in the game.
	 *            Matching team numbers except for -1 means players are allied,
	 *            everyone else is hostile.
	 */
	public UnlockedDiplomacy(int[] players_team) {
		super(players_team);
	}

	/**
	 * @param player1
	 *            - the player whose alliance we are setting.
	 * @param player2
	 *            - the target who player1 is setting the alliance for.
	 * @param standing
	 *            - new standing against player2.
	 */
	@Override
	public void setStanding(int player1, int player2,
			DiplomacyStandingEnum standing) {
		super.setStanding(player1, player2, standing);
	}

	/**
	 * Allows a player to declare war on all other players with a single
	 * command. ex. setAll(playerId, Standing.Enemy);
	 * 
	 * @param player
	 *            - this player is changing his alliance towards all other
	 *            players.
	 * @param standing
	 *            - this is the value he is changing his alliance to.
	 */
	@Override
	public void setAll(int player, DiplomacyStandingEnum standing) {
		super.setAll(player, standing);
	}

	/**
	 * provides a method which allows two players to agree upon a new alliance
	 * standing.
	 * 
	 * @param player1
	 *            - the first player
	 * @param player2
	 *            - the second player
	 * @param standing
	 *            - the mutually agreed upon alliance change.
	 */
	@Override
	public void setMutual(int player1, int player2,
			DiplomacyStandingEnum standing) {
		super.setMutual(player1, player2, standing);
	}
}
