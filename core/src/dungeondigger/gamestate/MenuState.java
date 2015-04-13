package dungeondigger.gamestate;

import static dungeondigger.Assets.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import dungeondigger.SpriteResources;
import dungeondigger.tools.References;
import dungeondigger.ui.MenuElements;

public class MenuState extends GameState {
	Stage	stage;

	protected MenuState( GameStateManager gsm ) {
		super( gsm );
		init();
	}

	@Override
	public void init() {
		SpriteResources.buildSprites();
		setupUI();
	}

	private void setupUI() {
		Table table = new Table();
		table.setFillParent( true );
		table.align( Align.top );

		stage = new Stage();
		stage.addActor( table );

		Label titleLabel = MenuElements.createMenuTitle();
		table.add( titleLabel ).expandX().left()
				.padTop( Gdx.graphics.getHeight() * 0.05f )
				.padLeft( Value.percentWidth( 0.25f ) );
		table.row();

		Label subTitleLabel = MenuElements.createMenuSubTitle();
		table.add( subTitleLabel ).padTop( titleLabel.getHeight() * -0.3f );
		table.row();

		TextButton startButton = MenuElements.createStartMenuButton();
		table.add( startButton ).padTop( Gdx.graphics.getHeight() * 0.33f );
		table.row();

		Label versionLabel = MenuElements.createMenuSubTitle( References.BUILD_NUMBER );
		table.add( versionLabel ).expandX().expandY().bottom().right();
		table.row();

		Gdx.input.setInputProcessor( stage );
	}

	@Override
	public void update( float dt ) {
		manager.update();
	}

	@Override
	public void draw() {
		stage.act( Math.min( Gdx.graphics.getDeltaTime(), 1 / 30f ) );
		stage.draw();
	}

	@Override
	public void resize( int width, int height ) {
		stage.getViewport().update( width, height, true );
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
