package dungeondigger.gamestate;

import com.badlogic.gdx.InputAdapter;

public abstract class GameState extends InputAdapter {
	protected GameStateManager	gsm;

	protected GameState( GameStateManager gsm ) {
		this.gsm = gsm;
	}

	public abstract void init();

	public abstract void update( float dt );

	public abstract void draw();

	public abstract void dispose();
}
