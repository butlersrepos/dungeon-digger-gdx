package dungeondigger.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import lombok.Builder;

import com.badlogic.gdx.math.Vector2;

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
				System.out.println( "Already have objective: " + o.title );
				return;
			}
		}
		goals.add( o );
		System.out.println( "I has new objective! " + o.title );
		Collections.sort( goals, new Comparator<Objective>() {
			@Override
			public int compare( Objective o1, Objective o2 ) {
				return ( int ) Math.signum( o1.priorityLevel - o2.priorityLevel );
			}
		} );
	}

	public Vector2 computeEasiestGoal( ArrayList<Vector2> locs ) {
		// Distance

		// Difficulty of action

		return null;
	}
}
