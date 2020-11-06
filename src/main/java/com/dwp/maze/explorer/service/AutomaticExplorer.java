package com.dwp.maze.explorer.service;

import java.util.List;
import java.util.Optional;

import com.dwp.maze.service.impl.MazeCoordinate;

public interface AutomaticExplorer {

	/**
	 * Search the way out from the maze and returning the movement history
	 *
	 * @return the movement history that was made to find the way out or empty
	 *         if no exit found
	 */
	Optional<List<MazeCoordinate>> searchWayOut();
}
