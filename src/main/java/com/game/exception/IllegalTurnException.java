package com.game.exception;

public class IllegalTurnException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public IllegalTurnException(String message) {
		super(message);
	}

	public IllegalTurnException(Throwable ex, String message) {
		super(message, ex);
	}
}
