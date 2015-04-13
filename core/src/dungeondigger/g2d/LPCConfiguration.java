package dungeondigger.g2d;

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
		DEFAULTS.add( builder().actionState( ActionState.CASTING ).direction( FourDirection.NORTH ).rowNumber( 0 ).frameCount( 7 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( ActionState.CASTING ).direction( FourDirection.WEST ).rowNumber( 1 ).frameCount( 7 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( ActionState.CASTING ).direction( FourDirection.SOUTH ).rowNumber( 2 ).frameCount( 7 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( ActionState.CASTING ).direction( FourDirection.EAST ).rowNumber( 3 ).frameCount( 7 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );

		DEFAULTS.add( builder().actionState( ActionState.THRUSTING ).direction( FourDirection.NORTH ).rowNumber( 4 ).frameCount( 8 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( ActionState.THRUSTING ).direction( FourDirection.WEST ).rowNumber( 5 ).frameCount( 8 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( ActionState.THRUSTING ).direction( FourDirection.SOUTH ).rowNumber( 6 ).frameCount( 8 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( ActionState.THRUSTING ).direction( FourDirection.EAST ).rowNumber( 7 ).frameCount( 8 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );

		DEFAULTS.add( builder().actionState( ActionState.WALKING ).direction( FourDirection.NORTH ).rowNumber( 8 ).frameCount( 9 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( ActionState.WALKING ).direction( FourDirection.WEST ).rowNumber( 9 ).frameCount( 9 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( ActionState.WALKING ).direction( FourDirection.SOUTH ).rowNumber( 10 ).frameCount( 9 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( ActionState.WALKING ).direction( FourDirection.EAST ).rowNumber( 11 ).frameCount( 9 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );

		DEFAULTS.add( builder().actionState( ActionState.SLASHING ).direction( FourDirection.NORTH ).rowNumber( 12 ).frameCount( 6 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( ActionState.SLASHING ).direction( FourDirection.WEST ).rowNumber( 13 ).frameCount( 6 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( ActionState.SLASHING ).direction( FourDirection.SOUTH ).rowNumber( 14 ).frameCount( 6 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( ActionState.SLASHING ).direction( FourDirection.EAST ).rowNumber( 15 ).frameCount( 6 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );

		DEFAULTS.add( builder().actionState( ActionState.SHOOTING ).direction( FourDirection.NORTH ).rowNumber( 16 ).frameCount( 13 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( ActionState.SHOOTING ).direction( FourDirection.WEST ).rowNumber( 17 ).frameCount( 13 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( ActionState.SHOOTING ).direction( FourDirection.SOUTH ).rowNumber( 18 ).frameCount( 13 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );
		DEFAULTS.add( builder().actionState( ActionState.SHOOTING ).direction( FourDirection.EAST ).rowNumber( 19 ).frameCount( 13 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );

		DEFAULTS.add( builder().actionState( ActionState.HURTING ).direction( FourDirection.SOUTH ).rowNumber( 20 ).frameCount( 6 ).playMode( PlayMode.LOOP ).frameRates( defaultFrames ).build() );

	}

	private ActionState						actionState;
	private FourDirection					direction;
	private int								rowNumber;
	private int								frameCount;
	private PlayMode						playMode;
	private float[]							frameRates;
}
