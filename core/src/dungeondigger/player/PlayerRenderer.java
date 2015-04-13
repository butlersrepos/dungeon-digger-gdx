package dungeondigger.player;

import lombok.Data;

import com.badlogic.gdx.graphics.g2d.Batch;

import dungeondigger.g2d.ActorAnimationSet;
import dungeondigger.g2d.SmartAnimation;

@Data
public class PlayerRenderer {
	ActorAnimationSet	animationSet;
	PlayerState			playerState;

	public PlayerRenderer( PlayerState playerState ) {
		this.playerState = playerState;
	}

	public void update( float dt ) {
		SmartAnimation currentAnimation = animationSet.get( playerState.getActionState(), playerState.getDirection() );
		currentAnimation.update( dt );
	}

	public void draw( Batch batch ) {
		SmartAnimation currentAnimation = animationSet.get( playerState.getActionState(), playerState.getDirection() );
		try {
			batch.draw( currentAnimation.getKeyFrame(), playerState.xPos, playerState.yPos );
		} catch( Exception e ) {
			System.out.println();
		}
	}
}
