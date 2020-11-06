package com.dwp.maze.explorer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.dwp.maze.constants.HeadingDirectionClockWise;
import com.dwp.maze.constants.MazeStructure;
import com.dwp.maze.exception.FieldIsOutOfMazeBoundsException;
import com.dwp.maze.exception.MovementBlockedException;
import com.dwp.maze.explorer.service.Explorer;
import com.dwp.maze.service.impl.MazeCoordinate;
import com.dwp.maze.service.response.MazeResponseService;

/**
 * Thread safe implementation of a an explorer of the maze
 */
public class MazeExplorer implements Explorer {

	protected final MazeResponseService maze;
	private final List<MazeCoordinate> movement;
	private ExplorerPosition position;

	public MazeExplorer(MazeResponseService maze) {
		this(maze, HeadingDirectionClockWise.UP);
	}

	public MazeExplorer(MazeResponseService maze, HeadingDirectionClockWise startingDirection) {
		this.maze = maze;
		position = new ExplorerPosition(maze.getStartLocation(), startingDirection);
		this.movement = new ArrayList<>();
		this.movement.add(maze.getStartLocation());
	}

	protected void movingToHook(MazeCoordinate mazeCoordinate) {
		// do nothing here, provide it for extendability
	}

	@Override
	public synchronized final void moveForward() {
		ExplorerPosition newPosition = position.calculateForwardPositionInMaze(maze);
		if (maze.whatsAt(newPosition.getCoordinate()).canBeExplored()) {
			movingToHook(newPosition.getCoordinate());
			this.movement.add(newPosition.getCoordinate());
			this.position = newPosition;
		} else {
			throw new MovementBlockedException(newPosition.getCoordinate());
		}
	}

	@Override
	public synchronized final void turnLeft() {
		position = position.turnLeft();
	}

	@Override
	public synchronized final void turnRight() {
		position = position.turnRight();
	}

	@Override
	public synchronized final void turnTo(HeadingDirectionClockWise direction) {
		position = position.withDirection(direction);
	}

	@Override
	public synchronized final List<HeadingDirectionClockWise> getPossibleDirections() {
		return Arrays.stream(HeadingDirectionClockWise.values()).filter(canBeExplored()).collect(Collectors.toList());
	}

	@Override
	public synchronized final Optional<MazeStructure> whatsInFront() {
		return whatsInDirection(position.getDirection());
	}

	@Override
	public synchronized final MazeStructure whatsAtMyLocation() {
		return maze.whatsAt(position.getCoordinate());
	}

	@Override
	public synchronized List<MazeCoordinate> getMovement() {
		return new ArrayList<>(movement);
	}

	@Override
	public synchronized ExplorerPosition getPosition() {
		return position;
	}

	private Predicate<HeadingDirectionClockWise> canBeExplored() {
		return d -> {
			Optional<MazeStructure> ms = whatsInDirection(d);
			return ms.isPresent() && ms.get().canBeExplored();
		};
	}

	private Optional<MazeStructure> whatsInDirection(HeadingDirectionClockWise direction) {
		try {
			ExplorerPosition newPosition = position.withDirection(direction).calculateForwardPositionInMaze(maze);
			return Optional.of(maze.whatsAt(newPosition.getCoordinate()));
		} catch (FieldIsOutOfMazeBoundsException ex) {
			return Optional.empty();
		}
	}
}
