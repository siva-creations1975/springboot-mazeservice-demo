package com.dwp.maze.constants;

public enum HeadingDirectionClockWise {
	UP, RIGHT, DOWN, LEFT;

	public HeadingDirectionClockWise turnRight() {
		if (ordinal() == values().length - 1)
			return values()[0];
		return values()[ordinal() + 1];
	}

	public HeadingDirectionClockWise turnLeft() {
		if (ordinal() == 0)
			return values()[values().length - 1];
		return values()[ordinal() - 1];
	}

	public HeadingDirectionClockWise opposite() {
		return turnLeft().turnLeft();
	}
}
