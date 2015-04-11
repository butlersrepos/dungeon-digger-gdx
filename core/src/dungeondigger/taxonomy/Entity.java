package dungeondigger.taxonomy;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Data
@Builder
public class Entity {
	private String		name;
	@Singular
	private List<Trait>	traits;

	public boolean isA( Trait trait ) {
		return traits.contains( trait );
	}

	public Entity copy() {
		List<Trait> newTraits = new ArrayList<Trait>( traits );
		return Entity.builder().name( getName() ).traits( newTraits ).build();
	}
}
