package dungeondigger.ui;

import static dungeondigger.Assets.manager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Value;

import dungeondigger.DungeonDigger;
import dungeondigger.gamestate.GameStateManager;
import dungeondigger.tools.References;

public class MenuElements {
	public static BitmapFont				regularFont;
	private static BitmapFont				titleFont;
	private static BitmapFont				menuOptionFont;
	private static BitmapFont				highlightedOptionFont;
	private static FreeTypeFontGenerator	generator;
	private static FreeTypeFontParameter	parameter;
	private static Skin						skin;
	private static Pixmap					pixmap;

	static {
		DungeonDigger.loadFonts();
		generateFonts();
		skin = new Skin();
		pixmap = new Pixmap( 1, 1, Format.RGBA8888 );
		pixmap.setColor( Clrs.white );
		pixmap.fill();
		skin.add( "white", new Texture( pixmap ) );
	}

	private static void generateFonts() {
		manager.finishLoadingAsset( "fonts/gothic-pixel.ttf" );
		generator = manager.get( "fonts/gothic-pixel.ttf" );
		parameter = new FreeTypeFontParameter();

		parameter.size = 36;
		parameter.color = Clrs.white;
		regularFont = generator.generateFont( parameter ); // font size 12 pixels

		parameter.color = Clrs.silver;
		parameter.size = 72;
		parameter.borderColor = Color.WHITE;
		parameter.borderWidth = 2;
		titleFont = generator.generateFont( parameter );

		parameter.size = 48;
		parameter.borderWidth = 1;
		parameter.color = Clrs.blue;
		menuOptionFont = generator.generateFont( parameter );

		parameter.borderColor = Clrs.yellow;
		parameter.borderWidth = 1.5f;
		highlightedOptionFont = generator.generateFont( parameter );
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
	}

	public static TextButton createStartMenuButton( BitmapFont font, String text ) {
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font != null ? font : menuOptionFont;
		textButtonStyle.up = skin.newDrawable( "white", Color.DARK_GRAY );
		textButtonStyle.down = skin.newDrawable( "white", Color.DARK_GRAY );
		textButtonStyle.over = skin.newDrawable( "white", Color.LIGHT_GRAY );

		TextButton button = new TextButton( text, textButtonStyle );
		button.addListener( new InputListener() {
			@Override
			public boolean touchDown( InputEvent event, float x, float y, int pointer, int button ) {
				DungeonDigger.GAMESTATEMANAGER.setState( GameStateManager.PLAY );
				return true;
			}
		} );

		padByPercentage( button, 0.1f );
		return button;
	}

	private static void padByPercentage( Table button, float percentage ) {
		button.padLeft( Value.percentWidth( percentage ) )
				.padRight( Value.percentWidth( percentage ) )
				.padTop( Value.percentHeight( percentage ) )
				.padBottom( Value.percentHeight( percentage ) );
	}

	public static Label createMenuTitle( BitmapFont font, String text ) {
		LabelStyle titleLabelStyle = new LabelStyle();
		titleLabelStyle.font = font != null ? font : titleFont;
		Label titleLabel = new Label( text, titleLabelStyle );
		return titleLabel;
	}

	public static Label createMenuSubTitle( BitmapFont font, String text ) {
		LabelStyle subtitleLabelStyle = new LabelStyle();
		subtitleLabelStyle.font = font != null ? font : regularFont;
		Label subtitleLabel = new Label( text, subtitleLabelStyle );
		return subtitleLabel;
	}

	public static TextButton createStartMenuButton() {
		return createStartMenuButton( menuOptionFont, References.START_BUTTON_TEXT );
	}

	public static Label createMenuTitle() {
		return createMenuTitle( titleFont, References.GAME_TITLE );
	}

	public static Label createMenuSubTitle() {
		return createMenuSubTitle( regularFont, References.GAME_SUBTITLE );
	}

	public static Label createMenuSubTitle( String text ) {
		return createMenuSubTitle( regularFont, text );
	}
}
