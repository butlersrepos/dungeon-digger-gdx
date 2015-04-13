package dungeondigger;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets {
	public static AssetManager	manager;

	static {
		manager = new AssetManager();
		manager.setLoader( TiledMap.class, new TmxMapLoader( new InternalFileHandleResolver() ) );
		manager.setLoader( FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader( new InternalFileHandleResolver() ) );
		manager.setLoader( Texture.class, new TextureLoader( new InternalFileHandleResolver() ) );
	}
}
