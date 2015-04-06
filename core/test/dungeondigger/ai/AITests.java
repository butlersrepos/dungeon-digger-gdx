package dungeondigger.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

public class AITests {

	HashMap<String, List<String>>	propertiesCatalog	= new HashMap<String, List<String>>();

	class Objective {
		String				title;
		ArrayList<String>	requirements;
		int					priorityLevel	= 1;
	}

	class Entity {
		Vector<String>	categories	= new Vector<String>();
	}

	class Agent {
		HashMap<String, List<Vector2>>	observedEntities	= new HashMap<String, List<Vector2>>();
		ArrayList<Objective>			goals				= new ArrayList<Objective>();
		Vector2							location			= new Vector2();
		boolean							isDead				= false;

		public void update( float dt ) {
			think( dt );
		}

		public void think( float dt ) {}

		public void observe( String e, Vector2 loc ) {
			if( observedEntities.get( e ) == null ) {
				observedEntities.put( e, new ArrayList<Vector2>() );
			}
			List<Vector2> locs = observedEntities.get( e );
			locs.add( loc.cpy() );
		}

		public void die() {
			System.out.println( "Agent died!" );
			isDead = true;
		}
	}

	class Orc extends Agent {
		int		hunger					= 1000;
		int		hungryAt				= 600;
		int		hungerDecrementPerTick	= 100;

		float	hungryTimer				= 1000;
		float	hungerTickCounter		= 0;

		int		thinkCrement			= 1000;
		float	thinkCounter			= 0;

		public Orc() {
			location.x = 10;
			location.y = 10;
		}

		@Override
		public void update( float dt ) {
			if( isDead ) { return; }
			hungerTickCounter += dt;
			thinkCounter += dt;

			if( thinkCounter >= thinkCrement ) {
				thinkCounter = 0;
				think( dt );
			}
			if( hungerTickCounter >= hungryTimer ) {
				hungerTickCounter = 0;
				hunger( hunger - hungerDecrementPerTick );
			}
		}

		@Override
		public void think( float dt ) {
			System.out.println( "Orc thinking..." );
			// check for objectives
			for( Objective objective : goals ) {
				System.out.println( "Orc try to solve: " + objective.title );
				// see if doable
				for( String requirement : objective.requirements ) {
					System.out.println( "\tfor " + objective.title + " orc need " + requirement );
					for( Map.Entry<String, List<Vector2>> seen : observedEntities.entrySet() ) {
						System.out.println( "\tOrc has seen a " + seen.getKey() );
						if( propertiesCatalog.get( seen.getKey() ).contains( requirement ) ) {
							String locs = "";
							for( Vector2 v : seen.getValue() ) {
								locs += "(" + v.x + ", " + v.y + ") and ";
							}
							System.out.println( "\t\t" + seen.getKey() + " is a " + requirement + "! I saw one at " + locs.substring( 0, locs.length() - 4 ) );
						} else {
							System.out.println( "\t\t" + seen.getKey() + " is NOT a " + requirement );
						}
					}
				}
			}
			// evaluate action choice
		}

		public void hunger( int newHunger ) {
			hunger = newHunger;
			if( hunger < 0 ) {
				die();
			}
			if( hunger <= hungryAt ) {
				Objective food = new Objective();
				food.title = "FOOD!";
				food.requirements = new ArrayList<String>( Arrays.asList( "food" ) );
				food.priorityLevel = ( int ) Math.ceil( hunger / 100 );
				addObjective( food );
			}
		}

		@Override
		public void die() {
			System.out.println( "Orc died!" );
			isDead = true;
		}

		public void addObjective( Objective o ) {
			for( Objective oldObjs : goals ) {
				if( oldObjs.title.equalsIgnoreCase( o.title ) ) {
					System.out.println( "Already have objective: " + o.title );
					return;
				}
			}
			goals.add( o );
			System.out.println( "Orc has new objective! " + o.title );
			Collections.sort( goals, new Comparator<Objective>() {
				@Override
				public int compare( Objective o1, Objective o2 ) {
					return ( int ) Math.signum( o1.priorityLevel - o2.priorityLevel );
				}
			} );
		}
	}

	@Before
	public void setupCatalog() {
		propertiesCatalog.put( "apple", Arrays.asList( "food" ) );
	}

	@Test
	public void testAIfor10Seconds() {
		Entity apple = new Entity();
		apple.categories.add( "food" );

		Orc orc1 = new Orc();
		orc1.observe( "apple", Vector2.Zero );
		orc1.observe( "apple", new Vector2( 21, 21 ) );

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
		Orc orc1 = new Orc();

		Objective o1 = new Objective();
		o1.title = "food2";
		o1.requirements = new ArrayList<String>( Arrays.asList( "food" ) );
		o1.priorityLevel = 2;
		orc1.addObjective( o1 );

		Objective o2 = new Objective();
		o2.title = "food1";
		o2.requirements = new ArrayList<String>( Arrays.asList( "food" ) );
		o2.priorityLevel = ( int ) Math.ceil( 1 );
		orc1.addObjective( o2 );

		Objective o3 = new Objective();
		o3.title = "food5";
		o3.requirements = new ArrayList<String>( Arrays.asList( "food" ) );
		o3.priorityLevel = ( int ) Math.ceil( 5 );
		orc1.addObjective( o3 );

		Assert.assertEquals( 1, orc1.goals.get( 0 ).priorityLevel );
		Assert.assertEquals( 2, orc1.goals.get( 1 ).priorityLevel );
		Assert.assertEquals( 5, orc1.goals.get( 2 ).priorityLevel );
	}
}
