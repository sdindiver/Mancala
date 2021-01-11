package com.game.controller;

import java.net.InetAddress;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.game.entities.KalahGame;
import com.game.entities.Pit;
import com.game.entities.Player;
import com.game.service.KalahService;
import com.games.dto.GameResponse;

@RestController
@RequestMapping(path = "/games")
public class KalahLobbyController {
	private KalahService kalahService;
	private final Environment environment;

	@Autowired
	public KalahLobbyController(@Qualifier(value = "kalahService") final KalahService kalahService,
			final Environment environment) {
		this.kalahService = kalahService;
		this.environment = environment;
	}

	@RequestMapping(path = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> joinGame(HttpSession session) {
		Player playerInfo = new Player(session.getId());
		KalahGame game = kalahService.getWaitingGame();
		kalahService.joinPlayer(game, playerInfo);
		GameResponse response = new GameResponse();
		response.setId(game.getGameId());
		response.setUn(getUrl(game.getGameId()));
		return ResponseEntity.ok(response);
	}

	@RequestMapping(path = "/{gameId}/pits/{pitId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> makeMove(HttpSession session, @PathVariable("gameId") int gameId,
			@PathVariable("pitId") int pitId) {
		Player playerInfo = new Player(session.getId());
		KalahGame game = kalahService.makeMove(playerInfo, gameId, pitId);

		final Map<Integer, Integer> statusMap = game.getRoom().getGameAreaMap().values().stream()
				.flatMap(x -> x.getPits().stream())
				.collect(Collectors.toMap(Pit::getPitId, value -> value.getStoneCount()));
		GameResponse response = new GameResponse();
		response.setId(game.getGameId());
		response.setUn(getUrl(game.getGameId()));
		response.setStatus(statusMap);
		return ResponseEntity.ok(response);

	}

	private String getUrl(final int gameId) {
		final int port = environment.getProperty("server.port", Integer.class, 8080);
		return String.format("http://%s:%s/games/%s", InetAddress.getLoopbackAddress().getHostName(), port, gameId);
	}

}
