package platform.game;

import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

public class Grenade extends Bomb{
	/**
	 * Actor which the grenade sticks to 
	 */
	private Actor support;
	
	/**
	 * Vector indicating the difference of position between the grenade and the support
	 * @see support
	 */
	private Vector difference;
	
	/**
	 * Constructor of the class Grenade
	 * @param p
	 * @param v
	 * @param owner
	 */
	public Grenade(Vector p, Vector v, Actor owner, double duration){
		super(p, v, owner, duration);
		setSprite("bomb.white");
	}
	
	/**
	 * Updates the grenade
	 */
	@Override
	public void update(Input input) {
		super.update(input);
		if(support!=null){
			if(support.getWorld()==null){
				support=null;
			}
			if (support != null){
				setPosition(support.getPosition().add(difference));
			}
		}
	}
	
	/**
	 * Method which makes the greande stick to another actor (if it is solid)
	 * It determines the difference, the position and the velocity
	 */
	@Override
	public void interact(Actor other) {
		if (other.isSolid()) {
			Vector delta = other.getBox().getCollision(getPosition());
			if (delta != null) {
				difference=getPosition().sub(other.getPosition());
				support=other;
				setPosition(getPosition().add(delta));
				setVelocity(getVelocity().mirrored(delta));
			}
		}
		if (getTimer()<=0) {
			explode();
			setIsExploded(true);
		}
	}
}
