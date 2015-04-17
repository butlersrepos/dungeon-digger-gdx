package dungeondigger.player;

import static dungeondigger.Assets.manager;

import java.util.Optional;

import lombok.Data;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import dungeondigger.g2d.ActorAnimationSet;
import dungeondigger.g2d.SmartAnimation;

@Data
public class PlayerRenderer {
	private ActorAnimationSet			animationSet;
	private PlayerState					playerState;
	private Optional<SmartAnimation>	currentAnimation;

	public PlayerRenderer( PlayerState playerState ) {
		this.playerState = playerState;
	}

	public void update( float dt ) {
		currentAnimation = animationSet.get( playerState.getActionState(), playerState.getDirection() );
		currentAnimation.ifPresent( a -> a.update( dt ) );
	}

	public void draw( Batch batch ) {
		batch.draw( currentAnimation.map( a -> a.getKeyFrame() )
				.orElse( new TextureRegion( manager.get( "dwarf1.png" ) ) ),
				playerState.position.x, playerState.position.y );
	}
}
