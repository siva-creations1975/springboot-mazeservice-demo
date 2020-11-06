package com.dwp.maze.model;

import org.junit.Test;

import com.dwp.maze.service.impl.MazeCoordinate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MazeCoordinateTest {

	@Test
	public void mazeCoordinateXShouldNotBeNegative() {
		assertThatThrownBy(() -> new MazeCoordinate(-1, 0)).isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("Coordinate should not be negative!");
	}

	@Test
	public void mazeCoordinateYShouldNotBeNegative() {
		assertThatThrownBy(() -> new MazeCoordinate(0, -1)).isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("Coordinate should not be negative!");
	}

	@Test
	public void validCoordinateOrigoShouldBeCreated() {
		MazeCoordinate coord = new MazeCoordinate(0, 0);
		assertThat(coord.getX(), is(0));
		assertThat(coord.getY(), is(0));
	}

	@Test
	public void validCoordinateShouldBeCreated() {
		MazeCoordinate coord = new MazeCoordinate(11, 12);
		assertThat(coord.getX(), is(11));
		assertThat(coord.getY(), is(12));
	}

	@Test
	public void toTheLeftShouldReturnCoordinateToTheLeft() {
		MazeCoordinate coord = new MazeCoordinate(1, 1);
		assertThat(coord.toTheLeft(), is(new MazeCoordinate(0, 1)));
	}

	@Test
	public void toTheRightShouldReturnCoordinateToTheRight() {
		MazeCoordinate coord = new MazeCoordinate(1, 1);
		assertThat(coord.toTheRight(), is(new MazeCoordinate(2, 1)));
	}

	@Test
	public void aboveShouldReturnCoordinateAbove() {
		MazeCoordinate coord = new MazeCoordinate(1, 1);
		assertThat(coord.above(), is(new MazeCoordinate(1, 0)));
	}

	@Test
	public void belowShouldReturnCoordinateBelow() {
		MazeCoordinate coord = new MazeCoordinate(1, 1);
		assertThat(coord.below(), is(new MazeCoordinate(1, 2)));
	}

	@Test
	public void withXShouldReturnCoordinateWithNewX() {
		MazeCoordinate coord = new MazeCoordinate(1, 1);
		assertThat(coord.withX(10), is(new MazeCoordinate(10, 1)));
	}

	@Test
	public void withYShouldReturnCoordinateWithNewY() {
		MazeCoordinate coord = new MazeCoordinate(1, 1);
		assertThat(coord.withY(10), is(new MazeCoordinate(1, 10)));
	}
}
