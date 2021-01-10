package com.game.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.game.Enum.GameStatus;
import com.game.Enum.KalahGameType;
import com.game.Enum.Seat;
import com.game.context.KalahContext;
import com.game.model.PlayerAreaInfo;
import com.game.strategy.MoveStrategy;

public final class KalahGame implements Serializable {
	private static final long serialVersionUID = 1L;

	private int gameId;
	private GameStatus gameStatus = GameStatus.WAITING;;
	private int seatedPlayerCount = 0;
	public static final int MAX_PLAYER_COUNT = 2;
	private KalahGameType gameType;
	private final Map<Seat,PlayerArea> gameAreaMap = new HashMap<>();
	private final Map<Player,Seat> playersMap = new HashMap<>();

	private MoveStrategy action;

	private KalahGame(KalahGameType kalahType) {
		this.gameId = ThreadLocalRandom.current().nextInt();
		this.gameStatus = GameStatus.WAITING;
		this.gameType = kalahType;
		this.action = new MoveStrategy(this);
		this.gameAreaMap.put(Seat.NORTH,PlayerArea.getArea(new PlayerAreaInfo(Seat.NORTH,gameType)));
		this.gameAreaMap.put(Seat.SOUTH,PlayerArea.getArea(new PlayerAreaInfo(Seat.SOUTH,gameType)));

	}
	
	
	public boolean makeMoveAllowed() {
		
		return false;
	}

	public static KalahGame getInstance(KalahGameType kalahType) {
		KalahGame game =  new KalahGame(kalahType);
		return game;
	}

	
	public PlayerArea getPlayerArea(Seat seatType) {
		return this.gameAreaMap.get(seatType);
	}
	public boolean isWaiting() {
		return gameStatus == GameStatus.WAITING;
	}

	public KalahGame joinPlayer(Player playerInfo) {
		if(this.seatedPlayerCount==0) {
			this.playersMap.put(playerInfo, Seat.NORTH);
		}else {
			this.playersMap.put(playerInfo, Seat.SOUTH);
		}
		this.seatedPlayerCount++;
		this.updateState();
		return this;
	}
	
	public Seat getPlayerSeat(Player player) {
		return this.playersMap.get(player);
	}
	
	public Seat getCurrentTurnPlayerSeat() {
		Player turnPlayer = KalahContext.getTurnPlayer();
        return getPlayerSeat(turnPlayer);
	}
	
	private void updateState() {
		if(playersMap.size() == MAX_PLAYER_COUNT) {
			startGame();
		}
		
	}
	
	public PlayerArea getCurrentTurnPlayerArea() {
		return this.gameAreaMap.get(getCurrentTurnPlayerSeat());
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
		return seatedPlayerCount;
	}

	public KalahGame makeMove(int pitId) {
		this.action.makeMove(pitId);
		return this;

	}

	public MoveStrategy getMoveStrategy() {
		return action;
	}

}
