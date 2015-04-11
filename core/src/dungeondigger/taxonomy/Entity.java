package dungeondigger.taxonomy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors( chain = true )
public class Entity {
	private String		name;
	private long		id;
	@Singular
	private List<Trait>	traits;

	public boolean isA( Trait trait ) {
		return traits.contains( trait );
	}

	public Entity copy() {
		List<Trait> newTraits = new ArrayList<Trait>( traits );
		return Entity.builder().name( getName() ).id( getId() ).traits( newTraits ).build();
	}

	@Override
	public boolean equals( Object o ) {
		if( this == o ) return true;
		if( !( o instanceof Entity ) ) return false;
		return ( ( Entity ) o ).getId() == id;
	}

	@Override
	public int hashCode() {
		return Objects.hash( getName(), getId() );
	}
}
