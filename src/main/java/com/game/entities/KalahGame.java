package com.game.entities;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

import com.game.Enum.GameStatus;
import com.game.Enum.KalahGameType;
import com.game.Enum.Seat;
import com.game.context.KalahContext;
import com.game.strategy.MoveStrategy;

public final class KalahGame implements Serializable {
	private static final long serialVersionUID = 1L;

	private int gameId;
	private GameStatus gameStatus = GameStatus.WAITING;;
	private KalahGameType gameType;
	private Room room;
	Player disAllowedPlayer;

	private MoveStrategy action;

	private KalahGame(KalahGameType kalahType) {
		this.gameId = Math.abs(ThreadLocalRandom.current().nextInt());
		this.gameStatus = GameStatus.WAITING;
		this.gameType = kalahType;
		this.action = new MoveStrategy(this);
		this.room = Room.getInstance(this);
	}

	public void makeMoveDisAllowed(Player player) {
		disAllowedPlayer=player;
	}

	public static KalahGame getInstance(KalahGameType kalahType) {
		KalahGame game = new KalahGame(kalahType);
		return game;
	}

	public Room getRoom() {
		return this.room;
	}

	public PlayerArea getPlayerArea(Seat seatType) {
		return getRoom().getPlayerArea(seatType);
	}

	public boolean isWaiting() {
		return gameStatus == GameStatus.WAITING;
	}

	public KalahGame joinPlayer(Player playerInfo) {
		getRoom().joinPlayer(playerInfo);
		this.updateStateIfAny();
		return this;
	}

	public Seat getPlayerSeat(Player player) {
		return getRoom().getPlayerSeat(player);
	}

	public Seat getCurrentTurnPlayerSeat() {
		return room.getCurrentTurnPlayerSeat();
	}

	public void updateStateIfAny() {
		if (getRoom().isFull()) {
			startGame();
		}
		if(getRoom().containsEmptyPlayerArea()) {
			GameOver();
		}

	}

	private void GameOver() {
		this.gameStatus= GameStatus.GAMEOVER;
		
	}

	public PlayerArea getCurrentTurnPlayerArea() {
		return room.getCurrentTurnPlyrArea();
	}

	private void startGame() {
		this.gameStatus = GameStatus.STARTED;
	}

	public KalahGameType getGameType() {
		return gameType;
	}

	public int getGameId() {
		return gameId;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public int getSeatedPlayerCount() {
		return this.room.size();
	}

	public KalahGame makeMove(int pitId) {
		this.action.makeMove(pitId);
		return this;

	}

	public MoveStrategy getMoveStrategy() {
		return action;
	}

	public boolean hasGameStatus(GameStatus gameStatus) {
		return this.gameStatus == gameStatus;
	}

	public boolean isMoveAllowed(Player playerInfo) {
		return  !playerInfo.equals(this.disAllowedPlayer);
	}

	public boolean isCurrentPlayerPit(Pit pit) {
		return KalahContext.getCurrentTurnPlayerArea().contains(pit);
	}

}
