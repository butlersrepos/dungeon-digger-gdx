package dungeondigger.g2d;

import java.util.HashMap;
import java.util.Map;

public class ActorAnimationSets {
	private static Map<String, ActorAnimationSet>	sets;

	static {
		sets = new HashMap<>();
	}

	public static ActorAnimationSet get( String name ) {
		return sets.get( name );
	}

	public static void add( ActorAnimationSet newAnimationSet ) {
		sets.put( newAnimationSet.getTitle(), newAnimationSet );
	}
}
