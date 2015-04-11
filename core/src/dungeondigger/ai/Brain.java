package dungeondigger.ai;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import com.badlogic.gdx.math.Vector2;

import dungeondigger.taxonomy.Entity;

@Slf4j
@Data
@Builder
public class Brain {
	private List<Observation>								observations		= new ArrayList<>();
	private List<Objective>									goals				= new ArrayList<>();
	private final static Comparator<? super Observation>	entityIdSort		= ( one, two ) -> ( int ) ( one.getEntity().getId() - two.getEntity().getId() );
	private final static Comparator<? super Observation>	observationTimeSort	= ( one, two ) -> ( int ) ( two.getTime() - one.getTime() );

	public void observe( Entity e, Vector2 loc ) {
		long time = Instant.now().toEpochMilli();
		Observation newest = Observation.builder().entity( e.copy() ).location( loc.cpy() ).time( time ).build();
		observations.add( newest );
		curateObservations();
	}

	protected void curateObservations() {
		observations = observations.stream()
				.sorted( entityIdSort )
				.sorted( observationTimeSort )
				.distinct()
				.collect( Collectors.toList() );
	}

	public void addObjective( Objective o ) {
		for( Objective oldObjs : goals ) {
			if( oldObjs.title.equalsIgnoreCase( o.title ) ) {
				log.info( "Already have objective: {}", o.title );
				return;
			}
		}
		goals.add( o );
		log.info( "I has new objective! {}", o.title );
		Collections.sort( goals, new Comparator<Objective>() {
			@Override
			public int compare( Objective o1, Objective o2 ) {
				return ( int ) Math.signum( o1.priorityLevel - o2.priorityLevel );
			}
		} );
	}

	public Optional<Observation> computeEasiestGoal( Vector2 startLoc, List<Observation> destLocs ) {
		BinaryOperator<Observation> closestObservationReducer = ( a, b ) -> startLoc.dst2( a.getLocation() ) < startLoc.dst2( b.getLocation() ) ? a : b;
		return destLocs.stream().reduce( closestObservationReducer );
	}
}
