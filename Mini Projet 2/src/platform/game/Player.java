package platform.game;

import platform.game.Actor.Damage;
import platform.game.level.BasicLevel;
import platform.game.level.Level1;
import platform.util.Box;
import platform.util.Input;
import platform.util.Sprite;
import platform.util.Vector;
import platform.util.View;
import platform.util.Output;
import java.awt.event.KeyEvent;
import java.util.logging.Level;

import javax.print.attribute.standard.Media;
import platform.util.Sound;

public class Player extends Alive {
	private final int PRIORITY = 42;
	private final double SIZE = 0.5;

	// Max nb of ammunitions:
	private final int FIREBALLS_MAX = 15;
	private final int BOMBS_MAX = 2;
	private final int GRENADES_MAX = 4;

	// counters of the ammunitions:
	private int fireballs, bombs, grenades = 0;// counts the ammunitions

	/**
	 * Constructor of the class Player
	 * 
	 * @param p
	 * @param v
	 * @param health
	 * @param healthMax
	 */
	public Player(Vector p, Vector v, double health, double healthMax) {
		super(p, v, health, healthMax);
	}

	@Override
	public Box getBox() {
		return new Box(getPosition(), SIZE, SIZE);
	}

	@Override
	public void preUpdate(Input input) {
		setColliding(false);
	}

	@Override
	public void postUpdate(Input input) {
		super.postUpdate(input);
		// getWorld().setView(position, 8);
	}

	/**
	 * Method which make the player move
	 */
	@Override
	public void update(Input input) {
		super.update(input);
		double maxSpeed = 4.0; // player's max speed
		double delta = input.getDeltaTime();

		// If the user presses the "RIGHT" arrow, he goes to the right
		if (input.getKeyboardButton(KeyEvent.VK_RIGHT).isDown() ) {
			if (getVelocity().getX() < maxSpeed) {
				double increase = 60.0 * input.getDeltaTime();
				double speed = getVelocity().getX() + increase;
				if (speed > maxSpeed)
					speed = maxSpeed;
				setVelocity(new Vector(speed, getVelocity().getY()));
			}
		}
		// If the user presses the "LEFT" arrow, he goes to the left
		if (input.getKeyboardButton(KeyEvent.VK_LEFT).isDown()) {
			if (getVelocity().getX() > -maxSpeed) {
				double decrease = 60.0 * input.getDeltaTime();
				double speed = getVelocity().getX() - decrease;
				if (speed < -maxSpeed)
					speed = -maxSpeed;
				setVelocity(new Vector(speed, getVelocity().getY()));
			}
		}
		// If the user presses the "RIGHT" arrow, he jumps
		if (input.getKeyboardButton(KeyEvent.VK_UP).isPressed() && getColliding()) {
			setVelocity(new Vector(getVelocity().getX(), 7.0));
			setPosition(getPosition().add(getVelocity().mul(delta)));
			Sound.music("up.wav");
		}

		// SPACE : fireball
		if (input.getKeyboardButton(KeyEvent.VK_SPACE).isPressed() && fireballs <= FIREBALLS_MAX) {
			getWorld().register(
					new Fireball(getPosition(), input.getMouseLocation().sub(getPosition()).normalized().mul(5), this));
			Sound.music("fireball.wav");
			++fireballs;
		}
		// B : Airball
		if (input.getKeyboardButton(KeyEvent.VK_B).isPressed()) {
			getWorld().hurt(getBox(), this, Damage.AIR, 1.0, getPosition());
		}
		// E : Lever activation
		if (input.getKeyboardButton(KeyEvent.VK_E).isPressed()) {
			getWorld().hurt(getBox(), this, Damage.ACTIVATION, 1.0, getPosition());
		}
		// G: Throw Grenade
		if (input.getKeyboardButton(KeyEvent.VK_G).isPressed() && grenades <= GRENADES_MAX) {
			getWorld().register(new Grenade(getPosition(),
					input.getMouseLocation().sub(getPosition()).normalized().mul(5), this, 5.0));
			++grenades;
		}
		// LEFT CLICK (mouse) : Throw bomb
		if (input.getMouseButton(1).isPressed() && bombs < BOMBS_MAX) {
			getWorld().register(new Bomb(getPosition(), input.getMouseLocation().sub(getPosition()).normalized().mul(5),
					this, 5.0));
			++bombs;
		}
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);
	}

	@Override
	public void draw(Input input, Output output) {
		Sprite sprite = getSprite("blocker.happy");
		output.drawSprite(sprite, getBox());
	}

	@Override
	public boolean hurt(Actor instigator, Damage type, double amount, Vector location) {
		return super.hurt(instigator, type, amount, location);
	}

	@Override
	public void finDeVie() {
		getWorld().unregister(this);
		getWorld().nextLevel();
	}
}
