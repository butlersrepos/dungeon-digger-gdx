package dungeondigger.player;

import lombok.Data;

import com.badlogic.gdx.physics.box2d.Body;

@Data
public class PlayerState {
	// TODO add sprite animations in here
	PlayerInputs	controller	= new PlayerInputs( this );
	Body			body;
	float			xPos		= 0f;
	float			yPos		= 0f;

	float			xSpeed		= 0f;
	float			ySpeed		= 0f;

	float			accel		= 50f;
	float			friction	= -10f;
	float			maxSpeed	= 100f;

	int				denom		= 50;
	boolean			moveLeft, moveRight, moveUp, moveDown;

	public void update( float dt ) {
		// System.out.println( "ySpeed: " + ySpeed );
		// System.out.println( "xSpeed: " + xSpeed );
		accel = 140f;
		friction = -10f;
		maxSpeed = 200f;

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
			if( !moveUp ) ySpeed += friction;
			ySpeed = highestBounded( ySpeed, 0, maxSpeed );
		}
		if( ySpeed < 0 ) {
			if( !moveDown ) ySpeed -= friction;
			ySpeed = lowestBounded( ySpeed, 0, -maxSpeed );
		}
	}

	private void updateXSpeed( float dt ) {
		xSpeed += ( moveRight ? accel * dt : 0 );
		xSpeed -= ( moveLeft ? accel * dt : 0 );

		if( xSpeed > 0 ) {
			if( !moveRight ) xSpeed += friction;
			xSpeed = highestBounded( xSpeed, 0, maxSpeed );
		}
		if( xSpeed < 0 ) {
			if( !moveLeft ) xSpeed -= friction;
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
