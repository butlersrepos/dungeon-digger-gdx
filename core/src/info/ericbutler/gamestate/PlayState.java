package info.ericbutler.gamestate;


public class PlayState extends GameState {

	protected PlayState( GameStateManager gsm ) {
		super( gsm );
		init();
	}

	@Override
	public void init() {}

	@Override
	public void update( float dt ) {}

	@Override
	public void draw() {}

	@Override
	public void handleInput() {}

	@Override
	public void dispose() {}
}
