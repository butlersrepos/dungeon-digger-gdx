package info.ericbutler.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Rectangle;

import dungeondigger.DungeonDigger;
import dungeondigger.g2d.MenuOption;

public class MenuState extends GameState implements InputProcessor {
	SpriteBatch						batch;
	BitmapFont						regularFont;
	BitmapFont						titleFont;
	BitmapFont						menuOptionFont;
	BitmapFont						highlightedOptionFont;
	private FreeTypeFontGenerator	generator;
	private FreeTypeFontParameter	parameter;
	MenuOption						startGame;
	InputMultiplexer				m;

	protected MenuState( GameStateManager gsm ) {
		super( gsm );
		init();
	}

	@Override
	public void init() {
		batch = new SpriteBatch();
		generateFonts();
		generateMenuEventAreas();
		setupInputs();
	}

	@Override
	public void update( float dt ) {}

	@Override
	public void draw() {
		batch.begin();

		titleFont.draw( batch, "Vessels of Strife", 100, DungeonDigger.SCREEN_HEIGHT - 50 );
		regularFont.draw( batch, "Wandering without Gods", 250, DungeonDigger.SCREEN_HEIGHT - 100 );
		startGame.draw( batch );

		batch.end();
	}

	private void setupInputs() {
		m = new InputMultiplexer();
		m.addProcessor( startGame );
		m.addProcessor( this );
		Gdx.input.setInputProcessor( m );
	}

	private void generateMenuEventAreas() {
		startGame = new MenuOption( menuOptionFont, "Begin Journey" );
		startGame.highlightFont = highlightedOptionFont;
		startGame.area = new Rectangle( 200,
				DungeonDigger.SCREEN_HEIGHT - 350,
				startGame.font.getBounds( startGame.text ).width,
				startGame.font.getBounds( startGame.text ).height );
	}

	private void generateFonts() {
		generator = new FreeTypeFontGenerator( Gdx.files.internal( "fonts/gothic-pixel.ttf" ) );
		parameter = new FreeTypeFontParameter();

		parameter.size = 36;
		parameter.color = Color.WHITE;
		regularFont = generator.generateFont( parameter ); // font size 12 pixels

		parameter.color = Color.valueOf( "ddddddff" );
		parameter.size = 72;
		parameter.borderColor = Color.WHITE;
		parameter.borderWidth = 2;
		titleFont = generator.generateFont( parameter );

		parameter.size = 48;
		parameter.borderWidth = 1;
		parameter.color = Color.valueOf( "0074D9FF" );
		menuOptionFont = generator.generateFont( parameter );

		parameter.borderColor = Color.valueOf( "ffdc00ff" );
		parameter.borderWidth = 1.5f;
		highlightedOptionFont = generator.generateFont( parameter );
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
	}

	@Override
	public boolean keyDown( int keycode ) {
		return false;
	}

	@Override
	public boolean keyUp( int keycode ) {
		return false;
	}

	@Override
	public boolean keyTyped( char character ) {
		return false;
	}

	@Override
	public boolean touchDown( int screenX, int screenY, int pointer, int button ) {
		return false;
	}

	@Override
	public boolean touchUp( int screenX, int screenY, int pointer, int button ) {
		return false;
	}

	@Override
	public boolean touchDragged( int screenX, int screenY, int pointer ) {
		return false;
	}

	@Override
	public boolean mouseMoved( int screenX, int screenY ) {
		return false;
	}

	@Override
	public boolean scrolled( int amount ) {
		return false;
	}

	@Override
	public void handleInput() {}

	@Override
	public void dispose() {}
}
