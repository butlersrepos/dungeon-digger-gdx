package dungeondigger.player;

import lombok.extern.slf4j.Slf4j;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

@Slf4j
public class PlayerInputs extends InputAdapter {
	PlayerState	playerState;

	public PlayerInputs( PlayerState ps ) {
		playerState = ps;
	}

	@Override
	public boolean keyDown( int keycode ) {
		log.info( "Key: {} pressed down", Keys.toString( keycode ) );

		if( keycode == Input.Keys.LEFT ) {
			playerState.setMoveLeft( true );
			return true;
		}
		if( keycode == Input.Keys.RIGHT ) {
			playerState.setMoveRight( true );
			return true;
		}
		if( keycode == Input.Keys.UP ) {
			playerState.setMoveUp( true );
			return true;
		}
		if( keycode == Input.Keys.DOWN ) {
			playerState.setMoveDown( true );
			return true;
		}

		return super.keyDown( keycode );
	}

	@Override
	public boolean keyUp( int keycode ) {
		if( keycode == Input.Keys.LEFT ) {
			playerState.setMoveLeft( false );
			return true;
		}
		if( keycode == Input.Keys.RIGHT ) {
			playerState.setMoveRight( false );
			return true;
		}
		if( keycode == Input.Keys.UP ) {
			playerState.setMoveUp( false );
			return true;
		}
		if( keycode == Input.Keys.DOWN ) {
			playerState.setMoveDown( false );
			return true;
		}

		return super.keyUp( keycode );
	}
}
