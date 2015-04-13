package dungeondigger.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import dungeondigger.DungeonDigger;

public class DesktopLauncher {
	public static void main( String[] arg ) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.resizable = false;
		config.height = DungeonDigger.SCREEN_HEIGHT;
		config.width = DungeonDigger.SCREEN_WIDTH;

		new LwjglApplication( new DungeonDigger(), config );
	}
}
