package platform.game;

import platform.util.Box;

import platform.util.Input;
import platform.util.Sprite;
import platform.util.Vector;
import platform.util.View;
import platform.util.Output;

/**
 * Simple solid actor that does nothing.
 */
public class Block extends Actor {
	private Box box;
	private Sprite sprite;
	private final int PRIORITY = 0;

	/**
	 * Constructor of the class Block
	 * 
	 * @param box
	 * @param sprite
	 */
	public Block(Box box, Sprite sprite) {
		this.box = box;
		this.sprite = sprite;
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void update(Input input) {
		super.update(input);
	}

	@Override
	public void draw(Input input, Output output) {
		if (sprite == null) {
			super.draw(input, output);
		} else {
			output.drawSprite(sprite, box);
		}
	}

	@Override
	public int compareTo(Actor other) {
		return super.compareTo(other);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public Box getBox() {
		return box;
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);
	}
}