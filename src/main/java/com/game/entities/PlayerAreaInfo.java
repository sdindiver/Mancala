package com.game.entities;

import com.game.Enum.KalahGameType;
import com.game.Enum.Seat;

/** Player Area data holder
 * @author indiv
 *
 */
public class PlayerAreaInfo {
	private Seat seatType;
	private KalahGameType gameType;

	public PlayerAreaInfo(Seat seatType, KalahGameType gameType) {
		super();
		this.seatType = seatType;
		this.gameType = gameType;
	}

	public Seat getSeatType() {
		return seatType;
	}

	public KalahGameType getGameType() {
		return gameType;
	}

}
