package com.dwp.maze.exception;

public class MazeServiceException extends RuntimeException {
	private static final long serialVersionUID = 4004595069219201140L;

	public MazeServiceException(String errorMessage) {
		super(errorMessage);
	}
}
