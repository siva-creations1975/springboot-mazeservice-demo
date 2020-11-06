package com.dwp.maze.service.impl;

import com.google.common.base.Preconditions;

public final class MazeCoordinate {
	private final int x;

	private final int y;

	public MazeCoordinate(int x, int y) {
		Preconditions.checkArgument(x >= 0 && y >= 0, "Coordinate should not be negative!");

		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public MazeCoordinate withX(int newX) {
		return new MazeCoordinate(newX, y);
	}

	public MazeCoordinate withY(int newY) {
		return new MazeCoordinate(x, newY);
	}

	public MazeCoordinate toTheLeft() {
		return new MazeCoordinate(x - 1, y);
	}

	public MazeCoordinate toTheRight() {
		return new MazeCoordinate(x + 1, y);
	}

	public MazeCoordinate above() {
		return new MazeCoordinate(x, y - 1);
	}

	public MazeCoordinate below() {
		return new MazeCoordinate(x, y + 1);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		MazeCoordinate that = (MazeCoordinate) o;

		if (x != that.x)
			return false;
		return y == that.y;

	}

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		return result;
	}

	@Override
	public String toString() {
		return "MazeCoordinate{" + "x=" + x + ", y=" + y + '}';
	}
}
