package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Input;
import platform.util.Sprite;
import platform.util.Vector;
import platform.util.View;
import platform.util.Output;
import java.awt.event.KeyEvent;
import platform.game.level.Level;

/**
 * Door that allows the player to get to the next level
 */
public class Exit extends Actor implements Signal {
	private Vector position;
	private final int PRIORITY = 50;
	private final double SIZE = 1;
	private Level level;
	private Signal signal;

	
	public Exit(Vector position, Level level, Signal signal) {
		if (position == null) {
			throw new NullPointerException();
		}
		this.position = position;
		this.level = level;
		if (signal == null) {
			this.signal = new Constant();
		} else {
			this.signal = signal;
		}
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
	 * Method which allows us to go to another level as someone enters the Exit door(i.e if an actor collides with the Exit's box)
	 * @param other is an actor which interacts with the Exit door 
	 */
	@Override
	public void interact(Actor other) {
		if (other instanceof Player) {
			if (getBox().isColliding(other.getBox()) && signal.isActive()) {
				getWorld().setNextLevel(level);
				getWorld().nextLevel();
			}
		}
	}

	@Override
	public boolean isActive() {
		return true;
	}
	
	@Override
	public Box getBox() {
		return new Box(position, SIZE, SIZE);
	}
	
	/**
	 * Method which draws an open door if the door is closed, if the signal is active, a closed door if the signal is inactive
	 */
	@Override
	public void draw(Input input, Output output) {
		Sprite sprite;
		if (isActive()) {
			sprite = getSprite("door.open");
		} else if (isActive() == false) {
			sprite = getSprite("door.closed");
		} else {
			sprite = null;
		}

		if (sprite == null) {
			super.draw(input, output);
		} else {
			output.drawSprite(sprite, getBox());
		}
	}
}
