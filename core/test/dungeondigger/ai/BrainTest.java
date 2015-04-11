package dungeondigger.ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import dungeondigger.taxonomy.Entity;

public class BrainTest {

	private Brain	subject;

	@Before
	public void setUp() throws Exception {
		subject = Brain.builder()
				.observations( new ArrayList<>() )
				.goals( new ArrayList<>() )
				.build();
	}

	@Test
	public void shouldChooseCorrectlyBetweenIdenticalSolutions() throws Exception {
		fail();
	}

	@Test
	public void shouldCurateObservations() throws Exception {
		Entity entity1 = Entity.builder().id( 1l ).name( "apple" ).build();
		Entity entity2 = Entity.builder().id( 2l ).name( "apple" ).build();

		subject.observe( entity1, new Vector2( 5, 5 ) );
		subject.observe( entity2, new Vector2( 15, 15 ) );

		assertEquals( 2, subject.getObservations().size() );

		Vector2 newLoc = new Vector2( 10, 10 );
		Thread.sleep( 10 );
		subject.observe( entity1, newLoc );

		assertEquals( 2, subject.getObservations().size() );

		Optional<Observation> obs = subject.getObservations().stream()
				.filter( ( o ) -> o.getEntity().equals( entity1 ) )
				.findFirst();

		assertEquals( newLoc, obs.get().getLocation() );
	}
}
