package dungeondigger.ai;

import lombok.Builder;
import lombok.Data;
import dungeondigger.taxonomy.Trait;

@Data
@Builder
public class Requirement {
	Action	action;
	Trait	target;

	public Requirement copy() {
		return Requirement.builder().action( action ).target( target ).build();
	}
}
