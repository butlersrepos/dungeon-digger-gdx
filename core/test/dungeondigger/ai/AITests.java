package dungeondigger.ai;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import dungeondigger.actors.Agent;
import dungeondigger.actors.Race;
import dungeondigger.taxonomy.Entities;
import dungeondigger.taxonomy.Entity;

public class AITests {
	private Agent	subject;

	@Before
	public void setup() {
		subject = Agent.builder()
				.brain( Brain.builder().goals( new ArrayList<>() )
						.observations( new ArrayList<>() ).build() )
				.race( Race.builder().appetite( new Appetite() )
						.intelligence( new Intelligence() ).build() )
				.myLocation( new Vector2( 10, 10 ) )
				.isDead( false )
				.build();
	}

	@Test
	public void testAIfor10Seconds() {
		Entity apple1 = Entities.get( "apple" ).setId( 100l );
		subject.getBrain().observe( apple1, Vector2.Zero );

		Entity apple2 = apple1.copy().setId( 200l );
		subject.getBrain().observe( apple2, new Vector2( 21, 21 ) );

		long startStamp = new Date().getTime();
		long lastTime = startStamp;
		long newTime = startStamp;

		while( newTime - startStamp < 10000 ) {
			newTime = new Date().getTime();
			subject.update( newTime - lastTime );
			lastTime = newTime;
		}
	}
}
