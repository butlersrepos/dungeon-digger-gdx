package dungeondigger.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import dungeondigger.actors.Agent;
import dungeondigger.actors.Race;

public class AITests {

	class Entity {
		Vector<String>	categories	= new Vector<String>();
	}

	@Test
	public void testAIfor10Seconds() {
		Entity apple = new Entity();
		apple.categories.add( "food" );

		Agent orc1 = Agent.builder()
				.brain( Brain.builder().goals( new ArrayList<Objective>() )
						.observedEntities( new HashMap<String, List<Vector2>>() ).build() )
				.race( Race.builder().appetite( new Appetite() )
						.intelligence( new Intelligence() ).build() )
				.location( new Vector2( 10, 10 ) )
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

	@Test
	public void addingObjectivesShouldBeSorted() {
		Agent orc1 = Agent.builder()
				.brain( Brain.builder().goals( new ArrayList<Objective>() )
						.observedEntities( new HashMap<String, List<Vector2>>() ).build() )
				.race( Race.builder().appetite( new Appetite() )
						.intelligence( new Intelligence() ).build() )
				.location( new Vector2( 10, 10 ) )
				.isDead( false )
				.build();

		Objective o1 = new Objective();
		o1.title = "food2";
		o1.requirements = new ArrayList<String>( Arrays.asList( "food" ) );
		o1.priorityLevel = 2;
		orc1.brain.addObjective( o1 );

		Objective o2 = new Objective();
		o2.title = "food1";
		o2.requirements = new ArrayList<String>( Arrays.asList( "food" ) );
		o2.priorityLevel = ( int ) Math.ceil( 1 );
		orc1.brain.addObjective( o2 );

		Objective o3 = new Objective();
		o3.title = "food5";
		o3.requirements = new ArrayList<String>( Arrays.asList( "food" ) );
		o3.priorityLevel = ( int ) Math.ceil( 5 );
		orc1.brain.addObjective( o3 );

		Assert.assertEquals( 1, orc1.brain.goals.get( 0 ).priorityLevel );
		Assert.assertEquals( 2, orc1.brain.goals.get( 1 ).priorityLevel );
		Assert.assertEquals( 5, orc1.brain.goals.get( 2 ).priorityLevel );
	}
}
