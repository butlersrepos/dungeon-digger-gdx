package dungeondigger.taxonomy;

public enum FourDirection {
	NONE, NORTH, EAST, SOUTH, WEST;

	public static FourDirection valueOf( float angle ) {
		FourDirection[] directions = {
				NORTH, EAST, SOUTH, WEST, NORTH
		};
		angle = angle < 0 ? angle + 360 : angle;
		int result = ( int ) Math.abs( Math.round( ( ( ( double ) angle % 360 ) / 90 ) ) );
		return directions[result];
	}
}
