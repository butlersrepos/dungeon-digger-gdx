package dungeondigger.actors;

import lombok.Builder;
import dungeondigger.ai.Appetite;
import dungeondigger.ai.Intelligence;

/** Race as in a high fantasy setting where Race is a very real thing that defines identity. Let's not get political people! */
@Builder
public class Race {
	Appetite		appetite;
	Intelligence	intelligence;
}
