package dungeondigger.g2d;

import java.util.HashMap;

import lombok.Data;
import dungeondigger.actors.ActionState;
import dungeondigger.taxonomy.FourDirection;

@Data
public class ActorAnimationSet {
	private String															title;
	private HashMap<ActionState, HashMap<FourDirection, SmartAnimation>>	directory	= new HashMap<ActionState, HashMap<FourDirection, SmartAnimation>>();

	public SmartAnimation get( ActionState actionState, FourDirection FourDirection ) {
		return directory.get( actionState ).get( FourDirection );
	}
}
