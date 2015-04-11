package dungeondigger;

import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileTests {
	@Test
	public void tryDirectory() throws Exception {
		FileHandle dir = Gdx.files.internal( "/" );
		for( FileHandle e : dir.list() ) {
			System.out.println( "entry -> " + e.name() );
		}
	}
}
