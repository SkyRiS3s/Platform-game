package platform.game;

import platform.game.Actor.Damage;
//import java.util.Vector;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * Base class for projectiles (any actor which is thrown by the player) (i.e
 * fireballs, ... )
 */
public class Projectile extends Actor {

	private Vector position;
	private Vector velocity;
	
	/**
	 * Owner is the Actor which throws the projectile
	 */
	private Actor owner;
	
	private final int PRIORITY = 666;
	private final double SIZE = 0.4;

	/**
	 * Constructor for the class Porjectile
	 * 
	 * @param position
	 * @param velocity
	 * @param owner
	 */
	public Projectile(Vector position, Vector velocity, Actor owner) {
		if (position == null || velocity == null) {
			throw new NullPointerException();
		} else {
			this.position = position;
			this.velocity = velocity;
			this.owner = owner;
		}
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	@Override
	public Box getBox() {
		return new Box(position, SIZE, SIZE);
	}

	@Override
	public void update(Input input) {
		super.update(input);
	}

	@Override
	public void draw(Input input, Output output) {
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);
	}

	@Override
	public boolean hurt(Actor instigator, Damage type, double amount, Vector location) {
		return super.hurt(instigator, type, amount, location);
	}

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public Actor getOwner() {
		return owner;
	}

	public void setOwner(Actor owner) {
		this.owner = owner;
	}
}