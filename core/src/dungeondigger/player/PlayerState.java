package dungeondigger.player;

import static dungeondigger.environment.Constants.friction;
import lombok.Data;

import com.badlogic.gdx.math.Vector2;

import dungeondigger.actors.ActionState;
import dungeondigger.taxonomy.FourDirection;

@Data
public class PlayerState {
	PlayerInputs	controller		= new PlayerInputs( this );
	PlayerRenderer	playerRenderer	= new PlayerRenderer( this );

	ActionState		actionState		= ActionState.WALKING;
	FourDirection	direction		= FourDirection.NORTH;

	float			xPos			= 0f;
	float			yPos			= 0f;

	float			xSpeed			= 0f;
	float			ySpeed			= 0f;

	float			accel			= 140f;
	float			maxSpeed		= 200f;

	boolean			moveLeft, moveRight, moveUp, moveDown;

	public void update( float dt ) {
		updateMovement( dt );
		direction = FourDirection.valueOf( new Vector2( xSpeed, ySpeed ).angle( Vector2.Y ) );
		updateActionState();
		playerRenderer.update( dt );
	}

	private void updateActionState() {
		if( xSpeed != 0f || ySpeed != 0f ) {
			actionState = ActionState.WALKING;
		} else {

		}
	}

	private void updateMovement( float dt ) {
		updatePosition( dt );
		updateXSpeed( dt );
		updateYSpeed( dt );
	}

	private void updatePosition( float dt ) {
		xPos += ( xSpeed * dt );
		yPos += ( ySpeed * dt );
	}

	private void updateYSpeed( float dt ) {
		ySpeed += ( moveUp ? accel * dt : 0 );
		ySpeed -= ( moveDown ? accel * dt : 0 );

		if( ySpeed > 0 ) {
			if( !moveUp ) ySpeed += friction * dt;
			ySpeed = highestBounded( ySpeed, 0, maxSpeed );
		}
		if( ySpeed < 0 ) {
			if( !moveDown ) ySpeed -= friction * dt;
			ySpeed = lowestBounded( ySpeed, 0, -maxSpeed );
		}
	}

	private void updateXSpeed( float dt ) {
		xSpeed += ( moveRight ? accel * dt : 0 );
		xSpeed -= ( moveLeft ? accel * dt : 0 );

		if( xSpeed > 0 ) {
			if( !moveRight ) xSpeed += friction * dt;
			xSpeed = highestBounded( xSpeed, 0, maxSpeed );
		}
		if( xSpeed < 0 ) {
			if( !moveLeft ) xSpeed -= friction * dt;
			xSpeed = lowestBounded( xSpeed, 0, -maxSpeed );
		}
	}

	float highestBounded( float a, float b, float bound ) {
		return Math.min( Math.max( a, b ), bound );
	}

	float lowestBounded( float a, float b, float bound ) {
		return Math.max( Math.min( a, b ), bound );
	}
}
