package dungeondigger.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.BinaryOperator;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import com.badlogic.gdx.math.Vector2;

@Slf4j
@Builder
public class Brain {
	public HashMap<String, List<Vector2>>	observedEntities	= new HashMap<String, List<Vector2>>();
	public ArrayList<Objective>				goals				= new ArrayList<Objective>();

	public void observe( String e, Vector2 loc ) {
		if( observedEntities.get( e ) == null ) {
			observedEntities.put( e, new ArrayList<Vector2>() );
		}
		List<Vector2> locs = observedEntities.get( e );
		locs.add( loc.cpy() );
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

	public Vector2 computeEasiestGoal( Vector2 start, ArrayList<Vector2> locs ) {
		return locs.stream().reduce( locs.get( 0 ), vector2DistanceReduce( start ) );
	}

	private BinaryOperator<Vector2> vector2DistanceReduce( Vector2 start ) {
		return new BinaryOperator<Vector2>() {
			@Override
			public Vector2 apply( Vector2 t, Vector2 u ) {
				return start.dst2( t ) < start.dst2( u ) ? t : u;
			}
		};
	}
}
