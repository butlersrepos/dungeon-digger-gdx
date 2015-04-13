package dungeondigger.g2d;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SmartAnimationTest {
	private SmartAnimation	subject;

	@Before
	public void setup() {
		subject = new SmartAnimation( new float[] {
				0.1f
		}, new TextureRegion[] {
				null, null, null
		} );
	}

	@Test
	public void shouldExtendSingletonFloatArrayToNumberOfKeyFrames() throws Exception {
		assertTrue( subject.getFrameDurations().length == 3 );
		assertTrue( subject.getFrameDurations()[0] == 0.1f );
		assertTrue( subject.getFrameDurations()[1] == 0.1f );
		assertTrue( subject.getFrameDurations()[2] == 0.1f );
	}
}
