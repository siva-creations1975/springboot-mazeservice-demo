package com.dwp.maze.explorer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

import com.dwp.maze.constants.HeadingDirectionClockWise;
import com.dwp.maze.constants.MazeStructure;
import com.dwp.maze.explorer.service.AutomaticExplorer;
import com.dwp.maze.service.impl.MazeCoordinate;
import com.dwp.maze.service.response.MazeResponseService;

public class AutomaticMazeExplorer extends MazeExplorer implements AutomaticExplorer {

	public AutomaticMazeExplorer(MazeResponseService maze) {
		super(maze);
		movingToHook(maze.getStartLocation());
	}

	private final Stack<Breadcrumb> pathFollowed = new Stack<>();

	private final Set<MazeCoordinate> visitedCoordinates = new HashSet<>();

	@Override
	protected void movingToHook(MazeCoordinate coordinate) {
		// track visited coordinates in a HashSet for fast constant time lookup
		visitedCoordinates.add(coordinate);
	}

	private boolean findPathTillExit() {
		while (!pathFollowed.isEmpty()) {
			if (pathFollowed.peek().hasUnexploredDirection()) {
				HeadingDirectionClockWise direction = pathFollowed.peek().exploreFirstDirection();

				ExplorerPosition nextPosition = getPosition().withDirection(direction)
						.calculateForwardPositionInMaze(maze);

				if (!visitedCoordinates.contains(nextPosition.getCoordinate())) {
					moveTo(direction);
					if (whatsAtMyLocation() == MazeStructure.EXIT) {
						return true;
					}
					pathFollowed.add(new Breadcrumb(direction.opposite(), getPossibleDirections()));
				}
			} else {
				moveBackToFirstFieldWithAlternateRoute();
			}
		}
		return false;
	}

	private void moveBackToFirstFieldWithAlternateRoute() {
		Breadcrumb previousBreadcrumb;
		do {
			previousBreadcrumb = pathFollowed.pop();
			if (previousBreadcrumb.getArrivingFrom().isPresent()) {
				moveTo(previousBreadcrumb.getArrivingFrom().get());
			}
		} while (previousBreadcrumb.hasUnexploredDirection());
	}

	@Override
	public synchronized Optional<List<MazeCoordinate>> searchWayOut() {
		pathFollowed.add(new Breadcrumb(getPossibleDirections()));
		boolean exitReached = findPathTillExit();
		if (exitReached) {
			return Optional.of(getMovement());
		} else {
			return Optional.empty();
		}
	}
}

class Breadcrumb {
	private final Optional<HeadingDirectionClockWise> arrivingFrom;
	private final List<HeadingDirectionClockWise> unexploredDirections;

	public Breadcrumb(HeadingDirectionClockWise arrivingFrom, List<HeadingDirectionClockWise> unexploredDirections) {
		this.arrivingFrom = Optional.of(arrivingFrom);
		this.unexploredDirections = new ArrayList<>(unexploredDirections);
		this.unexploredDirections.remove(arrivingFrom);
	}

	public Breadcrumb(List<HeadingDirectionClockWise> unexploredDirections) {
		this.arrivingFrom = Optional.empty();
		this.unexploredDirections = new ArrayList<>(unexploredDirections);
		this.unexploredDirections.remove(arrivingFrom);
	}

	public Optional<HeadingDirectionClockWise> getArrivingFrom() {
		return arrivingFrom;
	}

	public boolean hasUnexploredDirection() {
		return !unexploredDirections.isEmpty();
	}

	public HeadingDirectionClockWise exploreFirstDirection() {
		return unexploredDirections.remove(0);
	}
}
