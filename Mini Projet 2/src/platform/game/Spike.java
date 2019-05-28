package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

public class Spike extends Actor {
	private int PRIORITY = 100;
	private Vector position;
	private final double AMOUNT = 0.1;

	/**
	 * Constructor for the class Spike
	 * @param position
	 */
	public Spike(Vector position) {
		this.position = position;
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	@Override
	public void update(Input input) {
		super.update(input);
	}

	/**
	 * Method which allows the spike to deal physical dmg. It only deals damage
	 * if someone jumps (or lands) on it
	 */
	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (getBox().isColliding(other.getBox()) && other.getVelocity().getY() < -1) {
			other.hurt(this, Damage.PHYSICAL, AMOUNT, position);
		}
	}

	@Override
	public void draw(Input input, Output output) {
		Sprite sprite;
		sprite = getSprite("spikes");
		output.drawSprite(sprite, getBox());
	}

	@Override
	public Box getBox() {
		return new Box(position, 1, 1);
	}
}
