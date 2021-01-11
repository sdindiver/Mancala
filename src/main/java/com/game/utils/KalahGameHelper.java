package com.game.utils;

import java.net.InetAddress;

public class KalahGameHelper {

	public static String getUrl(String port, final int gameId) {
		return String.format("http://%s:%s/games/%s", InetAddress.getLoopbackAddress().getHostName(), port, gameId);
	}

}


