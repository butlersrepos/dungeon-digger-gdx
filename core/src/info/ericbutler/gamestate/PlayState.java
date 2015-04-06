package info.ericbutler.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import dungeondigger.environment.DungeonGenerator;
import dungeondigger.g2d.SmartAnimation;

public class PlayState extends GameState {
	public static int	LPC_CHAR_FRAME_WIDTH	= 64;
	public static int	LPC_CHAR_FRAME_HEIGHT	= 64;
	public static int	LPC_WALK_LEFT_ROW		= 11;

	SmartAnimation		walkAnim;
	SpriteBatch			batch;
	TextureRegion[][]	tmp;

	protected PlayState( GameStateManager gsm ) {
		super( gsm );
		init();
	}

	@Override
	public void init() {
		batch = new SpriteBatch();
		loadDarkElf();
		DungeonGenerator.generateDungeon();
	}

	@Override
	public void update( float dt ) {
		walkAnim.update( dt );
	}

	@Override
	public void draw() {
		Gdx.gl.glClearColor( 0.066f, 0.066f, 0.066f, 1 );
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
		batch.begin();

		batch.draw( walkAnim.getKeyFrame(), 200, 200 );

		batch.end();
	}

	@Override
	public void handleInput() {}

	@Override
	public void dispose() {}

	private void loadDarkElf() {
		Texture deSheet = new Texture( Gdx.files.internal( "darkelf-spearman.png" ) );
		tmp = TextureRegion.split( deSheet, LPC_CHAR_FRAME_WIDTH, LPC_CHAR_FRAME_HEIGHT );
		TextureRegion[] walkFrames = new TextureRegion[9];
		for( int i = 0; i < 9; i++ ) {
			walkFrames[i] = tmp[LPC_WALK_LEFT_ROW][i];
		}
		walkAnim = new SmartAnimation( 0.1f, walkFrames );
		walkAnim.setPlayMode( PlayMode.LOOP );
	}
}
