package dungeondigger.player;

import static dungeondigger.environment.Constants.friction;
import dungeondigger.tools.DDMathUtils;

public class Movement2D {
	protected float	xSpeed;
	protected float	ySpeed;
	private float	accel;
	private float	maxSpeed;

	public Movement2D( float xSpeed, float ySpeed, float accel, float maxSpeed ) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.accel = accel;
		this.maxSpeed = maxSpeed;
	}

	public void update( PlayerState playerState, float dt ) {
		updatePosition( playerState, dt );
		updateXSpeed( playerState, dt );
		updateYSpeed( playerState, dt );
	}

	private void updatePosition( PlayerState playerState, float dt ) {
		playerState.getPosition().x += ( xSpeed * dt );
		playerState.getPosition().y += ( ySpeed * dt );
	}

	private void updateXSpeed( PlayerState playerState, float dt ) {
		xSpeed += ( playerState.moveRight ? accel * dt : 0 );
		xSpeed -= ( playerState.moveLeft ? accel * dt : 0 );

		if( xSpeed > 0 ) {
			if( !playerState.moveRight ) xSpeed += friction * dt;
			xSpeed = DDMathUtils.maxWithinBound( xSpeed, 0, maxSpeed );
		}
		if( xSpeed < 0 ) {
			if( !playerState.moveLeft ) xSpeed -= friction * dt;
			xSpeed = DDMathUtils.minWithinBound( xSpeed, 0, -maxSpeed );
		}
	}

	private void updateYSpeed( PlayerState playerState, float dt ) {
		ySpeed += ( playerState.moveUp ? accel * dt : 0 );
		ySpeed -= ( playerState.moveDown ? accel * dt : 0 );

		if( ySpeed > 0 ) {
			if( !playerState.moveUp ) ySpeed += friction * dt;
			ySpeed = DDMathUtils.maxWithinBound( ySpeed, 0, maxSpeed );
		}
		if( ySpeed < 0 ) {
			if( !playerState.moveDown ) ySpeed -= friction * dt;
			ySpeed = DDMathUtils.minWithinBound( ySpeed, 0, -maxSpeed );
		}
	}
}