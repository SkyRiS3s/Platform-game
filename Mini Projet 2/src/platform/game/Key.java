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
 * Actor which activates or deactivates any other actor which reacts to a signal
 * It looks like a key and has 4 different colours
 */
public class Key extends Actor implements Signal {
	private int PRIORITY = 600;
	private double SIZE = 1;
	private Vector position;

	/**
	 * Boolean variable indicating whether a key was taken or not
	 */
	private boolean taken;

	/**
	 * String indicating the key's colour
	 */
	private String colour;

	/**
	 * Constructor of the class key
	 * 
	 * @param position
	 * @param taken
	 * @param colour
	 */
	public Key(Vector position, boolean taken, String colour) {
		if (position == null) {
			throw new NullPointerException();
		}
		this.position = position;
		this.taken = taken;
		this.colour = colour;
	}



	@Override
	public void update(Input input) {
		super.update(input);
	}

	@Override
	public Box getBox() {
		return new Box(position, SIZE, SIZE);
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	/**
	 * Method which draws the key in function of its colour
	 */
	public void draw(Input input, Output output) {
		Sprite sprite;
		if (taken == false) {
			if (colour == "blue") {
				sprite = getSprite("key.blue");
			} else if (colour == "red") {
				sprite = getSprite("key.red");
			} else if (colour == "green") {
				sprite = getSprite("key.green");
			} else if (colour == "yellow") {
				sprite = getSprite("key.yellow");
			} else {
				sprite = null;
			}
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
	 * Method which makes the key disappear as player (i.e instanceof Player)
	 * collides with it
	 * 
	 * @param actor
	 *            is the actor which interacts with the key
	 */
	@Override
	public void interact(Actor actor) {
		super.interact(actor);
		if (actor instanceof Player && getBox().isColliding(actor.getBox())) {
			Sound.music("key.wav");
			taken = true;
			getWorld().unregister(this);
		}
	}

	/**
	 * Method which indicates if the signal is active or not.
	 * 
	 * @see taken
	 */
	@Override
	public boolean isActive() {
		if (taken) {
			return true;
		} else {
			return false;
		}
	}
}
