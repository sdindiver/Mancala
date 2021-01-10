package com.game.context;

import com.game.entities.Player;

public class KalahContext {

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
}
