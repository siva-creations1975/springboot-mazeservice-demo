package com.dwp.maze.explorer;

import org.junit.Before;
import org.junit.Test;

import com.dwp.maze.constants.MazeStructure;
import com.dwp.maze.explorer.AutomaticMazeExplorer;
import com.dwp.maze.explorer.service.AutomaticExplorer;
import com.dwp.maze.service.impl.MazeCoordinate;
import com.dwp.maze.service.response.MazeResponseService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class AutomaticExplorerTest {
	private final int mazeDimension = 10;

	private final MazeResponseService mazeMock = mock(MazeResponseService.class, RETURNS_SMART_NULLS);

	private final MazeCoordinate startLocation = new MazeCoordinate(1, 1);

	@Before
	public void Setup() {
		when(mazeMock.getStartLocation()).thenReturn(startLocation);
		when(mazeMock.getDimensionX()).thenReturn(mazeDimension);
		when(mazeMock.getDimensionY()).thenReturn(mazeDimension);
	}

	@Test
	public void automaticExplorerShouldReturnEmptyIfThereIsNoExitAvailable() {
		AutomaticExplorer explorer = new AutomaticMazeExplorer(mazeMock);
		when(mazeMock.whatsAt(startLocation.above())).thenReturn(MazeStructure.WALL);
		when(mazeMock.whatsAt(startLocation.below())).thenReturn(MazeStructure.WALL);
		when(mazeMock.whatsAt(startLocation.toTheLeft())).thenReturn(MazeStructure.WALL);
		when(mazeMock.whatsAt(startLocation.toTheRight())).thenReturn(MazeStructure.WALL);

		Optional<List<MazeCoordinate>> movement = explorer.searchWayOut();

		assertThat(movement, is(Optional.empty()));
	}

	@Test
	public void automaticExplorerShouldReturnPathIfExitFound() {
		AutomaticExplorer explorer = new AutomaticMazeExplorer(mazeMock);
		when(mazeMock.whatsAt(startLocation.above())).thenReturn(MazeStructure.WALL);
		when(mazeMock.whatsAt(startLocation.below())).thenReturn(MazeStructure.EXIT);
		when(mazeMock.whatsAt(startLocation.toTheLeft())).thenReturn(MazeStructure.WALL);
		when(mazeMock.whatsAt(startLocation.toTheRight())).thenReturn(MazeStructure.WALL);

		Optional<List<MazeCoordinate>> movement = explorer.searchWayOut();

		assertThat(movement, is(Optional.of(Arrays.asList(startLocation, startLocation.below()))));
	}
}
