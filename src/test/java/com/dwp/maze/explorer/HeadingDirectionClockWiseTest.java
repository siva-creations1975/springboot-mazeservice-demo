package com.dwp.maze.explorer;

import org.junit.Test;

import com.dwp.maze.constants.HeadingDirectionClockWise;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HeadingDirectionClockWiseTest {

	@Test
	public void turnLeftShouldReturnPreviousEnumElementGoingRound() {
		assertThat(HeadingDirectionClockWise.UP.turnLeft(), is(HeadingDirectionClockWise.LEFT));
		assertThat(HeadingDirectionClockWise.LEFT.turnLeft(), is(HeadingDirectionClockWise.DOWN));
		assertThat(HeadingDirectionClockWise.DOWN.turnLeft(), is(HeadingDirectionClockWise.RIGHT));
		assertThat(HeadingDirectionClockWise.RIGHT.turnLeft(), is(HeadingDirectionClockWise.UP));
	}

	@Test
	public void turnRightShouldReturnNextEnumElementGoingRound() {
		assertThat(HeadingDirectionClockWise.UP.turnRight(), is(HeadingDirectionClockWise.RIGHT));
		assertThat(HeadingDirectionClockWise.RIGHT.turnRight(), is(HeadingDirectionClockWise.DOWN));
		assertThat(HeadingDirectionClockWise.DOWN.turnRight(), is(HeadingDirectionClockWise.LEFT));
		assertThat(HeadingDirectionClockWise.LEFT.turnRight(), is(HeadingDirectionClockWise.UP));
	}

	@Test
	public void oppositeOfDirectionShouldBeCalculated() {
		assertThat(HeadingDirectionClockWise.UP.opposite(), is(HeadingDirectionClockWise.DOWN));
		assertThat(HeadingDirectionClockWise.DOWN.opposite(), is(HeadingDirectionClockWise.UP));
		assertThat(HeadingDirectionClockWise.LEFT.opposite(), is(HeadingDirectionClockWise.RIGHT));
		assertThat(HeadingDirectionClockWise.RIGHT.opposite(), is(HeadingDirectionClockWise.LEFT));
	}
}
