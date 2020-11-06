package com.dwp.maze.exception;

import com.dwp.maze.service.impl.MazeCoordinate;

public class MovementBlockedException extends RuntimeException {
	private static final long serialVersionUID = 4004595069219201140L;

	public MovementBlockedException(MazeCoordinate location) {
		super(String.format("Movement to location %s is blocked!", location));
	}
}
