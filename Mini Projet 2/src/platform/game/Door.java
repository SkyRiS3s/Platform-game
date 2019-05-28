package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Sprite;
import platform.util.Vector;
import platform.util.View;
import platform.util.Output;

/**
 * Simple solid actor that disappears when triggered by a signal.
 */
public class Door extends Block implements Signal {
	private Box box;
	private Sprite sprite;
	private Signal signal;

	/**
	 * Constructor of the class
	 * 
	 * @param box
	 * @param sprite
	 * @param s
	 */
	public Door(Box box, Sprite sprite, Signal s) {
		super(box, sprite);
		this.box = box;
		this.sprite = sprite;
		signal = s;
	}

	@Override
	public void update(Input input) {
		super.update(input);
	}

	/**
	 * Method which draws the door if the signal is active it is drwan and if
	 * the signal isn't active the door isn't drawn
	 */
	@Override
	public void draw(Input input, Output output) {
		if (isActive()) {
			if (sprite == null) {
				super.draw(input, output);
			} else {
				output.drawSprite(sprite, box);
			}
		}
	}

	@Override
	public int compareTo(Actor other) {
		return super.compareTo(other);
	}

	/**
	 * Method which indicates that the door is solid if the Signal is active and
	 * it is non-solid otherwise
	 */
	@Override
	public boolean isSolid() {
		if (isActive()) {
			return true;
		}
		return false;
	}
	
	@Override
	public Box getBox() {
		if (isActive()) {
			return box;
		}
		return null;
	}
	
	@Override
	public void interact(Actor other) {
		super.interact(other);
	}
	
	@Override
	public boolean isActive() {
		if (signal.isActive()) {
			return false;
		} else {
			return true;
		}
	}
}
