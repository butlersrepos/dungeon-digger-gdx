package dungeondigger;

import info.ericbutler.gamestate.GameStateManager;

import java.io.File;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dungeondigger.tools.References;

public class DungeonDigger extends ApplicationAdapter {
	public static GameStateManager	GAMESTATEMANAGER;
	public static AssetManager		assetManager;
	public static int				SCREEN_WIDTH	= 768;
	public static int				SCREEN_HEIGHT	= 512;
	SpriteBatch						batch;
	Texture							img;
	OrthographicCamera				camera;

	@Override
	public void create() {
		assetManager = new AssetManager();
		importSettings();

		batch = new SpriteBatch();
		GAMESTATEMANAGER = new GameStateManager();
		camera = new OrthographicCamera( SCREEN_WIDTH, SCREEN_HEIGHT );
		camera.setToOrtho( true, SCREEN_WIDTH, SCREEN_HEIGHT );

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor( 0.066f, 0.066f, 0.066f, 1 );
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
		batch.begin();

		GAMESTATEMANAGER.update( Gdx.graphics.getDeltaTime() );
		GAMESTATEMANAGER.draw();

		batch.end();
	}

	public static void importSettings() {
		prepDirectories();
		loadImages();
		loadCharacterFiles();
		loadAbilities();
		loadKeySettings();
		loadMobs();
	}

	public static void prepDirectories() {
		File file = new File( "data" );
		if( !file.isDirectory() ) {
			file.mkdir();
		}
		file = new File( "data/characters" );
		if( !file.isDirectory() ) {
			file.mkdir();
		}
		file = new File( "data/maps" );
		if( !file.isDirectory() ) {
			file.mkdir();
		}
	}

	public static void loadImages() {
		// Models
		References.IMAGES.put( "dwarf1", new Texture( Gdx.files.internal( "dwarf1.png" ) ) );
		References.IMAGES.put( "engy", new Texture( Gdx.files.internal( "engy.png" ) ) );
		// Terrain
		References.IMAGES.put( "FLOOR1", new Texture( Gdx.files.internal( "terrain/planetcute/dirt_floor_80x135.png" ) ) );
		References.IMAGES.put( "WALL1", new Texture( Gdx.files.internal( "terrain/planetcute/stone_wall_80x135.png" ) ) );
		References.IMAGES.put( "entranceImage", new Texture( Gdx.files.internal( "terrain/planetcute/Stone Block.png" ) ) );
		// unused
		References.IMAGES.put( "roomWallImage", new Texture( Gdx.files.internal( "terrain/planetcute/dirt floor 100x120.png" ) ) );
		References.IMAGES.put( "roomFloorImage", new Texture( Gdx.files.internal( "terrain/planetcute/dirt floor 100x120.png" ) ) );
		// UI
		References.IMAGES.put( "magicReticle", new Texture( Gdx.files.internal( "magic_reticle.png" ) ) );
	}

	public static void loadCharacterFiles() {}

	public static void loadAbilities() {}

	public static void loadKeySettings() {
		// Setup standard keyBindings
		References.KEY_BINDINGS.put( Input.Keys.P, "pause" );
		References.KEY_BINDINGS.put( Input.Keys.W, "moveUp" );
		References.KEY_BINDINGS.put( Input.Keys.S, "moveDown" );
		References.KEY_BINDINGS.put( Input.Keys.A, "moveLeft" );
		References.KEY_BINDINGS.put( Input.Keys.D, "moveRight" );
		References.KEY_BINDINGS.put( Input.Keys.NUM_1, "slot1" );
		References.KEY_BINDINGS.put( Input.Keys.NUM_2, "slot2" );
		References.KEY_BINDINGS.put( Input.Keys.NUM_3, "slot3" );
		References.KEY_BINDINGS.put( Input.Keys.NUM_4, "slot4" );
		References.KEY_BINDINGS.put( Input.Keys.GRAVE, "slot5" );
		References.KEY_BINDINGS.put( Input.Keys.Q, "slot6" );
		References.KEY_BINDINGS.put( Input.Keys.E, "slot7" );
		References.KEY_BINDINGS.put( Input.Keys.R, "slot8" );
		References.KEY_BINDINGS.put( Input.Keys.F, "slot9" );
		References.KEY_BINDINGS.put( Input.Keys.Z, "slot10" );
		References.KEY_BINDINGS.put( Input.Keys.X, "slot11" );
		References.KEY_BINDINGS.put( Input.Keys.C, "slot12" );
		References.KEY_BINDINGS.put( Input.Keys.V, "slot13" );
		References.KEY_BINDINGS.put( Input.Keys.SPACE, "slot14" );
		// TODO: there used to be a config.ini with hotkeys for user like "W=moveUp" /shrug
		// Setup standard/empty hotbars
		References.SLOT_BINDINGS.put( "slot1", "fireball" );
		References.SLOT_BINDINGS.put( "slot2", "doom" );
		References.SLOT_BINDINGS.put( "slot3", "empty" );
		References.SLOT_BINDINGS.put( "slot4", "empty" );
		References.SLOT_BINDINGS.put( "slot5", "empty" );
		References.SLOT_BINDINGS.put( "slot6", "empty" );
		References.SLOT_BINDINGS.put( "slot7", "empty" );
		References.SLOT_BINDINGS.put( "slot8", "empty" );
		References.SLOT_BINDINGS.put( "slot9", "empty" );
		References.SLOT_BINDINGS.put( "slot10", "empty" );
		References.SLOT_BINDINGS.put( "slot11", "empty" );
		References.SLOT_BINDINGS.put( "slot12", "empty" );
		References.SLOT_BINDINGS.put( "slot13", "empty" );
		References.SLOT_BINDINGS.put( "slot14", "empty" );
	}

	public static void loadMobs() {}

}
