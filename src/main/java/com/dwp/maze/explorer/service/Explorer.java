package com.dwp.maze.explorer.service;

import java.util.List;
import java.util.Optional;

import com.dwp.maze.constants.HeadingDirectionClockWise;
import com.dwp.maze.constants.MazeStructure;
import com.dwp.maze.explorer.ExplorerPosition;
import com.dwp.maze.service.impl.MazeCoordinate;

/**
 * Explorer of a maze
 */
public interface Explorer {

	/**
	 * Move forward based on the current position's coordinate and heading
	 * direction
	 */
	void moveForward();

	/**
	 * Move forward n times
	 *
	 * @param times
	 *            the number of fields the explorer needs to move forward
	 */
	default void moveForward(int times) {
		for (int i = 0; i < times; i++)
			moveForward();
	}

	/**
	 * set heading direction to 90 degrees anti clockwise e.g. UP becomes LEFT
	 */
	void turnLeft();

	/**
	 * set heading direction to 90 degrees clockwise e.g. UP becomes RIGHT
	 */
	void turnRight();

	/**
	 * Set heading direction to the given direction
	 *
	 * @param direction
	 *            the given direction the explorer should face to
	 */
	void turnTo(HeadingDirectionClockWise direction);

	/**
	 * Set heading direction of the explorer and move one forward
	 *
	 * @param direction
	 *            the given direction the explorer should face to before the
	 *            movement made
	 */
	default void moveTo(HeadingDirectionClockWise direction) {
		turnTo(direction);
		moveForward();
	}

	/**
	 * return the possible directions the explorer can move based on the current
	 * position's coordinate
	 *
	 * @return
	 */
	List<HeadingDirectionClockWise> getPossibleDirections();

	/**
	 * Get the type of the field the explorer is facing against
	 *
	 * @return the type of the field in front of the explorer
	 */
	Optional<MazeStructure> whatsInFront();

	/**
	 * Get the type of the field on which coordinate the explorer currently at
	 *
	 * @return the type of the field under the explorer
	 */
	MazeStructure whatsAtMyLocation();

	/**
	 * Get the movement history of the explorer
	 *
	 * @return the movement history of the explorer
	 */
	List<MazeCoordinate> getMovement();

	/**
	 * Get the current position of the explorer
	 *
	 * @return the current position of the explorer including coordinate and
	 *         heading direction
	 */
	ExplorerPosition getPosition();

}
