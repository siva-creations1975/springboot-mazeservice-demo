package com.dwp.maze.exception;

public class FieldIsOutOfMazeBoundsException extends RuntimeException {
	private static final long serialVersionUID = -4776764753531851637L;

	public FieldIsOutOfMazeBoundsException() {
		super("Field is out of the maze!");
	}
}
