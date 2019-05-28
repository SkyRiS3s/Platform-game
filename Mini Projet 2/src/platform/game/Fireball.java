package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * Actor which is thrown by player to deal damage It takes the form of a
 * Fireball
 */
public class Fireball extends Projectile {
	private final int PRIORITY = 667;
	private final double SIZE = 0.4;

	/**
	 * Constructor of the class Fireball
	 * 
	 * @param position
	 * @param velocity
	 * @param owner
	 */
	public Fireball(Vector position, Vector velocity, Actor owner) {
		super(position, velocity, owner);
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	@Override
	public Box getBox() {
		return new Box(getPosition(), SIZE, SIZE);
	}
	
	/**
	 * Method required to make the fireball move
	 */
	@Override
	public void update(Input input) {
		super.update(input);
		double delta = input.getDeltaTime();
		Vector acceleration = new Vector(0.0, -9.81);
		setVelocity(getVelocity().add(acceleration.mul(delta)));
		setPosition(getPosition().add(getVelocity().mul(delta)));
	}

	@Override
	public void draw(Input input, Output output) {
		Sprite sprite = getSprite("fireball");
		if (sprite == null) {
			super.draw(input, output);
		} else {
			output.drawSprite(sprite, getBox(), input.getTime());
		}
	}
	
	/**
	 * Method which tells to the Fireball how to react with another actor If it
	 * collides with a "solid" actor, it bounces If it collides with a
	 * "non-solid" actor, it deals fire damage to the other actor
	 * 
	 * @param other
	 */
	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (other.isSolid()) {
			Vector delta = other.getBox().getCollision(getPosition());
			if (delta != null) {
				setPosition(getPosition().add(delta));
				setVelocity(getVelocity().mirrored(delta));
			}
		}
		if (other.getBox() != null) {
			if (!other.equals(getOwner()) && other.getBox().isColliding(getBox())
					&& other.hurt(this, Damage.FIRE, 1.0, getPosition())) {
				getWorld().unregister(this);
			}
		}
	}
}