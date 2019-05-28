package platform.game;

import platform.game.Actor.Damage;
import platform.util.Vector;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sound;

/**
 * Actor which defines the limit of a level If these limits are crossed, the
 * level restarts
 */
public class Limits extends Actor {
	private final int PRIORITY = 100;
	private Box box;

	/**
	 * If start is true, start sound, if flase do not start sound
	 */
	private boolean start;

	/**
	 * Constructor for the class Limits
	 * 
	 * @param box
	 * @param start
	 */
	public Limits(Box box, boolean start) {
		this.box = box;
		this.start = start;
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
	 * Method which tells what happens when the actor "Limtits" interacts with
	 * another actor In this case, the method says that if the box isn't
	 * colliding with the other actor's box, the actor "Limits" will cause
	 * infinite damage to the other actor
	 */
	@Override
	public void interact(Actor other) {
		super.interact(other);

		if (!box.isColliding(other.getBox())) {
			other.hurt(this, Damage.VOID, Double.POSITIVE_INFINITY, Vector.ZERO);
		}
		if (other instanceof Player && !getBox().isColliding(other.getPosition().add(new Vector(0, -20))) && start) {
			Sound.music("pada.wav");
			start = false;
		}
	}

	@Override
	public Box getBox() {
		return box;
	}

}
