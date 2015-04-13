package dungeondigger.taxonomy;

public enum Direction {
	NONE, NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST;

	public static Direction valueOf( float angle ) {
		Direction[] directions = {
				NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST, NORTH
		};
		int result = ( int ) Math.abs( Math.round( ( ( ( double ) angle % 360 ) / 45 ) ) );
		return directions[result];
	}
}
