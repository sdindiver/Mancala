package com.game.Enum;

public enum Seat {
	NORTH(1), SOUTH(8);

	int pitStartIdx;

	private Seat(int pitStartIdx) {
		this.pitStartIdx = pitStartIdx;
	}

	public int getPitStartIdx() {
		return pitStartIdx;
	}

	public static Seat getSeatType(int pidId) {
		Seat seatType = Seat.NORTH;
		if(pidId >= SOUTH.getPitStartIdx()) {
			seatType = SOUTH;
		}
		return seatType;
	}

}
