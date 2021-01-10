package com.game.strategy;

import com.game.Enum.Seat;
import com.game.entities.KalahGame;
import com.game.entities.PlayerArea;
import com.game.exception.IllegalTurnException;

public class MoveStrategy {
	
	private KalahGame game;
	
	public MoveStrategy(KalahGame kalahGame) {
		this.game = kalahGame;
	}

	public void makeMove(int pitId) {
		if(!validateMove(pitId)) {
			throw new IllegalTurnException("Turn is illegal. So not allowed");
		}
		PlayerArea area1 = game.getPlayerArea(Seat.NORTH);
		PlayerArea area2 = game.getPlayerArea(Seat.SOUTH);
		//Write business logic here
		
	}

	protected boolean validateMove(int pitId) {
		int reminder = pitId%(game.getGameType().getStoneCount());
		if(reminder ==0) {
			throw new IllegalArgumentException("PitId is Kalah House. Move is not allowed");
		}
		Seat requestedTurn = Seat.getSeatType(pitId);
		Seat actualTurn  = game.getCurrentTurnPlayerSeat();
		if(requestedTurn != actualTurn) {
			throw new IllegalArgumentException("This is not current turn Player's pits.");
		}
		return true;
	}

}
