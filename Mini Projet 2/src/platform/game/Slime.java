package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * Class (Actor) which can be considered as a living enemy for the player
 */
public class Slime extends Alive {
	private final int PRIORITY = 43;
	private final double AMOUNT = 0.001;// damage of the slime
	private double size;
	private boolean right;

	/**
	 * Vectors indicating where the slime goes back and fourth (between which
	 * points)
	 */
	private Vector pointA;
	private Vector pointB;

	/**
	 * Constructor for the class Slime
	 * 
	 * @param p
	 * @param v
	 * @param health
	 * @param healthMax
	 * @param size
	 * @param pointA
	 * @param pointB
	 */
	public Slime(Vector p, Vector v, double health, double healthMax, double size, Vector pointA, Vector pointB) {
		super(p, v, health, healthMax);
		if (getVelocity().getX() > 0) {
			right = true;
		} else {
			right = false;
		}
		this.pointA = pointA;
		this.pointB = pointB;
		this.size = size;
	}

	@Override
	public Box getBox() {
		return new Box(getPosition(), size, size);
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}
	
	/**
	 * Makes the slime move between Vector pointA and Vector  pointB
	 */
	@Override
	public void update(Input input) {
		super.update(input);
		double delta = input.getDeltaTime();
		setVelocity(getVelocity().add(getWorld().getGravity().mul(delta)));
		setPosition(getPosition().add(getVelocity().mul(delta)));
		if (getPosition().getX() < pointA.getX() || getPosition().getX() > pointB.getX()) {
			// velocity=velocity.opposite();
			setVelocity(new Vector(-getVelocity().getX(), getVelocity().getY()));
		}

		if (getHealth() <= 0) {
			finDeVie();
		}
	}

	/**
	 * Deals physical damage to the player
	 */
	@Override
	public void interact(Actor other) {
		super.interact(other);
		setColliding(false);
		if (other instanceof Player && getBox().isColliding(other.getBox())) {
			other.hurt(other, Damage.PHYSICAL, AMOUNT, getPosition());
		} else if (other.isSolid() && other.getBox() != null) {
			Vector delta = other.getBox().getCollision(getBox());
			if (delta != null) {
				setPosition(getPosition().add(delta));
				// so that the slime doesn't pass through solid actors
				setVelocity(
						new Vector(getVelocity().mirrored(delta).getX(), getVelocity().mirrored(delta).getY() / 100));
				right = !right;
			}

		}
	}

	@Override
	public void draw(Input input, Output output) {
		Sprite sprite;
		if (right) {
			sprite = getSprite("slime.right.1");
		} else {
			sprite = getSprite("slime.left.1");
		}
		output.drawSprite(sprite, getBox());
	}
	
	
	/**
	 * Slime takes damage from another actor
	 * @param instigator is the actor which deals damage to the actor Slime
	 * @param Damage indicates the type of damage
	 * @param amount
	 * @param location is position vector
	 */
	@Override
	public boolean hurt(Actor instigator, Damage type, double amount, Vector location) {
		super.hurt(instigator, type, amount, location);
		switch (type) {
		case AIR:
			setVelocity(getPosition().sub(location).resized(amount));
			return true;
		case VOID:
			setHealth(0);
			return true;
		case HEAL:
			if (getHealth() + amount > getHealthMax()) {
				setHealth(getHealthMax());
			} else {
				setHealth(getHealth() + amount);
			}
			return true;
		case PHYSICAL:
			setHealth(getHealth() - amount);
			return true;
		case FIRE:
			setHealth(getHealth() - amount);
			return true;
		default:
			return super.hurt(instigator, type, amount, location);
		}
	}

	/**
	 * Method which unregisters the Slime (which is usefull when he has no more life)
	 */
	public void finDeVie() {
		getWorld().unregister(this);
	}
}
