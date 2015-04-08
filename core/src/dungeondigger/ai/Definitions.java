package dungeondigger.ai;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Definitions {
	public static HashMap<String, List<String>>	ENTITY_CATALOG	= new HashMap<String, List<String>>();

	static {
		ENTITY_CATALOG.put( "apple", Arrays.asList( "food" ) );
	}
}
