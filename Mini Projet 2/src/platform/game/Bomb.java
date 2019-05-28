package platform.game;

import platform.game.Actor.Damage;

import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sound;
import platform.util.Sprite;
import platform.util.Vector;
import sun.audio.ContinuousAudioDataStream;

public class Bomb extends Projectile {

	private final int PRIORITY = 666;
	private double bounciness = 0.1;
	private double friction = 0.001;
	private double timer = 4.0;
	private boolean colliding;// used for friction
	private boolean isExploded = false;
	private double start = 0;
	private int growth = 0;// counter
	private boolean go = false;// to activate the smokes
	private Sprite sprite;
	private String name = "bomb";// Sprite's name (by default)

	private double duration;// time for smoke
	private double variation = 0;
	private boolean solid = true;// used in explode() for isSolid()
	private boolean sound = false;// true: play the sound, else don't play
	private final double RANGE = 2.0;// size of the box of the damage
	private ContinuousAudioDataStream CADS;

	private int counter = 0;// counter: not to have infinity loops of explode(); called in hurt()

	/**
	 * Constructor of the class Bomb
	 * 
	 * @param p
	 * @param v
	 * @param owner
	 * @param duration
	 */
	public Bomb(Vector p, Vector v, Actor owner, double duration) {// duration: time for smoke dissipation
		super(p, v, owner);
		this.duration=duration;
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}
	
	public Vector getPosition() {
		return super.getPosition();
	}

	public void setPosition(Vector p) {
		super.setPosition(p);
	}

	public Vector getVelocity() {
		return super.getVelocity();
	}

	public void setVelocity(Vector v) {
		super.setVelocity(v);
	}

	public double getTimer() {
		return timer;
	}

	public boolean getColliding() {
		return colliding;
	}

	public void setColliding(boolean c) {
		colliding = c;
	}

	public void setSprite(String name) {
		this.name = name;
	}

	public void setIsExploded(boolean isExp) {
		isExploded = isExp;
	}

	public void setSound(boolean s) {
		sound = s;
	}

	public void setStart(double start) {
		this.start = start;
	}

	public double getStart() {
		return start;
	}

	public boolean getGo() {
		return go;
	}

	public void setGo(boolean go) {
		this.go = go;
	}

	public double getVariation() {
		return variation;
	}

	public void setVariation(double variation) {
		this.variation = variation;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public void setIsSolid(boolean solid) {
		this.solid = solid;
	}

	public boolean getIsExploded() {
		return isExploded;
	}

	public void setGrowth(int g) {
		growth = g;
	}

	public int getGrowth() {
		return growth;
	}

	public void setTimer(double t) {
		timer = t;
	}
	public boolean isSolid() {
		return solid;
	}

	public void preUpdate(Input input) {
		colliding = false;
	}
	
	/**
	 * Updates the bomb, makes it explode and then dissipates the smoke
	 */
	@Override
	public void update(Input input) {
		super.update(input);
		if (!sound) {
			CADS=Sound.loop("bomb.wav");//to be able to stop it
			sound = true;
		}
		timer -= input.getDeltaTime();
		double delta = input.getDeltaTime();
		Vector acceleration = new Vector(0.0, -9.81);
		setVelocity(getVelocity().add(acceleration.mul(delta)));
		setPosition(getPosition().add(getVelocity().mul(delta)));
		if (colliding) {
			double scale = Math.pow(friction, input.getDeltaTime());
			setVelocity(getVelocity().mul(scale));
		}
		//enables a 1 second interval between two draws
		variation-=input.getDeltaTime();
		if (variation < 0.0) {// 2*Math.pow(0.5, growth)
			variation=1;
		}
		start -= input.getDeltaTime();
		//when bomb activated start the counter start determining the time left to unregister(waiting for the smoke)
		if (go) {
			start = duration;
			Sound.stop(CADS);
			Sound.music("bomb2.wav");
			go=false;
		} else if (!isExploded) {
			start = 0;
		}
		//if exploded, then stops moving
		if (isExploded) {
			setVelocity(new Vector(0, 0));
		}
	}
	
	/**
	 * Draws the bomb
	 * if the timer is less or equal to , then the smoke appears (depending in the growth)
	 */
	@Override
	public void draw(Input input, Output output) {
		if (timer <= 0) {
			if (growth < 5 && variation == 1) {
				smoke("smoke.gray.1");
				smoke("smoke.yellow.2");
				smoke("smoke.gray.3");
				++growth;
			}
		} else {
			sprite = getSprite(name);
			output.drawSprite(sprite, getBox());
		}
	}
	
	/**
	 * Method that registers the smoke in the world
	 * @param name is the name of the sprite used to draw the smoke
	 */
	public void smoke(String name) {
		Particle smoke = new Particle(getPosition(), getWorld().getLoader().getSprite(name), Math.pow(0.5, growth), 0,
				2 * Math.pow(0.5, growth), 1.0);
		getWorld().register(smoke);
	}
	/**
	 * The bomb bounces if it collides with a solid actor 
	 * It explodes as the timer <= 0
	 */
	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (other.isSolid()) {
			Vector delta = other.getBox().getCollision(getPosition());
			if (delta != null) {
				colliding = true;
				setPosition(getPosition().add(delta));
				setVelocity(getVelocity().mirrored(delta));
			}
		}
		if (timer <= 0) {
			explode();
			isExploded = true;
		}
	}
	
	/**
	 * The bomb deals damage of type FIRE and AIR
	 */
	public void explode() {
		if (!isExploded) {
			go=true;// for smoke
			sound=true;//so that it stops playing the loop (tic tac)
			getWorld().hurt(new Box(getPosition(), RANGE, RANGE), this, Damage.FIRE, 50, getPosition());
			getWorld().hurt(new Box(getPosition(), RANGE, RANGE), this, Damage.AIR, 5, getPosition());
		} else {
			solid = false;
		}
		if (start < 0) {// for smoke
			getWorld().unregister(this);
		}
	}

	/**
	 * Method which tells that the bomb explodes if it takes damage of type FIRE
	 */
	@Override
	public boolean hurt(Actor instigator, Damage type, double amount, Vector location) {
		super.hurt(instigator, type, amount, location);
		if (instigator != this) {
			switch (type) {
			case FIRE:
				++counter;
				if (counter == 1) {
					isExploded = true;
					go=true;//for smoke
					sound=true;//so that it stops playing the loop (tic tac)
					timer=-1;//so that the bomb stops waiting the timer
				}
				explode();
				return true;
			default:
				return super.hurt(instigator, type, amount, location);
			}
		}
		return false;
	}
}
