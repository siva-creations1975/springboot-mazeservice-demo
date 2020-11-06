package com.dwp.maze.service.impl;

import org.springframework.stereotype.Service;

import com.dwp.maze.constants.MazeStructure;
import com.dwp.maze.exception.MazeServiceException;
import com.dwp.maze.service.MazeService;
import com.dwp.maze.service.response.MazeResponseService;
import com.dwp.maze.service.response.MazeServiceResponse;
import com.google.common.base.Strings;

@Service
public final class MazeServiceImpl implements MazeService {

	private final String LINEBREAK = "\\r?\\n";

	public MazeResponseService processMazeService(String mazeStr) throws MazeServiceException {

		if (Strings.isNullOrEmpty(mazeStr)) {
			throw new MazeServiceException("Maze can not be empty!");
		} else if (!(countStringContainsOfGivenChar(mazeStr, MazeStructure.START.charRepresentation()) == 1)) {
			throw new MazeServiceException("Maze should have exactly one starting point!");
		} else if (!(countStringContainsOfGivenChar(mazeStr, MazeStructure.EXIT.charRepresentation()) == 1)) {
			throw new MazeServiceException("Maze should have exactly one exit point!");
		}

		String[] mazeData = mazeStr.split(LINEBREAK);

		if (!allRowsHasTheSameLength(mazeData)) {
			throw new MazeServiceException("Maze rows should consist of the same number of blocks!");
		}
		
		int dimensionX = mazeData[0].length();
		int dimensionY = mazeData.length;

		MazeCoordinate startLocation = findLocation(mazeStr, MazeStructure.START, dimensionX, dimensionY);
		MazeCoordinate exitLocation = findLocation(mazeStr, MazeStructure.EXIT, dimensionX, dimensionY);

		long numberOfWalls = countStringContainsOfGivenChar(mazeStr, MazeStructure.WALL.charRepresentation());
		long numberOfEmptySpaces = countStringContainsOfGivenChar(mazeStr, MazeStructure.SPACE.charRepresentation());

		MazeResponseService responseService = new MazeServiceResponse(mazeData, dimensionX, dimensionY, startLocation,
				exitLocation, numberOfWalls, numberOfEmptySpaces);

		return responseService;
	}

	private final long countStringContainsOfGivenChar(String str, char c) {
		return str.chars().filter(ch -> ch == c).count();
	}

	private final MazeCoordinate findLocation(String mazeStr, MazeStructure mazeStructure, int dimensionX,
			int dimensionY) throws MazeServiceException {
		int indexOfLocation = mazeStr.replaceAll(LINEBREAK, "").indexOf(mazeStructure.charRepresentation());
		return new MazeCoordinate(indexOfLocation % dimensionX, indexOfLocation / dimensionY);
	}

	private final boolean allRowsHasTheSameLength(String[] mazeData) {
		for (String mazeRow : mazeData) {
			if (mazeRow.length() != mazeData[0].length()) {
				return false;
			}
		}
		return true;
	}
}
