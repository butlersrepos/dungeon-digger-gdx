package info.ericbutler.gamestate;

public class GameStateManager {
	private GameState	gameState;

	public static int	MENU	= 0;
	public static int	PLAY	= 1;

	public GameStateManager() {
		setState( MENU );
	}

	public void setState( int state ) {
		if( gameState != null ) {
			gameState.dispose();
		}

		if( state == MENU ) {
			gameState = new MenuState( this );
		}
		if( state == PLAY ) {
			gameState = new PlayState( this );
		}
	}

	public void update( float dt ) {
		gameState.update( dt );
	}

	public void draw() {
		gameState.draw();
	}
}
