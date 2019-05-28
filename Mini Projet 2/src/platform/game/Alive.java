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


/**
 * Base class for all actors which are considered to be alive
 */
public class Alive extends Actor {
	/**
	 * Vector indicating the actor's position
	 */
	private Vector position;

	/**
	 * Vector indicating the actor's velocity
	 */
	private Vector velocity;

	/**
	 * Double variable indicating the health of the actor Indicates the level of
	 * health
	 */
	private double health;

	/**
	 * Double variable indicating the maximum health of the actor
	 */
	private double healthMax;

	/**
	 * Boolean Variable indicating if the actor is colliding or not.
	 */
	private boolean colliding;

	private final int PRIORITY = 42;

	/**
	 * Constructor of the class Alive
	 * 
	 * @param p
	 *            is the position vector
	 * @param v
	 *            is the velocity vector
	 * @param health
	 *            is a double variable indicating the actor's health
	 * @param healthMax
	 *            is a double variable indicating the actor's maximum health
	 */
	public Alive(Vector p, Vector v, double health, double healthMax) {
		position = p;
		velocity = v;
		this.health = health;
		this.healthMax = healthMax;
	}

	public void setHealth(double h) {
		health = h;
	}

	public double getHealth() {
		return health;
	}

	public void setHealthMax(double h) {
		healthMax = h;
	}

	public double getHealthMax() {
		return healthMax;
	}

	public void setPosition(Vector p) {
		position = p;
	}

	public Vector getPosition() {
		return position;
	}

	public void setVelocity(Vector v) {
		velocity = v;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setColliding(boolean c) {
		colliding = c;
	}

	public boolean getColliding() {
		return colliding;
	}

	@Override
	public Box getBox() {
		return super.getBox();
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	/**
	 * Method which sets colliding as false
	 */
	@Override
	public void preUpdate(Input input) {
		super.preUpdate(input);
		colliding = false;
	}

	/**
	 * Method which updates the actor. It changes its position and velocity over
	 * time. If the actor has no more health, then we start the level again
	 * using the method finDeVie() If he collides, he is slowed down (friction)
	 * 
	 * @see finDeVie()
	 */
	@Override
	public void update(Input input) {
		super.update(input);
		double delta = input.getDeltaTime();
		velocity = velocity.add(getWorld().getGravity().mul(delta));
		position = position.add(velocity.mul(delta));

		if (health <= 0) {
			finDeVie();
		}

		if (colliding) {
			double scale = Math.pow(0.001, input.getDeltaTime());
			velocity = velocity.mul(scale);
		}
	}

	@Override
	public void postUpdate(Input input) {
		super.postUpdate(input);
	}

	@Override
	public void draw(Input input, Output output) {
		super.draw(input, output);
	}

	/**
	 * Interact: if the actor's box is colliding with an other actor, it sets
	 * colliding as true and it stops the player from moving
	 */
	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (other.isSolid() && other.getBox() != null) {
			Vector delta = other.getBox().getCollision(getBox());
			if (delta != null) {
				colliding = true;
				position = position.add(delta);
				if (delta.getX() != 0.0)
					velocity = new Vector(0.0, velocity.getY());
				if (delta.getY() != 0.0)
					velocity = new Vector(velocity.getX(), 0.0);
			}
		}
	}

	/**
	 * Alive takes damage from another actor
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
		case AIR:
			velocity = getPosition().sub(location).resized(amount);
			return true;
		case VOID:
			health = 0;
			return true;
		case HEAL:
			if (health + amount > healthMax) {
				health = healthMax;
			} else {
				health += amount;
			}
			return true;
		case PHYSICAL:
			health -= amount;
			return true;
		case FIRE:
			health -= amount;
			return true;
		default:
			return super.hurt(instigator, type, amount, location);
		}
	}

	/**
	 * Method which unregisters the player from the world.
	 */
	public void finDeVie() {
		getWorld().unregister(this);
	}

}
