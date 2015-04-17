package dungeondigger.g2d;

import java.util.HashMap;
import java.util.Optional;

import lombok.Data;
import dungeondigger.actors.ActionState;
import dungeondigger.taxonomy.FourDirection;

@Data
public class ActorAnimationSet {
	private String															title;
	private HashMap<ActionState, HashMap<FourDirection, SmartAnimation>>	directory	= new HashMap<ActionState, HashMap<FourDirection, SmartAnimation>>();

	public Optional<SmartAnimation> get( ActionState actionState, FourDirection direction ) {
		Optional<HashMap<FourDirection, SmartAnimation>> actionStateMap = Optional.ofNullable( directory.get( actionState ) );
		return actionStateMap.map( m -> m.get( direction ) );
	}
}
