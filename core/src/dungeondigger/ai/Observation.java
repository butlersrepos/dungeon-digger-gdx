package dungeondigger.ai;

import java.util.Objects;

import lombok.Builder;
import lombok.Data;

import com.badlogic.gdx.math.Vector2;

import dungeondigger.taxonomy.Entity;

@Data
@Builder
public class Observation {
	private Entity	entity;
	private Vector2	location;
	private long	time;

	@Override
	public boolean equals( Object o ) {
		if( this == o ) return true;
		if( !( o instanceof Observation ) ) return false;
		return ( ( Observation ) o ).getEntity().getId() == getEntity().getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash( getEntity().getName(), getEntity().getId() );
	}
}
