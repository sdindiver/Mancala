package com.game.context;

import com.game.entities.Player;
import com.game.entities.PlayerArea;

/**
 * Kalah Game Context that keeps request data
 * @author indiv
 *
 */
public final class KalahContext {
	
	private KalahContext() {
		super();
	}

	private static ThreadLocal<KalahContextInfo> gameThreadLocal = new ThreadLocal<KalahContextInfo>() {
		protected KalahContextInfo initialValue() {
			return new KalahContextInfo();
		};
	};
	
	public static void setTurn(Player player) {
		gameThreadLocal.get().setTurnPlayer(player);
	}
	
	public static Player getTurnPlayer() {
		return gameThreadLocal.get().getTurnPlayer();
	}

	public static void setCurrentTurnPlayerArea(PlayerArea currentTurnPlyrArea) {
		gameThreadLocal.get().setTurnPlayerArea(currentTurnPlyrArea);
		
	}
	
	public static PlayerArea getCurrentTurnPlayerArea() {
		return gameThreadLocal.get().getTurnPlayerArea();
	}
	
	public static void remove() {
		gameThreadLocal.remove();
	}

}



	