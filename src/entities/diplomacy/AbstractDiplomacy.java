package entities.diplomacy;

public abstract class AbstractDiplomacy implements IDiplomacy {
	/** Alliance Table */
	protected final DiplomacyStandingEnum[][] table;

	/**
	 * Constructor that sets all players' alliances towards each other to one
	 * initial value.
	 * 
	 * @param num_players
	 *            - the number of players in the game.
	 * @param initial
	 *            - the initial standing of the players. (most likely
	 *            neutral/enemy)
	 */
	protected AbstractDiplomacy(int num_players, DiplomacyStandingEnum initial) {
		this(num_players);
		for (int i = 0; i < num_players; i++) {
			for (int j = 0; j < num_players; j++) {
				setStanding(i, j, initial);
			}
		}
		setSelfToAllied(num_players);
	}

	/**
	 * Constructor that sets up the game board size, but does not set any of the
	 * initial values.
	 * 
	 * @param num_players
	 *            - the number of players in the game.
	 */
	private AbstractDiplomacy(int num_players) {
		table = new DiplomacyStandingEnum[num_players][num_players];
	}

	/**
	 * Constructor that takes an array of player's team numbers. All players
	 * with the same number are allied, all other players are enemies to them.
	 * 
	 * @param players_team
	 *            - An int[] of team numbers for the players in the game.
	 *            Matching team numbers except for -1 means players are allied,
	 *            everyone else is hostile.
	 */
	protected AbstractDiplomacy(int[] players_team) {
		this(players_team.length);
		setPlayersTeams(players_team);
		setSelfToAllied(players_team.length);
	}

	/**
	 * A method for setting the player alliances based on team values. All
	 * players with the same team are allied, except for team -1.
	 * 
	 * @param players_team
	 *            - the array of team values for the players.
	 */
	private void setPlayersTeams(int[] players_team) {
		int count = players_team.length;
		for (int i = 0; i < count; i++) {
			for (int j = 0; j < count; j++) {
				if (players_team[j] == players_team[i] && players_team[j] != -1) {
					setStanding(i, j, DiplomacyStandingEnum.Allied);
				} else {
					setStanding(i, j, DiplomacyStandingEnum.Enemy);
				}
			}
		}
	}

	/**
	 * Sets each player to be their own ally.
	 * 
	 * @param num_players
	 *            - the number of players in the game.
	 */
	private void setSelfToAllied(int num_players) {
		for (int i = 0; i < num_players; i++) {
			setStanding(i, i, DiplomacyStandingEnum.Allied);
		}
	}

	/**
	 * method getStanding - gets the alliance of player 1 towards player 2;
	 * 
	 * @param player1
	 *            - the player who we are checking.
	 * @param player2
	 *            - player1's alliance standing to this player.
	 */
	public DiplomacyStandingEnum getStanding(int player1, int player2) {
		return table[player1][player2];
	}

	/**
	 * @param player1
	 *            - the player whose alliance we are setting.
	 * @param player2
	 *            - the target who player1 is setting the alliance for.
	 * @param standing
	 *            - new standing against player2.
	 */
	public void setStanding(int player1, int player2,
			DiplomacyStandingEnum standing) {
		table[player1][player2] = standing;
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
	public void setAll(int player, DiplomacyStandingEnum standing) {
		for (int i = 0; i < this.table.length; i++) {
			table[player][i] = standing;
		}
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
	public void setMutual(int player1, int player2,
			DiplomacyStandingEnum standing) {
		table[player1][player2] = standing;
		table[player2][player1] = standing;
	}

	/**
	 * @return - returns a representation of the 2d Alliance Table.
	 */
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				if (j != 0) {
					b.append("/");
				}
				b.append(table[i][j].name());
			}
			b.append("\n");
		}
		return b.toString();
	}
}
