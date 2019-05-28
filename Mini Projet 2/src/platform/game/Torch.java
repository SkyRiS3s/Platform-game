package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

public class Torch extends Actor implements Signal {
	private Box box;
	private final int PRIORITY = 34;

	/**
	 * Boolean variable indicating if the torch is lit or not
	 */
	private boolean lit;
	private double variation = 0;// used for the variation of the light

	/**
	 * Constructor for class Torch
	 * 
	 * @param center
	 * @param lit
	 */
	public Torch(Vector center, boolean lit) {
		this.lit = lit;
		box = getBox(center);
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	/**
	 * Method that draws the torch.
	 * If lit, there are two sprites, so that there is a small animation with the fire
	 * @param input
	 * @param output
	 */
	@Override
	public void draw(Input input, Output output) {
		Sprite sprite;
		if (lit) {
			String name = "torch.lit.1";
			if (variation < 0.3) {
				name = "torch.lit.2";
			}
			sprite = getSprite(name);
		} else {
			sprite = getSprite("torch");
		}
		output.drawSprite(sprite, box);
	}

	@Override
	public void update(Input input) {
		variation -= input.getDeltaTime();
		if (variation < 0.0) {
			variation = 0.6;
		}
	}

	/**
	 * Torch takes damage from another actor
	 * In this case, it lights up if it receives fire damage
	 * @param instigator is the actor which deals damage to the actor Torch
	 * @param Damage indicates the type of damage
	 * @param amount of damage (Double variable)
	 * @param location is position vector
	 */
	@Override
	public boolean hurt(Actor instigator, Damage type, double amount, Vector location) {
		super.hurt(instigator, type, amount, location);
		switch (type) {
		case AIR:
			lit = false;
			return true;
		case FIRE:
			lit = true;
			return true;
		default:
			return super.hurt(instigator, type, amount, location);
		}
	}

	public Box getBox(Vector center) {
		return new Box(center, 0.8, 0.8);
	}

	@Override
	public Box getBox() {
		return box;
	}

	public boolean isActive() {
		return lit;
	}
}