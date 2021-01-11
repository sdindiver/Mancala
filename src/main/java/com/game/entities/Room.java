package com.game.entities;

import java.util.HashMap;
import java.util.Map;

import com.game.Enum.Seat;
import com.game.context.KalahContext;
import com.game.entities.Pit.StoneOperationResult;
import com.game.model.PlayerAreaInfo;

/**
 * Gaming room associated with Kalah game
 * 
 * @author indiv
 *
 */
public class Room {

	private Map<Seat, PlayerArea> gameAreaMap = new HashMap<>();
	private Map<Player, Seat> playersMap = new HashMap<>();
	private int seatedPlayerCount = 0;
	@SuppressWarnings("unused")
	private KalahGame game;

	public static final int MAX_PLAYER_COUNT = 2;

	private Room(KalahGame game) {
		this.gameAreaMap.put(Seat.NORTH, PlayerArea.getArea(new PlayerAreaInfo(Seat.NORTH, game.getGameType())));
		this.gameAreaMap.put(Seat.SOUTH, PlayerArea.getArea(new PlayerAreaInfo(Seat.SOUTH, game.getGameType())));
		this.game = game;
	}

	public static Room getInstance(KalahGame game) {
		return new Room(game);
	}

	public PlayerArea getPlayerArea(Seat seatType) {
		return this.gameAreaMap.get(seatType);
	}

	public void joinPlayer(Player playerInfo) {
		if (this.seatedPlayerCount == 0) {
			this.playersMap.put(playerInfo, Seat.NORTH);
		} else {
			this.playersMap.put(playerInfo, Seat.SOUTH);
		}
		this.seatedPlayerCount++;
	}

	public Map<Seat, PlayerArea> getGameAreaMap() {
		return gameAreaMap;
	}

	private Map<Player, Seat> getPlayersMap() {
		return playersMap;
	}

	public int getSeatedPlayerCount() {
		return seatedPlayerCount;
	}

	public Seat getPlayerSeat(Player player) {
		return getPlayersMap().get(player);
	}

	public boolean isFull() {
		return this.seatedPlayerCount == MAX_PLAYER_COUNT;
	}

	public PlayerArea getCurrentTurnPlyrArea() {
		return this.gameAreaMap.get(getCurrentTurnPlayerSeat());
	}

	public Seat getCurrentTurnPlayerSeat() {
		Player turnPlayer = KalahContext.getTurnPlayer();
		return getPlayerSeat(turnPlayer);
	}

	public int size() {
		return this.seatedPlayerCount;
	}

	public int getStoneCount(int pitId) {
		return this.getPlayerArea(Seat.getSeatType(pitId)).getPit(pitId).getStoneCount();
	}

	public PlayerArea getPlayerArea(int pitIdx) {
		return getPlayerArea(Seat.getSeatType(pitIdx));
	}

	public boolean makeEmpty(int pitId) {
		PlayerArea area1 = getPlayerArea(pitId);
		if (area1.getPit(pitId) != null && area1.getPit(pitId).getStoneCount() != 0) {
			area1.getPit(pitId).makeEmpty();
			return true;
		}
		return false;
	}

	private void addCurrentPlayerAreaToContext() {
		KalahContext.setCurrentTurnPlayerArea(getCurrentTurnPlyrArea());
	}

	public StoneOperationResult addStone(int pitidx, int increasedBy) {
		addCurrentPlayerAreaToContext();
		PlayerArea area1 = getPlayerArea(pitidx);
		return area1.addStone(pitidx, increasedBy);
	}

	public Pit getPit(int pitId) {
		PlayerArea area1 = getPlayerArea(pitId);
		Pit pit = area1.getPit(pitId);
		return pit;
	}

	public void stealStones(Pit finalPit) {

	}

	public boolean containsEmptyPlayerArea() {
		for (PlayerArea area : gameAreaMap.values()) {
			if (area.isEmpty()) {
				return true;
			}
		}
		return false;
	}

}
