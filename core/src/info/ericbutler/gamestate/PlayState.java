package info.ericbutler.gamestate;

import dungeondigger.environment.DungeonGenerator;

public class PlayState extends GameState {

	protected PlayState( GameStateManager gsm ) {
		super( gsm );
		init();
	}

	@Override
	public void init() {
		DungeonGenerator.generateDungeon();
	}

	@Override
	public void update( float dt ) {}

	@Override
	public void draw() {}

	@Override
	public void handleInput() {}

	@Override
	public void dispose() {}
}
