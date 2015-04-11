package dungeondigger.taxonomy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class EntityTest {

	private Entity	subject;

	@Before
	public void setUp() throws Exception {
		subject = Entity.builder().name( "apple" ).trait( Aspects.FOOD ).build();
	}

	@Test
	public void shouldBeAFoodIfSetupAsAFood() throws Exception {
		assertTrue( subject.isA( Aspects.FOOD ) );
	}

	@Test
	public void shouldNotBeAWeaponIfNotSetupAsAWeapon() throws Exception {
		assertFalse( subject.isA( Aspects.WEAPON ) );
	}

	@Test
	public void copyTest() throws Exception {
		try {
			Entity subCopy = subject.copy();
		} catch( Exception e ) {
			fail();
		}
	}
}
