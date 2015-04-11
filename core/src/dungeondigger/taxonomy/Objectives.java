package dungeondigger.taxonomy;

import java.util.HashMap;
import java.util.Optional;

import dungeondigger.ai.Action;
import dungeondigger.ai.Objective;
import dungeondigger.ai.Requirement;

public class Objectives {
	private static HashMap<String, Objective>	OBJECTIVES	= new HashMap<>();

	static {

		Objective foo = Objective.builder()
				.title( "food" )
				.requirement( Requirement.builder()
						.action( Action.GET )
						.target( Aspects.FOOD )
						.build()
				).build();
		OBJECTIVES.put( "food", foo );
	}

	public static Objective get( String name ) {
		Optional<Objective> result = Optional.ofNullable( OBJECTIVES.get( name ) );
		return result.map( ( r ) -> r.copy() ).orElse( null );
	}
}
