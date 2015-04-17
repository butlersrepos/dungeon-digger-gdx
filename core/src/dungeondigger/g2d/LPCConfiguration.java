package dungeondigger.g2d;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP;
import static dungeondigger.actors.ActionState.CASTING;
import static dungeondigger.actors.ActionState.HURTING;
import static dungeondigger.actors.ActionState.IDLING;
import static dungeondigger.actors.ActionState.SHOOTING;
import static dungeondigger.actors.ActionState.SLASHING;
import static dungeondigger.actors.ActionState.THRUSTING;
import static dungeondigger.actors.ActionState.WALKING;
import static dungeondigger.taxonomy.FourDirection.EAST;
import static dungeondigger.taxonomy.FourDirection.NORTH;
import static dungeondigger.taxonomy.FourDirection.SOUTH;
import static dungeondigger.taxonomy.FourDirection.WEST;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import dungeondigger.actors.ActionState;
import dungeondigger.taxonomy.FourDirection;

@Data
@Builder
public class LPCConfiguration {
	public static List<LPCConfiguration>	DEFAULTS;

	static {
		DEFAULTS = new ArrayList<>();
		float[] defaultFrames = new float[] {
			0.1f
		};
		DEFAULTS.add( builder().actionState( CASTING ).direction( NORTH ).rowNumber( 0 ).frameCount( 7 ).playMode( LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( CASTING ).direction( WEST ).rowNumber( 1 ).frameCount( 7 ).playMode( LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( CASTING ).direction( SOUTH ).rowNumber( 2 ).frameCount( 7 ).playMode( LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( CASTING ).direction( EAST ).rowNumber( 3 ).frameCount( 7 ).playMode( LOOP ).frameRates( defaultFrames ).build() );

		DEFAULTS.add( builder().actionState( THRUSTING ).direction( NORTH ).rowNumber( 4 ).frameCount( 8 ).playMode( LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( THRUSTING ).direction( WEST ).rowNumber( 5 ).frameCount( 8 ).playMode( LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( THRUSTING ).direction( SOUTH ).rowNumber( 6 ).frameCount( 8 ).playMode( LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( THRUSTING ).direction( EAST ).rowNumber( 7 ).frameCount( 8 ).playMode( LOOP ).frameRates( defaultFrames ).build() );

		DEFAULTS.add( builder().actionState( WALKING ).direction( NORTH ).rowNumber( 8 ).frameCount( 9 ).playMode( LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( WALKING ).direction( WEST ).rowNumber( 9 ).frameCount( 9 ).playMode( LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( WALKING ).direction( SOUTH ).rowNumber( 10 ).frameCount( 9 ).playMode( LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( WALKING ).direction( EAST ).rowNumber( 11 ).frameCount( 9 ).playMode( LOOP ).frameRates( defaultFrames ).build() );

		DEFAULTS.add( builder().actionState( SLASHING ).direction( NORTH ).rowNumber( 12 ).frameCount( 6 ).playMode( LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( SLASHING ).direction( WEST ).rowNumber( 13 ).frameCount( 6 ).playMode( LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( SLASHING ).direction( SOUTH ).rowNumber( 14 ).frameCount( 6 ).playMode( LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( SLASHING ).direction( EAST ).rowNumber( 15 ).frameCount( 6 ).playMode( LOOP ).frameRates( defaultFrames ).build() );

		DEFAULTS.add( builder().actionState( SHOOTING ).direction( NORTH ).rowNumber( 16 ).frameCount( 13 ).playMode( LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( SHOOTING ).direction( WEST ).rowNumber( 17 ).frameCount( 13 ).playMode( LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( SHOOTING ).direction( SOUTH ).rowNumber( 18 ).frameCount( 13 ).playMode( LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( SHOOTING ).direction( EAST ).rowNumber( 19 ).frameCount( 13 ).playMode( LOOP ).frameRates( defaultFrames ).build() );

		DEFAULTS.add( builder().actionState( HURTING ).direction( SOUTH ).rowNumber( 20 ).frameCount( 6 ).playMode( LOOP ).frameRates( defaultFrames ).build() );

		float[] idleFrames = {
			4f, 2f
		};
		DEFAULTS.add( builder().actionState( IDLING ).direction( NORTH ).rowNumber( 0 ).frameCount( 2 ).playMode( LOOP ).frameRates( idleFrames ).build() );
		DEFAULTS.add( builder().actionState( IDLING ).direction( WEST ).rowNumber( 1 ).frameCount( 2 ).playMode( LOOP ).frameRates( idleFrames ).build() );
		DEFAULTS.add( builder().actionState( IDLING ).direction( SOUTH ).rowNumber( 2 ).frameCount( 2 ).playMode( LOOP ).frameRates( idleFrames ).build() );
		DEFAULTS.add( builder().actionState( IDLING ).direction( EAST ).rowNumber( 3 ).frameCount( 2 ).playMode( LOOP ).frameRates( idleFrames ).build() );

	}

	private ActionState						actionState;
	private FourDirection					direction;
	private int								rowNumber;
	private int								frameCount;
	private PlayMode						playMode;
	private float[]							frameRates;
}
