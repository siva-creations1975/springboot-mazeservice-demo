package com.dwp.maze.constants;

import java.util.Arrays;
import java.util.Optional;

public enum MazeStructure {
	WALL('X'), SPACE(' '), START('S'), EXIT('F');

	private final char charRepresentation;

	MazeStructure(char charRepresentation) {
		this.charRepresentation = charRepresentation;
	}

	public char charRepresentation() {
		return charRepresentation;
	}

	public static MazeStructure from(char ch) {
		Optional<MazeStructure> structureFromChar = Arrays.stream(MazeStructure.values())
				.filter(ms -> ms.charRepresentation == ch).findFirst();
		return structureFromChar.orElseThrow(
				() -> new IllegalArgumentException(String.format("Maze structure not recognised from '%s'!", ch)));
	}

	public boolean canBeExplored() {
		return this != WALL;
	}
}