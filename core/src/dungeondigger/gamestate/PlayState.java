package dungeondigger.gamestate;

import static com.badlogic.gdx.Gdx.gl;
import static com.badlogic.gdx.Gdx.graphics;
import static dungeondigger.Assets.manager;
import static dungeondigger.tools.References.BACKGROUND_LAYER_NAME;
import static dungeondigger.tools.References.FOREGROUND_LAYER_NAME;
import lombok.extern.slf4j.Slf4j;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import dungeondigger.g2d.ActorAnimationSets;
import dungeondigger.player.PlayerRenderer;
import dungeondigger.player.PlayerState;
import dungeondigger.ui.MenuElements;

@Slf4j
public class PlayState extends GameState {
	private SpriteBatch					batch;
	private PlayerState					playerState;
	private PlayerRenderer				player;
	private InputMultiplexer			playStateInputMultiplexer;

	private TiledMap					tiledMap;
	private OrthogonalTiledMapRenderer	tiledMapRenderer;
	private OrthographicCamera			camera;
	private TiledMapTileLayer			backgroundLayer;
	private TiledMapTileLayer			foregroundLayer;

	protected PlayState( GameStateManager gsm ) {
		super( gsm );
		init();
	}

	@Override
	public void init() {
		batch = new SpriteBatch();
		setupPlayer();
		setupMap();
		setupInputs();
	}

	private void setupPlayer() {
		playerState = new PlayerState();
		player = playerState.getPlayerRenderer();
		player.setAnimationSet( ActorAnimationSets.get( "darkelf-spearman" ) );
	}

	private void setupMap() {
		camera = new OrthographicCamera();
		camera.setToOrtho( false, graphics.getWidth(), ( float ) graphics.getHeight() );
		camera.update();

		manager.finishLoadingAsset( "terrain/maps/first-try.tmx" );
		tiledMap = manager.get( "terrain/maps/first-try.tmx", TiledMap.class );
		tiledMapRenderer = new OrthogonalTiledMapRenderer( tiledMap, batch );
		backgroundLayer = ( TiledMapTileLayer ) tiledMap.getLayers().get( BACKGROUND_LAYER_NAME );
		foregroundLayer = ( TiledMapTileLayer ) tiledMap.getLayers().get( FOREGROUND_LAYER_NAME );
	}

	private void setupInputs() {
		playStateInputMultiplexer = new InputMultiplexer();
		playStateInputMultiplexer.addProcessor( playerState.getController() );
		playStateInputMultiplexer.addProcessor( this );
		Gdx.input.setInputProcessor( playStateInputMultiplexer );
	}

	@Override
	public void update( float dt ) {
		playerState.update( dt );
	}

	@Override
	public void draw() {
		gl.glClearColor( 0.066f, 0.066f, 0.066f, 1 );
		gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

		centerCameraOnPlayer();
		tiledMapRenderer.setView( camera );

		batch.setProjectionMatrix( camera.combined );
		batch.begin();

		tiledMapRenderer.renderTileLayer( backgroundLayer );
		// Render characters here
		player.draw( batch );
		tiledMapRenderer.renderTileLayer( foregroundLayer );

		MenuElements.regularFont.draw( batch, playerState.getDirection().toString(), camera.position.x, camera.position.y );
		batch.end();
	}

	private void centerCameraOnPlayer() {
		camera.position.set( playerState.getXPos() + 32, playerState.getYPos(), 0 );
		camera.update();
	}

	@Override
	public void dispose() {}

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
