package com.dwp.maze.explorer;

import org.junit.Before;
import org.junit.Test;

import com.dwp.maze.constants.HeadingDirectionClockWise;
import com.dwp.maze.constants.MazeStructure;
import com.dwp.maze.exception.FieldIsOutOfMazeBoundsException;
import com.dwp.maze.exception.MovementBlockedException;
import com.dwp.maze.explorer.ExplorerPosition;
import com.dwp.maze.explorer.MazeExplorer;
import com.dwp.maze.service.impl.MazeCoordinate;
import com.dwp.maze.service.response.MazeResponseService;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MazeExplorerTest {

	private final int mazeDimension = 10;

	private final MazeCoordinate bottomRightCorner = new MazeCoordinate(mazeDimension - 1, mazeDimension - 1);
	private final MazeCoordinate topLeftCorner = new MazeCoordinate(0, 0);

	private final MazeResponseService mazeMock = mock(MazeResponseService.class);

	private final MazeCoordinate startLocation = new MazeCoordinate(1, 1);

	@Before
	public void Setup() {
		when(mazeMock.getStartLocation()).thenReturn(startLocation);
		when(mazeMock.getDimensionX()).thenReturn(mazeDimension);
		when(mazeMock.getDimensionY()).thenReturn(mazeDimension);
	}

	@Test
	public void shouldInitializeInStartLocationTest() {
		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);

		ExplorerPosition loc = explorer.getPosition();
		assertThat(loc.getDirection(), is(HeadingDirectionClockWise.UP));
		assertThat(loc.getCoordinate(), is(startLocation));
	}

	@Test
	public void shouldMoveForwardToUpIfFieldIsSpace() {
		shouldMoveUpWhenFieldIs(new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP), MazeStructure.SPACE);
	}

	@Test
	public void shouldMoveForwardToUpIfFieldIsExit() {
		shouldMoveUpWhenFieldIs(new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP), MazeStructure.EXIT);
	}

	@Test
	public void shouldMoveForwardToUpIfFieldIsStart() {
		shouldMoveUpWhenFieldIs(new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP), MazeStructure.START);
	}

	@Test
	public void shouldThrowExceptionWhenMoveForwardAndFieldIsWall() {
		when(mazeMock.whatsAt(startLocation.above())).thenReturn(MazeStructure.WALL);
		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);

		assertThatThrownBy(() -> explorer.moveForward()).isInstanceOf(MovementBlockedException.class)
				.hasMessageContaining("Movement to location MazeCoordinate{x=1, y=0} is blocked!");
	}

	@Test
	public void shouldThrowExceptionWhenMoveToUpAndFieldIsOutOfBounds() {
		when(mazeMock.getStartLocation()).thenReturn(topLeftCorner);
		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);

		assertThatThrownBy(() -> explorer.moveForward()).isInstanceOf(FieldIsOutOfMazeBoundsException.class)
				.hasMessageContaining("Field is out of the maze!");

	}

	@Test
	public void shouldThrowExceptionWhenMoveToLeftAndFieldIsOutOfBounds() {
		when(mazeMock.getStartLocation()).thenReturn(topLeftCorner);
		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.LEFT);

		assertThatThrownBy(() -> explorer.moveForward()).isInstanceOf(FieldIsOutOfMazeBoundsException.class)
				.hasMessageContaining("Field is out of the maze!");
	}

	@Test
	public void shouldThrowExceptionWhenMoveToDownAndFieldIsOutOfBounds() {
		when(mazeMock.getStartLocation()).thenReturn(bottomRightCorner);
		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.DOWN);

		assertThatThrownBy(() -> explorer.moveForward()).isInstanceOf(FieldIsOutOfMazeBoundsException.class)
				.hasMessageContaining("Field is out of the maze!");

	}

	@Test
	public void shouldThrowExceptionWhenMoveToRightAndFieldIsOutOfBounds() {
		when(mazeMock.getStartLocation()).thenReturn(bottomRightCorner);
		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.RIGHT);

		assertThatThrownBy(() -> explorer.moveForward()).isInstanceOf(FieldIsOutOfMazeBoundsException.class)
				.hasMessageContaining("Field is out of the maze!");
	}

	@Test
	public void shouldMoveToRightIfFieldIsSpace() {
		when(mazeMock.whatsAt(startLocation.toTheRight())).thenReturn(MazeStructure.SPACE);
		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.RIGHT);

		explorer.moveForward();

		ExplorerPosition loc = explorer.getPosition();
		assertThat(loc.getDirection(), is(HeadingDirectionClockWise.RIGHT));
		assertThat(loc.getCoordinate(), is(startLocation.toTheRight()));
	}

	@Test
	public void shouldMoveToLeftIfFieldIsSpace() {
		when(mazeMock.whatsAt(startLocation.toTheLeft())).thenReturn(MazeStructure.SPACE);
		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.LEFT);

		explorer.moveForward();

		ExplorerPosition loc = explorer.getPosition();
		assertThat(loc.getDirection(), is(HeadingDirectionClockWise.LEFT));
		assertThat(loc.getCoordinate(), is(startLocation.toTheLeft()));
	}

	@Test
	public void shouldMoveDownIfFieldIsSpace() {
		when(mazeMock.whatsAt(startLocation.below())).thenReturn(MazeStructure.SPACE);
		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.DOWN);

		explorer.moveForward();

		ExplorerPosition loc = explorer.getPosition();
		assertThat(loc.getDirection(), is(HeadingDirectionClockWise.DOWN));
		assertThat(loc.getCoordinate(), is(startLocation.below()));
	}

	@Test
	public void whatsInFrontShouldReturnWallIfAbove() {
		when(mazeMock.whatsAt(startLocation.above())).thenReturn(MazeStructure.WALL);
		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);

		assertThat(explorer.whatsInFront(), is(Optional.of(MazeStructure.WALL)));
	}

	@Test
	public void whatsInFrontShouldReturnWallIfLeft() {
		when(mazeMock.whatsAt(startLocation.toTheLeft())).thenReturn(MazeStructure.WALL);
		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.LEFT);

		assertThat(explorer.whatsInFront(), is(Optional.of(MazeStructure.WALL)));
	}

	@Test
	public void whatsInFrontShouldReturnWallIfRight() {
		when(mazeMock.whatsAt(startLocation.toTheRight())).thenReturn(MazeStructure.WALL);
		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.RIGHT);

		assertThat(explorer.whatsInFront(), is(Optional.of(MazeStructure.WALL)));
	}

	@Test
	public void whatsInFrontShouldReturnWallIfDown() {
		when(mazeMock.whatsAt(startLocation.below())).thenReturn(MazeStructure.WALL);
		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.DOWN);

		assertThat(explorer.whatsInFront(), is(Optional.of(MazeStructure.WALL)));
	}

	@Test
	public void whatsInFrontShouldReturnNoneIfOutOfBounds() {
		when(mazeMock.getStartLocation()).thenReturn(topLeftCorner);
		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);

		assertThat(explorer.whatsInFront(), is(Optional.empty()));
	}

	@Test
	public void whatsAtMyLocationReturnTheCurrentLocationType() {
		when(mazeMock.whatsAt(startLocation)).thenReturn(MazeStructure.EXIT);
		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);

		assertThat(explorer.whatsAtMyLocation(), is(MazeStructure.EXIT));
	}

	@Test
	public void getPossibleDirectionsShouldReturnAllTheDirectionsIfExplorerCanMoveThere() {
		when(mazeMock.whatsAt(startLocation.below())).thenReturn(MazeStructure.SPACE);
		when(mazeMock.whatsAt(startLocation.above())).thenReturn(MazeStructure.SPACE);
		when(mazeMock.whatsAt(startLocation.toTheLeft())).thenReturn(MazeStructure.SPACE);
		when(mazeMock.whatsAt(startLocation.toTheRight())).thenReturn(MazeStructure.SPACE);

		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);

		assertThat(explorer.getPossibleDirections(), is(Arrays.asList(HeadingDirectionClockWise.values())));
	}

	@Test
	public void getPossibleDirectionsShouldReturnNoTheDirectionsIfExplorerCannotMoveAnywhere() {
		when(mazeMock.whatsAt(startLocation.below())).thenReturn(MazeStructure.WALL);
		when(mazeMock.whatsAt(startLocation.above())).thenReturn(MazeStructure.WALL);
		when(mazeMock.whatsAt(startLocation.toTheLeft())).thenReturn(MazeStructure.WALL);
		when(mazeMock.whatsAt(startLocation.toTheRight())).thenReturn(MazeStructure.WALL);

		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);

		assertThat(explorer.getPossibleDirections(), is(Arrays.asList(new HeadingDirectionClockWise[] {})));
	}

	@Test
	public void movementShouldBeTracked() {
		when(mazeMock.whatsAt(startLocation.above())).thenReturn(MazeStructure.SPACE);
		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);
		explorer.moveForward();
		assertThat(explorer.getPosition(),
				is(new ExplorerPosition(new MazeCoordinate(1, 0), HeadingDirectionClockWise.UP)));
		explorer.turnLeft();
		when(mazeMock.whatsAt(new MazeCoordinate(1, 0).toTheLeft())).thenReturn(MazeStructure.SPACE);
		explorer.moveForward();
		assertThat(explorer.getPosition(),
				is(new ExplorerPosition(new MazeCoordinate(0, 0), HeadingDirectionClockWise.LEFT)));

		assertThat(explorer.getMovement(), is(Arrays.asList(new MazeCoordinate[] { new MazeCoordinate(1, 1),
				new MazeCoordinate(1, 0), new MazeCoordinate(0, 0) })));
	}

	@Test
	public void turnToShouldSetDirection() {
		MazeExplorer explorer = new MazeExplorer(mazeMock, HeadingDirectionClockWise.UP);
		assertThat(explorer.getPosition().getDirection(), is(HeadingDirectionClockWise.UP));

		explorer.turnTo(HeadingDirectionClockWise.DOWN);

		assertThat(explorer.getPosition().getDirection(), is(HeadingDirectionClockWise.DOWN));

	}

	private void shouldMoveUpWhenFieldIs(MazeExplorer explorer, MazeStructure field) {
		when(mazeMock.whatsAt(startLocation.above())).thenReturn(field);
		explorer.moveForward();

		ExplorerPosition loc = explorer.getPosition();
		assertThat(loc.getDirection(), is(HeadingDirectionClockWise.UP));
		assertThat(loc.getCoordinate(), is(startLocation.above()));
	}
}
