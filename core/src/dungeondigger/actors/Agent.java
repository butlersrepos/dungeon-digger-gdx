package dungeondigger.actors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.Builder;

import com.badlogic.gdx.math.Vector2;

import dungeondigger.ai.Brain;
import dungeondigger.ai.Definitions;
import dungeondigger.ai.Objective;

@Builder
public class Agent implements Updating {
	public Brain	brain;
	Race			race;
	Vector2			location	= new Vector2();
	boolean			isDead		= false;

	public void update( float dt ) {
		if( isDead ) { return; }
		race.appetite.hungerTickCounter += dt;
		race.intelligence.thinkCounter += dt;

		if( race.intelligence.thinkCounter >= race.intelligence.thinkCrement ) {
			race.intelligence.thinkCounter = 0;
			think( dt );
		}
		if( race.appetite.hungerTickCounter >= race.appetite.hungryTimer ) {
			race.appetite.hungerTickCounter = 0;
			hunger( race.appetite.hunger - race.appetite.hungerDecrementPerTick );
		}
	}

	public void think( float dt ) {
		System.out.println( "Orc thinking..." );
		// check for objectives
		for( Objective objective : brain.goals ) {
			System.out.println( "Orc try to solve: " + objective.title );
			// see if doable
			for( String requirement : objective.requirements ) {
				System.out.println( "\tfor " + objective.title + " orc need " + requirement );

				ArrayList<Vector2> verifiedLocs = new ArrayList<Vector2>();
				for( Map.Entry<String, List<Vector2>> seen : brain.observedEntities.entrySet() ) {
					System.out.println( "\tOrc has seen a " + seen.getKey() );
					if( Definitions.ENTITY_CATALOG.get( seen.getKey() ).contains( requirement ) ) {
						String locs = "";
						for( Vector2 v : seen.getValue() ) {
							verifiedLocs.add( v );
							locs += "(" + v.x + ", " + v.y + ") and ";
						}
						System.out.println( "\t\t" + seen.getKey() + " is a " + requirement + "! I saw one at " + locs.substring( 0, locs.length() - 4 ) );

					} else {
						System.out.println( "\t\t" + seen.getKey() + " is NOT a " + requirement );
					}
				}

				if( verifiedLocs.size() == 0 ) {
					System.out.println( "Orc has never seen anything to satisfy requirement:[" + requirement + "] of objective:[" + objective.title + "]. Ignoring objective." );
				} else {
					Vector2 target = brain.computeEasiestGoal( verifiedLocs );
				}
			}
		}
		// evaluate action choice
	}

	public void hunger( int newHunger ) {
		race.appetite.hunger = newHunger;
		if( race.appetite.hunger < 0 ) {
			die();
		}
		if( race.appetite.hunger <= race.appetite.hungryAt ) {
			Objective food = new Objective();
			food.title = "FOOD!";
			food.requirements = new ArrayList<String>( Arrays.asList( "food" ) );
			food.priorityLevel = ( int ) Math.ceil( race.appetite.hunger / 100 );
			brain.addObjective( food );
		}
	}

	public void die() {
		System.out.println( "Agent died!" );
		isDead = true;
	}
}
