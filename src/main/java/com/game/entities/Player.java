package com.game.entities;

/** Player of Kalah Game
 * @author indiv
 *
 */
public final class Player {

	private String playerId;

	public Player(String playerId) {
		super();
		this.playerId = playerId;
	}

	public String getPlayerId() {
		return playerId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((playerId == null) ? 0 : playerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (playerId == null) {
			if (other.playerId != null)
				return false;
		} else if (!playerId.equals(other.playerId))
			return false;
		return true;
	}
	
	
}
