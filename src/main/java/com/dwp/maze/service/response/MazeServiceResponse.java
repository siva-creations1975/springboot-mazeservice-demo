package com.dwp.maze.service.response;

import com.dwp.maze.constants.MazeStructure;
import com.dwp.maze.service.impl.MazeCoordinate;

public final class MazeServiceResponse implements MazeResponseService {
	private final String[] mazeData;

	private final int dimensionX;

	private final int dimensionY;

	private final long numberOfWalls;
	private final long numberOfEmptySpaces;

	private final MazeCoordinate startLocation;

	private final MazeCoordinate exitLocation;

	public MazeServiceResponse(String[] mazeData, int dimensionX, int dimensionY, MazeCoordinate startLocation,
			MazeCoordinate exitLocation, long numberOfWalls, long numberOfEmptySpaces) {
		this.mazeData = mazeData;

		this.dimensionX = dimensionX;
		this.dimensionY = dimensionY;
		this.startLocation = startLocation;
		this.exitLocation = exitLocation;
		this.numberOfWalls = numberOfWalls;
		this.numberOfEmptySpaces = numberOfEmptySpaces;
	}

	public long getNumberOfWalls() {
		return numberOfWalls;
	}

	public long getNumberOfEmptySpaces() {
		return numberOfEmptySpaces;
	}

	public int getDimensionX() {
		return dimensionX;
	}

	public int getDimensionY() {
		return dimensionY;
	}

	public MazeCoordinate getStartLocation() {
		return startLocation;
	}

	public MazeCoordinate getExitLocation() {
		return exitLocation;
	}

	public MazeStructure whatsAt(MazeCoordinate coord) {
		return MazeStructure.from(mazeData[coord.getY()].charAt(coord.getX()));
	}
}
