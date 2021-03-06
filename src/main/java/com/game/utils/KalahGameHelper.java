package com.game.utils;

import java.net.InetAddress;

/** Kalah Game Utility Helper
 * @author indiv
 *
 */
public final class KalahGameHelper {
	
	private KalahGameHelper() {
		super();
	}

	public static String getUrl(String port, final int gameId) {
		return String.format("http://%s:%s/games/%s", InetAddress.getLoopbackAddress().getHostName(), port, gameId);
	}

}


