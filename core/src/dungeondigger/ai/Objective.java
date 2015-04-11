package dungeondigger.ai;

import java.util.List;
import java.util.Objects;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.experimental.Wither;

@Data
@Builder
public class Objective {
	@Wither
	public String				title;
	@Singular
	public List<Requirement>	requirements;
	public int					priorityLevel	= 1;

	public Objective copy() {
		ObjectiveBuilder b = Objective.builder()
				.title( title )
				.priorityLevel( priorityLevel );
		for( Requirement req : requirements ) {
			b.requirement( req.copy() );
		}
		return b.build();
	}

	@Override
	public boolean equals( Object o ) {
		if( this == o ) return true;
		if( !( o instanceof Objective ) ) return false;
		return getTitle().equalsIgnoreCase( ( ( Objective ) o ).getTitle() );
	}

	@Override
	public int hashCode() {
		return Objects.hash( title, requirements );
	}
}
