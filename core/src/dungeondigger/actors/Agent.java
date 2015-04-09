package dungeondigger.actors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import com.badlogic.gdx.math.Vector2;

import dungeondigger.ai.Brain;
import dungeondigger.ai.Definitions;
import dungeondigger.ai.Objective;

@Slf4j
@Builder
public class Agent implements Updating {
	public Brain	brain;
	Race			race;
	Vector2			myLocation	= new Vector2();
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
		log.info( "Agent thinking..." );
		// check for objectives
		for( Objective objective : brain.goals ) {
			log.info( "Agent try to solve: {}", objective.title );
			// see if doable
			for( String requirement : objective.requirements ) {
				log.info( "\tfor {} agent need {}", objective.title, requirement );

				ArrayList<Vector2> verifiedLocs = new ArrayList<Vector2>();
				for( Map.Entry<String, List<Vector2>> seen : brain.observedEntities.entrySet() ) {
					log.info( "\tAgent has seen a {}", seen.getKey() );
					if( Definitions.ENTITY_CATALOG.get( seen.getKey() ).contains( requirement ) ) {
						String locs = "";
						for( Vector2 v : seen.getValue() ) {
							verifiedLocs.add( v );
							locs += "(" + v.x + ", " + v.y + ") and ";
						}
						log.info( "\t\t{} is a {}! I saw one at {}", seen.getKey(), requirement, locs.substring( 0, locs.length() - 4 ) );

					} else {
						log.info( "\t\t{} is NOT a {}", seen.getKey(), requirement );
					}
				}

				if( verifiedLocs.size() == 0 ) {
					log.info( "Agent has never seen anything to satisfy requirement:[{}] of objective:[{}]. Ignoring objective.", requirement, objective.title );
				} else {
					Vector2 target = brain.computeEasiestGoal( myLocation, verifiedLocs );
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
		log.info( "Agent died!" );
		isDead = true;
	}
}
