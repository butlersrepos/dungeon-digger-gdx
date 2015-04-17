package dungeondigger.tools;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dungeondigger.tools.DDMathUtils;

public class DDMathUtilsTest {

	@Test
	public void highestBoundTest() throws Exception {
		float result = DDMathUtils.maxWithinBound( 1f, 4f, 3f );
		assertTrue( 3f == result );

		result = DDMathUtils.maxWithinBound( 10f, -5f, 11f );
		assertTrue( 10f == result );

		result = DDMathUtils.maxWithinBound( -3f, -5f, 11f );
		assertTrue( -3f == result );

		result = DDMathUtils.maxWithinBound( 3f, -5f, 0f );
		assertTrue( 0f == result );
	}

	@Test
	public void lowestBoundTest() throws Exception {
		float result = DDMathUtils.minWithinBound( 1f, 4f, -5f );
		assertTrue( 1f == result );

		result = DDMathUtils.minWithinBound( 10f, -5f, 11f );
		assertTrue( 11f == result );

		result = DDMathUtils.minWithinBound( -3f, -5f, 11f );
		assertTrue( 11f == result );

		result = DDMathUtils.minWithinBound( 3f, -5f, 0f );
		assertTrue( 0f == result );
	}

}
