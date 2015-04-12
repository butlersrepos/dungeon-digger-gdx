package dungeondigger.player;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PlayerStateTest {
	PlayerState	subject	= new PlayerState();

	@Test
	public void highestBoundTest() throws Exception {
		float result = subject.highestBounded( 1f, 4f, 3f );
		assertTrue( 3f == result );

		result = subject.highestBounded( 10f, -5f, 11f );
		assertTrue( 10f == result );

		result = subject.highestBounded( -3f, -5f, 11f );
		assertTrue( -3f == result );

		result = subject.highestBounded( 3f, -5f, 0f );
		assertTrue( 0f == result );
	}

	@Test
	public void lowestBoundTest() throws Exception {
		float result = subject.lowestBounded( 1f, 4f, -5f );
		assertTrue( 1f == result );

		result = subject.lowestBounded( 10f, -5f, 11f );
		assertTrue( 11f == result );

		result = subject.lowestBounded( -3f, -5f, 11f );
		assertTrue( 11f == result );

		result = subject.lowestBounded( 3f, -5f, 0f );
		assertTrue( 0f == result );
	}

}
