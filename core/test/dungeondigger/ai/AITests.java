package dungeondigger.ai;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import dungeondigger.actors.Agent;
import dungeondigger.actors.Race;
import dungeondigger.taxonomy.Entities;
import dungeondigger.taxonomy.Entity;

public class AITests {
	@Test
	@Ignore
	public void testAIfor10Seconds() {
		Entity apple = Entities.get( "apple" );

		Agent orc1 = Agent.builder()
				.brain( Brain.builder().goals( new ArrayList<Objective>() )
						.observedEntities( new HashMap<String, List<Vector2>>() ).build() )
				.race( Race.builder().appetite( new Appetite() )
						.intelligence( new Intelligence() ).build() )
				.myLocation( new Vector2( 10, 10 ) )
				.isDead( false )
				.build();
		orc1.brain.observe( "apple", Vector2.Zero );
		orc1.brain.observe( "apple", new Vector2( 21, 21 ) );

		long startStamp = new Date().getTime();
		long lastTime = startStamp;
		long newTime = startStamp;

		while( newTime - startStamp < 10000 ) {
			newTime = new Date().getTime();
			orc1.update( newTime - lastTime );
			lastTime = newTime;
		}
	}
}
