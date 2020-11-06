package com.dwp.maze.service;

import com.dwp.maze.exception.MazeServiceException;
import com.dwp.maze.service.response.MazeResponseService;

/**
 * API for a MAZE
 */
public interface MazeService {
	/**
	 * Process Maze service request
	 *
	 * @return MazeServiceResponse
	 */
	MazeResponseService processMazeService(String mazeStr) throws MazeServiceException;
}
