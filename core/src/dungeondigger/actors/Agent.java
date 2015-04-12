package dungeondigger.actors;

import static java.lang.Math.signum;
import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import com.badlogic.gdx.math.Vector2;

import dungeondigger.ai.Brain;
import dungeondigger.ai.Objective;
import dungeondigger.ai.Objectives;
import dungeondigger.ai.Observation;
import dungeondigger.ai.Requirement;

@Slf4j
@Data
@Builder
public class Agent implements Updating {
	private Brain	brain;
	private Race	race;
	private Vector2	myLocation	= new Vector2();
	private boolean	isDead		= false;

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
		for( Objective objective : brain.getGoals() ) {
			log.info( "Agent try to solve: {}", objective.title );
			// see if doable
			for( Requirement req : objective.getRequirements() ) {
				log.info( "\tfor {} agent need {}", objective.title, req );

				Predicate<? super Observation> entityTraitMatchFilter = ( p ) -> p.getEntity().getTraits().contains( req.getTarget() );
				Comparator<? super Observation> observationDistanceSort = ( one, two ) -> ( int ) ( signum( one.getLocation().dst2( myLocation ) - two.getLocation().dst2( myLocation ) ) );

				List<Observation> useableTargets = brain.getObservations().stream()
						.filter( entityTraitMatchFilter )
						.collect( toList() );

				if( useableTargets.size() == 0 ) {
					log.info( "Agent has never seen anything to satisfy requirement:[{}] of objective:[{}]. Ignoring objective.", req, objective.title );
				}
				Optional<Observation> bestTarget = getBrain().computeEasiestGoal( myLocation, useableTargets );
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
			Objective food = Objectives.get( "food" );
			food.setPriorityLevel( ( int ) Math.ceil( race.appetite.hunger / 100 ) );
			brain.addObjective( food );
		}
	}

	public void die() {
		log.info( "Agent died!" );
		isDead = true;
	}
}
