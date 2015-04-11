package dungeondigger.taxonomy;

import java.util.HashMap;
import java.util.Optional;

public class Entities {
	private static HashMap<String, Entity>	ENTITIES	= new HashMap<>();

	static {
		Entities.ENTITIES.put( "apple",
				Entity.builder()
						.name( "apple" )
						.trait( Aspects.FOOD ).build() );

	}

	public static Entity get( String name ) {
		Optional<Entity> result = Optional.ofNullable( Entities.ENTITIES.get( name ) );
		return result.map( ( r ) -> r.copy() ).orElse( null );
	}

}
