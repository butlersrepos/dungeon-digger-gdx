package info.ericbutler.gamestate;

public class PlayState extends GameState {

	protected PlayState( GameStateManager gsm ) {
		super( gsm );
		init();
	}

	@Override
	public void init() {}

	@Override
	public void update( float dt ) {
		System.out.println( "play state updating" );
	}

	@Override
	public void draw() {
		System.out.println( "play state drawing" );
	}

	@Override
	public void handleInput() {}

	@Override
	public void dispose() {}
}
