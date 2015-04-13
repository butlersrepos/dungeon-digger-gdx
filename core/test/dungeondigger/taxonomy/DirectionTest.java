package dungeondigger.taxonomy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DirectionTest {
	@Test
	public void valueOfAngleTest() throws Exception {
		assertEquals( Direction.NORTH, Direction.valueOf( -22.4f ) );
		assertEquals( Direction.NORTHEAST, Direction.valueOf( 22.5f ) );
		assertEquals( Direction.EAST, Direction.valueOf( 67.5f ) );
		assertEquals( Direction.SOUTHEAST, Direction.valueOf( 112.5f ) );
		assertEquals( Direction.SOUTH, Direction.valueOf( 157.5f ) );
		assertEquals( Direction.NORTH, Direction.valueOf( 359f ) );
	}
}
