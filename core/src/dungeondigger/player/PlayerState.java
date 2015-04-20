package dungeondigger.player;

import static dungeondigger.actors.ActionState.WALKING;
import static dungeondigger.taxonomy.FourDirection.NORTH;
import lombok.Data;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import dungeondigger.actors.ActionState;
import dungeondigger.taxonomy.FourDirection;

@Data
public class PlayerState {
	protected PlayerInputs		controller			= new PlayerInputs( this );
	protected PlayerRenderer	renderer			= new PlayerRenderer( this );

	protected ActionState		actionState			= WALKING;
	protected FourDirection		direction			= NORTH;

	protected Vector2			position			= new Vector2( 0f, 0f );
	protected Vector2			lastGoodPosition	= new Vector2( 0f, 0f );
	protected Movement2D		movements			= new Movement2D( 0f, 0f, 140f, 200f );

	protected boolean			moveLeft, moveRight, moveUp, moveDown;

	public void update( float dt ) {
		movements.update( this, dt );

		updateDirection();

		updateActionState();

		renderer.update( dt );
	}

	private void updateDirection() {
		if( movements.xSpeed == 0f && movements.ySpeed == 0f ) { return; }
		direction = FourDirection.valueOf( new Vector2( movements.xSpeed, movements.ySpeed ).angle( Vector2.Y ) );
	}

	private void updateActionState() {
		actionState = ActionState.IDLING;
		if( movements.xSpeed != 0f || movements.ySpeed != 0f ) {
			actionState = WALKING;
		}
	}

	public void restoreLastGoodPosition() {
		position = lastGoodPosition.cpy();
	}

	public void updateLastGoodPosition() {
		lastGoodPosition = position.cpy();
	}

	public Rectangle getBounds() {
		Integer frameWidth = renderer.getCurrentAnimation().map( a -> a.getKeyFrame().getRegionWidth() ).orElse( 0 );
		Integer frameHeight = renderer.getCurrentAnimation().map( a -> a.getKeyFrame().getRegionHeight() ).orElse( 0 );
		return new Rectangle( position.x, position.y, frameWidth, frameHeight );
	}
}
