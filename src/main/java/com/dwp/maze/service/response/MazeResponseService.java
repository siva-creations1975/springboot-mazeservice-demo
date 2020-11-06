package com.dwp.maze.service.response;

import com.dwp.maze.constants.MazeStructure;
import com.dwp.maze.service.impl.MazeCoordinate;

/**
 * API for a MAZE Response Service
 */
public interface MazeResponseService {
	/**
	 * Get the number of walls
	 *
	 * @return the number of walls
	 */
	long getNumberOfWalls();

	/**
	 * Get the number of empty spaces
	 *
	 * @return the number of empty spaces
	 */
	long getNumberOfEmptySpaces();

	/**
	 * Get the number of columns
	 *
	 * @return the number of columns
	 */
	int getDimensionX();

	/**
	 * Get the number of rows
	 *
	 * @return the number of rows
	 */
	int getDimensionY();

	/**
	 * Get the start location
	 *
	 * @return the start location of the Maze
	 */
	MazeCoordinate getStartLocation();

	/**
	 * Get the exit location
	 *
	 * @return the exit location of the maze
	 */
	MazeCoordinate getExitLocation();

	/**
	 * Get what is at a coordinate
	 *
	 * @param coord
	 *            the coordinate
	 *
	 * @return the type of field on a given coordinate
	 */
	MazeStructure whatsAt(MazeCoordinate coord);
}
