package dungeondigger.taxonomy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

public class FourDirectionTest {
	@Test
	public void testVector2Assumptions() throws Exception {
		assertEquals( 90f, new Vector2( 1, 0 ).angle( new Vector2( 0, 1 ) ), 0.5f );
		assertEquals( -90f, new Vector2( -1, 0 ).angle( new Vector2( 0, 1 ) ), 0.5f );
		assertEquals( 180f, new Vector2( 0, -1 ).angle( new Vector2( 0, 1 ) ), 0.5f );
	}

	@Test
	public void testValueOf() throws Exception {
		assertEquals( FourDirection.NORTH, FourDirection.valueOf( 0f ) );
		assertEquals( FourDirection.WEST, FourDirection.valueOf( -90f ) );
		assertEquals( FourDirection.EAST, FourDirection.valueOf( 90f ) );
		assertEquals( FourDirection.SOUTH, FourDirection.valueOf( 180f ) );
		assertEquals( FourDirection.NORTH, FourDirection.valueOf( 360f ) );
		assertEquals( FourDirection.NORTH, FourDirection.valueOf( 361f ) );
		assertEquals( FourDirection.NORTH, FourDirection.valueOf( -360f ) );
		assertEquals( FourDirection.NORTH, FourDirection.valueOf( 359f ) );
	}
}
