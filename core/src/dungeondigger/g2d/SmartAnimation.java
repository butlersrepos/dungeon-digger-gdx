package dungeondigger.g2d;

import java.util.Arrays;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class SmartAnimation extends Animation {

	private float[]	frameDurations;
	private boolean	smartMode	= false;
	private float	lastTime	= 0;

	/** Constructor, storing the frame duration and key frames.
	 * 
	 * @param frameDuration
	 *            the time between frames in seconds.
	 * @param keyFrames
	 *            the {@link TextureRegion}s representing the frames. */
	public SmartAnimation( float frameDuration, TextureRegion[] keyFrames ) {
		super( frameDuration, keyFrames );
	}

	/** Constructor, storing the frame duration and key frames.
	 * 
	 * @param frameDuration
	 *            the times between each frame in seconds.
	 * @param keyFrames
	 *            the {@link TextureRegion}s representing the frames. */
	public SmartAnimation( float[] frameDurations, TextureRegion[] keyFrames ) {
		super( 0f, keyFrames );

		if( frameDurations.length != keyFrames.length && frameDurations.length != 1 ) { throw new IllegalArgumentException( "Number of frame durations must equal number of keyframes!" ); }
		if( frameDurations.length == 1 ) {
			float[] tmp = frameDurations;
			frameDurations = new float[keyFrames.length];
			Arrays.fill( frameDurations, tmp[0] );
		}
		this.frameDurations = frameDurations;
		smartMode = true;
	}

	public void update( float dt ) {
		lastTime += dt;
	}

	/** Calculates the total one-time pass through time this animation would
	 * take.
	 * 
	 * @return */
	private float getTotalPlayTime() {
		float total = 0f;
		for( float individualFrameTimer : frameDurations ) {
			total += individualFrameTimer;
		}
		return total;
	}

	public TextureRegion getKeyFrame() {
		return super.getKeyFrame( lastTime );
	}

	@Override
	public boolean isAnimationFinished( float stateTime ) {
		// Loops are never finished
		if( Arrays.asList( PlayMode.LOOP, PlayMode.LOOP_PINGPONG, PlayMode.LOOP_RANDOM, PlayMode.LOOP_REVERSED ).contains( getPlayMode() ) ) { return false; }
		return stateTime > getTotalPlayTime();
	};

	@Override
	public int getKeyFrameIndex( float stateTime ) {
		if( !smartMode ) { return super.getKeyFrameIndex( stateTime ); }

		TextureRegion[] keyFrames = getKeyFrames();
		PlayMode playMode = getPlayMode();

		int frameNumber = 0;
		while( stateTime >= 0 ) {
			for( float time : frameDurations ) {
				stateTime -= time;
				frameNumber++;
				if( stateTime <= 0 ) {
					break;
				}
			}
		}

		switch( playMode ) {
			case NORMAL:
				frameNumber = Math.min( keyFrames.length - 1, frameNumber );
				break;
			case LOOP:
				frameNumber = frameNumber % keyFrames.length;
				break;
			case LOOP_PINGPONG:
				frameNumber = frameNumber % ( ( keyFrames.length * 2 ) - 2 );
				if( frameNumber >= keyFrames.length )
					frameNumber = keyFrames.length - 2 - ( frameNumber - keyFrames.length );
				break;
			case LOOP_RANDOM:
				frameNumber = MathUtils.random( keyFrames.length - 1 );
				break;
			case REVERSED:
				frameNumber = Math.max( keyFrames.length - frameNumber - 1, 0 );
				break;
			case LOOP_REVERSED:
				frameNumber = frameNumber % keyFrames.length;
				frameNumber = keyFrames.length - frameNumber - 1;
				break;
		}
		return frameNumber;
	}

	public float[] getFrameDurations() {
		return frameDurations;
	}
}
