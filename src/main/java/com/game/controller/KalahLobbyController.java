package com.game.controller;

import java.net.InetAddress;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.entities.KalahGame;
import com.game.entities.Pit;
import com.game.entities.Player;
import com.game.exception.AppControllerAdvice;
import com.game.service.KalahService;
import com.games.dto.GameResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class KalahLobbyController {
	private KalahService kalahService;
	private final Environment environment;

	@Autowired
	public KalahLobbyController(@Qualifier(value = "kalahService") final KalahService kalahService,
			final Environment environment) {
		this.kalahService = kalahService;
		this.environment = environment;
	}
	@ApiOperation(value = "Create or get game",  response = GameResponse.class,
            tags = {"Games"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Returns the game detail with url", response = GameResponse.class),
            @ApiResponse(code = 400, message = "Error Details", response = AppControllerAdvice.Reason.class)})
    @PostMapping("/games")
	public ResponseEntity<Object> joinGame(HttpSession session) {
		Player playerInfo = new Player(session.getId());
		KalahGame game = kalahService.getWaitingGame();
		kalahService.joinPlayer(game, playerInfo);
		GameResponse response = new GameResponse();
		response.setId(game.getGameId());
		response.setUn(getUrl(game.getGameId()));
		return ResponseEntity.ok(response);
	}

	 @ApiOperation(value = "Play the game with pit value", notes = "", response = GameResponse.class,
	            tags = {"Game Play"})
	 @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "Returns the game info with status", response = GameResponse.class),
	            @ApiResponse(code = 404, message = "Error Details", response = AppControllerAdvice.Reason.class),
	            @ApiResponse(code = 400, message = "Error Details", response = AppControllerAdvice.Reason.class),
	            @ApiResponse(code = 401, message = "Error Details", response = AppControllerAdvice.Reason.class)})
    @PutMapping("/games/{gameId}/pits/{pitId}")
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
