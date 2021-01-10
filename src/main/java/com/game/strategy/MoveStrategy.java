package com.game.strategy;

import com.game.Enum.Seat;
import com.game.context.KalahContext;
import com.game.entities.KalahGame;
import com.game.entities.Pit;
import com.game.exception.IllegalTurnException;

public class MoveStrategy {
	
	private KalahGame game;
	
	public MoveStrategy(KalahGame kalahGame) {
		this.game = kalahGame;
	}

	public void makeMove(int pitId) {
		validateMove(pitId);
		int iterationCnt = game.getRoom().getPit(pitId).getStoneCount();
		this.game.getRoom().makeEmpty(pitId);
		
		int i=1;
		int pitidx = pitId;
		int finalPitIdx = pitId;
		while(i<= iterationCnt) {
			if(game.getRoom().addStone(pitidx++,1).isSuccess()) {
				i++;
				finalPitIdx=pitidx;
			}
		}
		Pit finalPit = game.getRoom().getPit(finalPitIdx);
		if(!finalPit.isKalah()) {
			game.makeMoveDisAllowed(KalahContext.getTurnPlayer());

		}
		if(finalPit.getStoneCount() ==1) {
			game.getRoom().stealStones(finalPit);
		}
		
		game.updateStateIfAny();
		
	}

	protected boolean validateMove(int pitId) {
		if(!game.isMoveAllowed(KalahContext.getTurnPlayer())) {
			throw new IllegalTurnException("Wrong player turn is now allowed");

		}
		if(game.getRoom().getPit(pitId).isKalah()) {
			throw new IllegalTurnException("Pit is Kalah. Move is not allowed");
		}
		if(game.getRoom().getPit(pitId).isEmpty()) {
			throw new IllegalTurnException("Pit contains 0 stones. Move is not allowed");
		}
		Seat requestedTurn = Seat.getSeatType(pitId);
		Seat actualTurn  = game.getCurrentTurnPlayerSeat();
		if(requestedTurn != actualTurn) {
			throw new IllegalTurnException("This is not current turn Player's pits.");
		}
		
		return true;
	}

}
