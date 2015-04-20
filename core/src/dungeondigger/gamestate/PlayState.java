package dungeondigger.gamestate;

import static com.badlogic.gdx.Gdx.gl;
import static com.badlogic.gdx.Gdx.graphics;
import static dungeondigger.Assets.manager;
import static dungeondigger.tools.References.BACKGROUND_LAYER_NAME;
import static dungeondigger.tools.References.COLLISION_LAYER_NAME;
import static dungeondigger.tools.References.FOREGROUND_LAYER_NAME;
import lombok.extern.slf4j.Slf4j;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Predicate;

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
	private MapLayer					collisionLayer;

	private ShapeRenderer				shapeRenderer;

	protected PlayState( GameStateManager gsm ) {
		super( gsm );
		init();
	}

	@Override
	public void init() {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		setupPlayer();
		setupMap();
		setupInputs();
	}

	private void setupPlayer() {
		playerState = new PlayerState();
		player = playerState.getRenderer();
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
		collisionLayer = tiledMap.getLayers().get( COLLISION_LAYER_NAME );
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

		collisionDetection();

		playerState.updateLastGoodPosition();
	}

	private void collisionDetection() {
		MapObjects objects = collisionLayer.getObjects();
		Array<RectangleMapObject> AllTheRectangles = objects.getByType( RectangleMapObject.class );

		int checkDistance = 64;
		Predicate<RectangleMapObject> rectanglesWithinXofPlayer = rmo -> rmo.getRectangle().getCenter( new Vector2() ).dst( playerState.getPosition() ) < checkDistance;
		AllTheRectangles.select( rectanglesWithinXofPlayer )
				.forEach( r -> checkCollisionWithPlayer( r.getRectangle() ) );
	}

	public void checkCollisionWithPlayer( Rectangle rectangle ) {
		if( rectangle.overlaps( playerState.getBounds() ) ) {
			log.warn( "Collision detected, restoring last known good position!" );
			playerState.restoreLastGoodPosition();
		}
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

		drawCollisionBoxes();
	}

	private void drawCollisionBoxes() {
		shapeRenderer.setProjectionMatrix( camera.combined );
		Rectangle playerHitbox = playerState.getBounds();
		shapeRenderer.begin( ShapeType.Line );
		shapeRenderer.rect( playerHitbox.x, playerHitbox.y, playerHitbox.width, playerHitbox.height );
		shapeRenderer.end();
	}

	private void centerCameraOnPlayer() {
		camera.position.set( playerState.getPosition().x + 32, playerState.getPosition().y, 0 );
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
