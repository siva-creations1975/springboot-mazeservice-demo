package com.dwp.maze.explorer;

import com.dwp.maze.constants.HeadingDirectionClockWise;
import com.dwp.maze.exception.FieldIsOutOfMazeBoundsException;
import com.dwp.maze.service.impl.MazeCoordinate;
import com.dwp.maze.service.response.MazeResponseService;

public class ExplorerPosition {

	private final MazeCoordinate coordinate;

	private final HeadingDirectionClockWise direction;

	public ExplorerPosition(MazeCoordinate location, HeadingDirectionClockWise direction) {
		this.coordinate = location;
		this.direction = direction;
	}

	public MazeCoordinate getCoordinate() {
		return coordinate;
	}

	public HeadingDirectionClockWise getDirection() {
		return direction;
	}

	public ExplorerPosition withCoordinate(MazeCoordinate newCoordinate) {
		return new ExplorerPosition(newCoordinate, direction);
	}

	public ExplorerPosition withDirection(HeadingDirectionClockWise newDirection) {
		return new ExplorerPosition(coordinate, newDirection);
	}

	public ExplorerPosition turnLeft() {
		return new ExplorerPosition(coordinate, direction.turnLeft());
	}

	public ExplorerPosition turnRight() {
		return new ExplorerPosition(coordinate, direction.turnRight());
	}

	public ExplorerPosition calculateForwardPositionInMaze(MazeResponseService maze) {
		switch (direction) {
		case UP:
			if (coordinate.getY() == 0)
				throw new FieldIsOutOfMazeBoundsException();
			return withCoordinate(coordinate.above());
		case DOWN:
			if (coordinate.getY() == maze.getDimensionY() - 1)
				throw new FieldIsOutOfMazeBoundsException();
			return withCoordinate(coordinate.below());
		case LEFT:
			if (coordinate.getX() == 0)
				throw new FieldIsOutOfMazeBoundsException();
			return withCoordinate(coordinate.toTheLeft());
		case RIGHT:
			if (coordinate.getX() == maze.getDimensionX() - 1)
				throw new FieldIsOutOfMazeBoundsException();
			return withCoordinate(coordinate.toTheRight());
		}
		throw new UnsupportedOperationException(String.format("Direction %s not supported!", direction));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ExplorerPosition that = (ExplorerPosition) o;

		if (!coordinate.equals(that.coordinate))
			return false;
		return direction == that.direction;
	}

	@Override
	public int hashCode() {
		int result = coordinate.hashCode();
		result = 31 * result + direction.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "ExplorerPosition{" + "coordinate=" + coordinate + ", direction=" + direction + '}';
	}
}
