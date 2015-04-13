package dungeondigger;

import static dungeondigger.Assets.manager;
import static dungeondigger.tools.References.KEY_BINDINGS;
import static dungeondigger.tools.References.SLOT_BINDINGS;

import java.io.File;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;

import dungeondigger.gamestate.GameStateManager;

public class DungeonDigger extends ApplicationAdapter {
	public static GameStateManager	GAMESTATEMANAGER;
	public static int				SCREEN_WIDTH	= 800;
	public static int				SCREEN_HEIGHT	= 600;

	@Override
	public void create() {
		loadAllTheThings();
		GAMESTATEMANAGER = new GameStateManager();
	}

	@Override
	public void render() {
		GAMESTATEMANAGER.update( Gdx.graphics.getDeltaTime() );
		GAMESTATEMANAGER.draw();
	}

	public static void loadAllTheThings() {
		prepDirectories();
		SpriteResources.loadSprites();
		loadImages();
		loadKeySettings();
		loadMaps();
	}

	public static void loadImages() {
		// Models
		manager.load( "dwarf1.png", Texture.class );
		manager.load( "engy.png", Texture.class );
		manager.load( "terrain/planetcute/dirt_floor_80x135.png", Texture.class );
		manager.load( "terrain/planetcute/stone_wall_80x135.png", Texture.class );
		manager.load( "terrain/planetcute/Stone Block.png", Texture.class );
		manager.load( "terrain/planetcute/dirt floor 100x120.png", Texture.class );
		manager.load( "magic_reticle.png", Texture.class );
	}

	public static void loadFonts() {
		manager.load( "fonts/gothic-pixel.ttf", FreeTypeFontGenerator.class );
	}

	private static void loadMaps() {
		manager.load( "terrain/maps/first-try.tmx", TiledMap.class );
	}

	public static void loadKeySettings() {
		// Setup standard keyBindings
		KEY_BINDINGS.put( Input.Keys.P, "pause" );
		KEY_BINDINGS.put( Input.Keys.W, "moveUp" );
		KEY_BINDINGS.put( Input.Keys.S, "moveDown" );
		KEY_BINDINGS.put( Input.Keys.A, "moveLeft" );
		KEY_BINDINGS.put( Input.Keys.D, "moveRight" );
		KEY_BINDINGS.put( Input.Keys.NUM_1, "slot1" );
		KEY_BINDINGS.put( Input.Keys.NUM_2, "slot2" );
		KEY_BINDINGS.put( Input.Keys.NUM_3, "slot3" );
		KEY_BINDINGS.put( Input.Keys.NUM_4, "slot4" );
		KEY_BINDINGS.put( Input.Keys.GRAVE, "slot5" );
		KEY_BINDINGS.put( Input.Keys.Q, "slot6" );
		KEY_BINDINGS.put( Input.Keys.E, "slot7" );
		KEY_BINDINGS.put( Input.Keys.R, "slot8" );
		KEY_BINDINGS.put( Input.Keys.F, "slot9" );
		KEY_BINDINGS.put( Input.Keys.Z, "slot10" );
		KEY_BINDINGS.put( Input.Keys.X, "slot11" );
		KEY_BINDINGS.put( Input.Keys.C, "slot12" );
		KEY_BINDINGS.put( Input.Keys.V, "slot13" );
		KEY_BINDINGS.put( Input.Keys.SPACE, "slot14" );
		// TODO: there used to be a config.ini with hotkeys for user like "W=moveUp" /shrug
		// Setup standard/empty hotbars
		SLOT_BINDINGS.put( "slot1", "fireball" );
		SLOT_BINDINGS.put( "slot2", "doom" );
		SLOT_BINDINGS.put( "slot3", "empty" );
		SLOT_BINDINGS.put( "slot4", "empty" );
		SLOT_BINDINGS.put( "slot5", "empty" );
		SLOT_BINDINGS.put( "slot6", "empty" );
		SLOT_BINDINGS.put( "slot7", "empty" );
		SLOT_BINDINGS.put( "slot8", "empty" );
		SLOT_BINDINGS.put( "slot9", "empty" );
		SLOT_BINDINGS.put( "slot10", "empty" );
		SLOT_BINDINGS.put( "slot11", "empty" );
		SLOT_BINDINGS.put( "slot12", "empty" );
		SLOT_BINDINGS.put( "slot13", "empty" );
		SLOT_BINDINGS.put( "slot14", "empty" );
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
}
