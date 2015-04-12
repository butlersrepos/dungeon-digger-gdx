package dungeondigger.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import dungeondigger.g2d.SmartAnimation;
import dungeondigger.player.PlayerState;

public class PlayState extends GameState {
	public static int					LPC_CHAR_FRAME_WIDTH	= 64;
	public static int					LPC_CHAR_FRAME_HEIGHT	= 64;
	public static int					LPC_WALK_LEFT_ROW		= 11;

	private SmartAnimation				walkAnim;
	private SpriteBatch					batch;
	private TextureRegion[][]			tmp;
	private PlayerState					playerState;
	private InputMultiplexer			playStateInputMultiplexer;
	private TiledMap					tiledMap;
	private OrthogonalTiledMapRenderer	tiledMapRenderer;
	private OrthographicCamera			camera;
	private AssetManager				assetManager;

	protected PlayState( GameStateManager gsm ) {
		super( gsm );
		init();
	}

	@Override
	public void init() {
		batch = new SpriteBatch();

		loadDarkElf();

		// DungeonGenerator.generateDungeon();

		// assetManager = new AssetManager( new InternalFileHandleResolver() );
		// assetManager.setLoader( TiledMap.class, new TmxMapLoader( new InternalFileHandleResolver() ) );
		// assetManager.load( "terrain/maps/first-try.tmx", TiledMap.class );
		// assetManager.finishLoading();

		setupMap();
		setupInputs();
	}

	private void setupMap() {

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho( false, w, h );
		camera.update();

		tiledMap = new TmxMapLoader().load( "terrain/maps/first-try.tmx" );
		tiledMapRenderer = new OrthogonalTiledMapRenderer( tiledMap );
	}

	private void setupInputs() {
		playStateInputMultiplexer = new InputMultiplexer();
		playerState = new PlayerState();
		// Controller definers
		playStateInputMultiplexer.addProcessor( playerState.getController() );
		playStateInputMultiplexer.addProcessor( this );
		Gdx.input.setInputProcessor( playStateInputMultiplexer );
	}

	@Override
	public void update( float dt ) {
		walkAnim.update( dt );
		playerState.update( dt );
	}

	@Override
	public void draw() {
		Gdx.gl.glClearColor( 0.066f, 0.066f, 0.066f, 1 );
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

		// TODO variablize these
		camera.position.set( playerState.getXPos() + 32, playerState.getYPos(), 0 );
		camera.update();

		// TODO render trees on top of player
		tiledMapRenderer.setView( camera );
		tiledMapRenderer.render();

		// TODO methodize this?
		batch.setProjectionMatrix( camera.combined );
		batch.begin();
		batch.draw( walkAnim.getKeyFrame(), playerState.getXPos(), playerState.getYPos() );
		batch.end();
	}

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

	@Override
	public boolean keyDown( int keycode ) {
		if( keycode == Input.Keys.NUM_1 ) {
			tiledMap.getLayers().get( 0 ).setVisible( !tiledMap.getLayers().get( 0 ).isVisible() );
			return true;
		}
		if( keycode == Input.Keys.NUM_2 ) {
			tiledMap.getLayers().get( 1 ).setVisible( !tiledMap.getLayers().get( 1 ).isVisible() );
			return true;
		}
		return false;
	}
}
