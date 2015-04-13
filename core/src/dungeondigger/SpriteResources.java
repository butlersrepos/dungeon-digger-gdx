package dungeondigger;

import static dungeondigger.Assets.manager;
import static dungeondigger.tools.References.LPC_CHAR_FRAME_HEIGHT;
import static dungeondigger.tools.References.LPC_CHAR_FRAME_WIDTH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import dungeondigger.actors.ActionState;
import dungeondigger.g2d.ActorAnimationSet;
import dungeondigger.g2d.ActorAnimationSets;
import dungeondigger.g2d.LPCConfiguration;
import dungeondigger.g2d.SmartAnimation;
import dungeondigger.taxonomy.FourDirection;

public class SpriteResources {
	public static List<String>	sprites;

	static {
		sprites = new ArrayList<>();
		sprites.add( "spritesheets/darkelf-spearman-lpc.png" );
	}

	public static void buildSprites() {
		for( String sprite : sprites ) {
			if( sprite.endsWith( "-lpc.png" ) ) {
				processLPCSpriteSheet( sprite );
			}
		}
	}

	private static void processLPCSpriteSheet( String sprite ) {
		manager.finishLoadingAsset( sprite );
		Texture sheet = manager.get( sprite );
		TextureRegion[][] tmp = TextureRegion.split( sheet, LPC_CHAR_FRAME_WIDTH, LPC_CHAR_FRAME_HEIGHT );

		String animationSetName = sprite.replace( "spritesheets/", "" ).replace( "-lpc.png", "" );
		ActorAnimationSet newSet = new ActorAnimationSet();
		newSet.setTitle( animationSetName );
		for( LPCConfiguration animationConfig : LPCConfiguration.DEFAULTS ) {
			ActionState action = animationConfig.getActionState();
			HashMap<FourDirection, SmartAnimation> directionsMap = newSet.getDirectory().computeIfAbsent( action, ( a ) -> new HashMap<>() );
			SmartAnimation newAnimation = constructAnimation( tmp, animationConfig.getRowNumber(), animationConfig.getFrameCount(), animationConfig.getPlayMode(), animationConfig.getFrameRates() );
			directionsMap.put( animationConfig.getDirection(), newAnimation );
		}
		ActorAnimationSets.add( newSet );
	}

	private static SmartAnimation constructAnimation( TextureRegion[][] tmp, int row, int numberFrames, PlayMode playMode, float[] frameRate ) {
		TextureRegion[] frames = new TextureRegion[numberFrames];
		for( int i = 0; i < numberFrames; i++ ) {
			frames[i] = tmp[row][i];
		}
		SmartAnimation animation = new SmartAnimation( frameRate, frames );
		animation.setPlayMode( playMode );
		return animation;
	}

	public static void loadSprites() {
		for( String sprite : sprites ) {
			manager.load( sprite, Texture.class );
		}
	}
}
