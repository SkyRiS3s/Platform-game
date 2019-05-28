package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Input;
import platform.util.Sprite;
import platform.util.Vector;
import platform.util.View;
import platform.util.Output;
import java.awt.event.KeyEvent;

import platform.util.Sound;

/**
 * Actor which activates or deactivates any other actor that reacts to a signal
 * It looks like an old lever
 */
public class Lever extends Actor implements Signal {
	private final int PRIORITY = 100;
	private final double SIZE = 1.2;
	private final double DURATION = Double.POSITIVE_INFINITY;
	private Vector position;

	/**
	 * Lever is left: false, lever is right true
	 */
	private boolean value;
	
	/**
	 * Double variable which decrements at each update
	 * As it hits 0, the lever is deactivated.
	 * @see update()
	 */
	private double time = 0;

	/**
	 * Boolean variable indicating if the lever has to be changed or not (from its initial position, either left or right)
	 */
	private boolean active = false;

	/**
	 * Constructor of the class lever
	 * 
	 * @param position
	 * @param value
	 */
	public Lever(Vector position, boolean value) {
		if (position == null) {
			throw new NullPointerException();
		}
		this.position = position;
		this.value = value;
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public Box getBox() {
		return new Box(position, SIZE, SIZE);
	}
	
	/**
	 * Method which tells how the lever interacts with another actor
	 * @param other
	 */
	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (active) {
			Sound.music("lever.wav");
			if (value) {
				value = false;
			} else {
				value = true;
				time = DURATION;
			}
			active = false;
		}
	}
	
	/**
	 * Method which updates the lever over time In this case, the lever is
	 * deactivated after the chosen duration.
	 * As duration = Double.INFINITY, the lever will not be deactivated
	 */
	@Override
	public void update(Input input) {
		super.update(input);
		if (time == 0 && value) {
			value = false;
		} else if (time != 0 && !value) {
			time -= 1;
		}
	}

	/**
	 * Method which draws the lever. If the lever is activated, the picture
	 * "lever.right" is used If the lever is deactivated, the picture
	 * "lever.left" is used
	 * @see value
	 */
	@Override
	public void draw(Input input, Output output) {
		Sprite sprite;
		if (value) {
			sprite = getSprite("lever.right");
		} else if (!value) {
			sprite = getSprite("lever.left");
		} else {
			sprite = null;
		}

		if (sprite == null) {
			super.draw(input, output);
		} else {
			output.drawSprite(sprite, getBox());
		}
	}
	
	/**
	 * Depends on value
	 */
	@Override
	public boolean isActive() {
		if (value) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	/**
	 * Method which tells what kind of damage it is subject to
	 * 
	 * @param instigator which is the actor that causes the damage
	 * @param type
	 * @param amount
	 * @param location
	 */
	@Override
	public boolean hurt(Actor instigator, Damage type, double amount, Vector location) {
		super.hurt(instigator, type, amount, location);
		switch (type) {
		case ACTIVATION:
			active = true;
			return true;
		default:
			return super.hurt(instigator, type, amount, location);
		}
	}
}
