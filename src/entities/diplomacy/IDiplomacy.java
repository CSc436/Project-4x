package entities.diplomacy;

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
public interface IDiplomacy {

	/**
	 * method getStanding - gets the alliance of player 1 towards player 2;
	 * 
	 * @param player1
	 *            - the player who we are checking.
	 * @param player2
	 *            - player1's alliance standing to this player.
	 */
	public DiplomacyStandingEnum getStanding(int player1, int player2);

	/**
	 * @param player1
	 *            - the player whose alliance we are setting.
	 * @param player2
	 *            - the target who player1 is setting the alliance for.
	 * @param standing
	 *            - new standing against player2.
	 */
	public void setStanding(int player1, int player2,
			DiplomacyStandingEnum standing);

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
	public void setAll(int player, DiplomacyStandingEnum standing);

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
	public void setMutual(int player1, int player2,
			DiplomacyStandingEnum standing);
}
