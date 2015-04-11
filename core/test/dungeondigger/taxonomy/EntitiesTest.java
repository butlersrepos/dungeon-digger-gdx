package dungeondigger.taxonomy;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EntitiesTest {
	@Test
	public void shouldReturnNullForNullEntries() throws Exception {
		assertNull( Entities.get( "fake entity name goes here!" ) );
	}

	@Test
	public void shouldReturnAnEntityObjectForNonNullEntries() throws Exception {
		assertNotNull( Entities.get( "apple" ) );
		assertTrue( Entities.get( "apple" ) instanceof Entity );
	}
}
