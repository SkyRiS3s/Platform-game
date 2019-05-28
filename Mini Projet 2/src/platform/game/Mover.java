package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * Actor which acts like an elevator and which reacts to a signal
 */
public class Mover extends Block {
	private Box box;
	private Sprite sprite;

	private Vector off;

	/**
	 * Vector which indicates the final position of the Mover (when it's turned
	 * on)
	 */
	private Vector on;
	private Signal signal;
	private double current = 0.0;

	/**
	 * Constructor of the class Mover
	 * 
	 * @param box
	 * @param sprite
	 * @param off
	 * @param on
	 * @param s
	 */
	public Mover(Box box, Sprite sprite, Vector off, Vector on, Signal s) {
		super(box, sprite);
		signal = s;
		this.sprite = sprite;
		this.on = on;
		this.off = off;
		this.box = box;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	/**
	 * Method which updates the Mover (it makes it move over time)
	 * 
	 * @param input
	 */
	@Override
	public void update(Input input) {
		super.update(input);
		if (signal.isActive()) {
			current += input.getDeltaTime();
			if (current > 1.0)
				current = 1.0;
		} else {
			current -= input.getDeltaTime();
			if (current < 0.0)
				current = 0.0;
		}
	}

	@Override
	public void draw(Input input, Output output) {
		if (sprite == null) {
			super.draw(input, output);
		} else {
			output.drawSprite(sprite, getBox());
		}
	}

	/**
	 * Method which tells what happens when the Mover is interacting with an
	 * other actor
	 * 
	 * @param other
	 *            is the other actor with whom the Mover is interacting
	 */
	@Override
	public void interact(Actor other) {
		super.interact(other);
	}

	/**
	 * interpolation between two points (on and off)
	 */
	@Override
	public Box getBox() {
		return new Box(off.mixed(on, current), box.getWidth(), box.getHeight());
	}
}
