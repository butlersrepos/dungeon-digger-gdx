package dungeondigger.taxonomy;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dungeondigger.ai.Objective;

public class ObjectivesTest {

	@Test
	public void shouldReturnNullForNullObjectives() throws Exception {
		assertNull( Objectives.get( "fake objective name goes here!" ) );
	}

	@Test
	public void shouldReturnAnEntityObjectForNonNullObjective() throws Exception {
		assertNotNull( Objectives.get( "food" ) );
		assertTrue( Objectives.get( "food" ) instanceof Objective );
	}
}
