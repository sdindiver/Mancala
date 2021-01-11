package com.game.strategy;

import com.game.Enum.Seat;
import com.game.context.KalahContext;
import com.game.entities.KalahGame;
import com.game.entities.Pit;
import com.game.exception.ApplicationException;
import com.game.exception.ApplicationException.Builder;
import com.game.exception.ErrorCode;

/**main Business logic layer to be performed if player makes a move, It perfoms validation and makes a move and changes
 * player turn
 * @author indiv
 *
 */
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
			if(game.getRoom().addStone(++pitidx,1).isSuccess()) {
				i++;
				finalPitIdx=pitidx;
			}
		}
		Pit finalPit = game.getRoom().getPit(finalPitIdx);
		if(!finalPit.isKalah()) {
			game.makeMoveDisAllowed(KalahContext.getTurnPlayer());
		}
		
		if(game.isCurrentPlayerPit(finalPit)&& finalPit.getStoneCount() ==1) {
			game.getRoom().stealStones(finalPit);
		}
		
		game.updateStateIfAny();
		
	}

	protected boolean validateMove(int pitId) {
		Builder errorBuilder =ApplicationException.Builder.builder();
		if(!game.isMoveAllowed(KalahContext.getTurnPlayer())) {
			throw errorBuilder.error(ErrorCode.TURN_CHANGED_ALREADY).build().get();

		}
		if(game.getRoom().getPit(pitId).isKalah()) {
			throw errorBuilder.error(ErrorCode.WRONG_PIT_MOVE_NOT_ALLOWED).build().get();
		}
		if(game.getRoom().getPit(pitId).isEmpty()) {
			throw errorBuilder.error(ErrorCode.ZERO_STONE_PIT_MOVE_NOT_ALLOWED).build().get();
		}
		Seat requestedTurn = Seat.getSeatType(pitId);
		Seat actualTurn  = game.getCurrentTurnPlayerSeat();
		if(actualTurn!= null && requestedTurn != actualTurn) {
			throw errorBuilder.error(ErrorCode.PIT_ACCESS_NOT_AUTHROIZED).build().get();
		}
		
		return true;
	}

}
