package dungeondigger.g2d;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;

import dungeondigger.DungeonDigger;

public class MenuOption extends InputAdapter {
	public boolean		isMouseOver	= false;
	public BitmapFont	font;
	public BitmapFont	highlightFont;
	public String		text;
	public Rectangle	area;

	public MenuOption() {
		this( new BitmapFont() );
	}

	public MenuOption( BitmapFont f ) {
		this( f, "Default Menu Text" );
	}

	public MenuOption( BitmapFont f, String t ) {
		this( f, t, new Rectangle( 0, 0, 0, 0 ) );
	}

	public MenuOption( BitmapFont f, String t, Rectangle r ) {
		font = f;
		text = t;
		area = r;
	}

	@Override
	public boolean mouseMoved( int screenX, int screenY ) {
		int translatedY = DungeonDigger.SCREEN_HEIGHT - screenY;
		isMouseOver = area.contains( screenX, translatedY );
		return true;
	}

	public void draw( Batch batch ) {
		BitmapFont fontToUse = font;
		if( highlightFont != null && isMouseOver ) {
			fontToUse = highlightFont;
		}

		fontToUse.draw( batch, text, area.x, area.y );
	}
}
