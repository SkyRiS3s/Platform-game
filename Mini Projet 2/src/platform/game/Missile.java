package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sound;
import platform.util.Sprite;
import platform.util.Vector;
import sun.audio.AudioDataStream;

public class Missile extends Bomb implements Signal {
	private final double RANGE = 2.0;// size of the box of the damage
	private boolean boom = false;
	private double angle;
	private boolean isExploded = false;
	private boolean sound1 = false;// flying sound activated
	private boolean sound2 = false;// impact sound activated
	private AudioDataStream ADS;// attribute used to memorize the sound played
	private Signal signal;// missile is activated on a signal

	/**
	 * Constructor of the call Missile
	 * 
	 * @param p
	 * @param v
	 * @param owner
	 * @param signal
	 * @param duration
	 */
	public Missile(Vector p, Vector v, Actor owner, Signal signal, double duration) {// duration:
																						// time
																						// for
																						// smoke
																						// dissipation
		super(p, v, owner, duration);
		if (getVelocity().getLength() == 0) {
			angle = -Math.PI / 2;
		} else {
			angle = getVelocity().getAngle();
		}
		setSound(true); // pour ne pas commencer le son d'une bombe
		if (signal == null) {
			this.signal = new Constant();
		} else {
			this.signal = signal;
		}
	}

	/**
	 * Method which updates the missile as well as the smoke which is created as
	 * the missile explodes
	 * 
	 */
	@Override
	public void update(Input input) {
		if (isActive()) {
			if (!sound1) {
				ADS = Sound.music("missile1.wav");
				sound1 = true;
			}
			double delta = input.getDeltaTime();
			Vector acceleration;
			if (getVelocity().getLength() != 0) {
				acceleration = Vector.X.rotated(angle).resized(1);
			} else {
				acceleration = new Vector(0, 0);
			}
			setVelocity(getVelocity().add(acceleration.mul(input.getDeltaTime())));
			setPosition(getPosition().add(getVelocity().mul(delta)));
			setVariation(getVariation() - input.getDeltaTime());

			if (getVariation() < 0.0) {
				setVariation(1);
			}
			// for smoke
			setStart(getStart() - input.getDeltaTime());
			if (getGo()) {
				setStart(getDuration());
				setGo(false);
			} else if (!isExploded) {
				setStart(0);
			}
			if (isExploded) {
				setVelocity(new Vector(0, 0));
			}
		}

	}
	
	/**
	 * The missile deals physical damage as it explodes
	 */
	@Override
	public void interact(Actor other) {
		if (other.getBox() != null) {
			if (other.isSolid()) {
				Vector delta = other.getBox().getCollision(getPosition());
				if (delta != null) {
					boom = true;
					if (!sound2) {
						Sound.music("missile2.wav");
						sound2 = true;
					}
					Sound.stop(ADS);
					explode();
					isExploded = true;
				}
			} else if (getBox().isColliding(other.getBox())
					&& other.hurt(this, Damage.PHYSICAL, 0.0000000000000000000001, getPosition())) {
				// setColliding(true);
				boom = true;
				if (!sound2) {
					Sound.music("missile2.wav");
					sound2 = true;
				}
				Sound.stop(ADS);
				explode();
				isExploded = true;
			}
		}
	}
	
	/**
	 * Method which draws the smoke as well as the missile
	 */
	@Override
	public void draw(Input input, Output output) {
		if (boom) {
			if (getGrowth() < 5 && getVariation() == 1) {
				smoke("smoke.gray.1");
				smoke("smoke.yellow.2");
				smoke("smoke.gray.3");
				setGrowth(getGrowth() + 1);
			}
		} else {
			Sprite sprite1 = getSprite("missile.red.1");
			Sprite sprite2 = getSprite("smoke.gray.1");
			output.drawSprite(sprite1, getBox(), angle);
			if (isActive()) {
				output.drawSprite(sprite2, new Box(getPosition()
						.sub(new Vector(getBox().getWidth() * Math.cos(angle), getBox().getWidth() * Math.sin(angle))),
						0.8, 0.8));
			}
		}
	}
	
	/**
	 * Method which makes the missile deal FIRE and AIR damage as it explodes
	 */
	@Override
	public void explode() {
		// new Sound().music("smw_coin.wav");
		// super.explode();
		if (!isExploded) {
			setGo(true);// for smoke
			getWorld().hurt(new Box(getPosition(), RANGE, RANGE), this, Damage.FIRE, 70, getPosition());
			getWorld().hurt(new Box(getPosition(), RANGE, RANGE), this, Damage.AIR, 20, getPosition());
		} else {
			setIsSolid(false);
		}
		if (getStart() < 0) {// for smoke
			getWorld().unregister(this);
		}
		/////////
		// isExploded=true;
	}

	// if not active, then size of is 0: no interaction with the world
	@Override
	public Box getBox() {
		if (!isActive()) {
			return new Box(getPosition(), 0, 0);
		}
		return new Box(getPosition(), 1, 0.5);
	}

	@Override
	public boolean isActive() {
		return signal.isActive();
	}
}
