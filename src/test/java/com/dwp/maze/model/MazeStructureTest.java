package com.dwp.maze.model;

import org.junit.Test;

import com.dwp.maze.constants.MazeStructure;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MazeStructureTest {
	@Test
	public void mazeStructureCharXShouldBeRecognizesAsWall() {
		assertThat(MazeStructure.from('X'), is(MazeStructure.WALL));
	}

	@Test
	public void mazeStructureCharSShouldBeRecognizesAsStartingPoint() {
		assertThat(MazeStructure.from('S'), is(MazeStructure.START));
	}

	@Test
	public void mazeStructureCharFShouldBeRecognizesAsExitPoint() {
		assertThat(MazeStructure.from('F'), is(MazeStructure.EXIT));
	}

	@Test
	public void mazeStructureCharSpaceShouldBeRecognizesAsSpace() {
		assertThat(MazeStructure.from(' '), is(MazeStructure.SPACE));
	}

	@Test
	public void unknownStructureCharShouldThrowException() {
		assertThatThrownBy(() -> MazeStructure.from('?')).isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("Maze structure not recognised from '?'!");
	}

}
