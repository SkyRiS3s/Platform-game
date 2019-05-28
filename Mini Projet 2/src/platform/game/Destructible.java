package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

public class Destructible extends Block {
	/**
	 * Boolean Variable telling if the actor disappears or not
	 */
	private boolean vanish;

	/**
	 * Constructor of the class Destructible
	 * 
	 * @param box
	 * @param sprite
	 */
	public Destructible(Box box, Sprite sprite, boolean vanish) {
		super(box, sprite);
		this.vanish = vanish;
	}

	/**
	 * Unregisters the actor actor if vanish is true
	 * 
	 * @see vanish
	 */
	@Override
	public void update(Input input) {
		super.update(input);
		if (vanish)
			getWorld().unregister(this);
	}

	/**
	 * Draws the actor if it has not vanished (i.e if vanish is false)
	 */
	@Override
	public void draw(Input input, Output output) {
		if (!vanish) {
			if (getSprite() == null) {
				super.draw(input, output);
			} else {
				output.drawSprite(getSprite(), getBox());
			}
		}
	}

	/**
	 * Actor takes damage from another actor
	 * 
	 * @param instigator
	 *            is the actor which deals damage to the actor "Alive"
	 * @param Damage
	 *            indicates the type of damage
	 * @param amount
	 * @param location
	 *            is position vector
	 */
	@Override
	public boolean hurt(Actor instigator, Damage type, double amount, Vector location) {
		super.hurt(instigator, type, amount, location);
		switch (type) {
		case FIRE:
			vanish = true;
			return true;
		default:
			return super.hurt(instigator, type, amount, location);
		}
	}
}
