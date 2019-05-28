package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Vector;
import platform.util.Output;
import platform.util.Sprite;

/**
 * Base class of all simulated actors, attached to a world.
 */
public abstract class Actor implements Comparable<Actor> {
	/**
	 * world is the World in which the actor will be registered
	 */
	private World world;
	
	
	/**
	 * Damage is an enumeration of all types of damage which are used in this game
	 */
	public enum Damage{
		FIRE, PHYSICAL, AIR, VOID, ACTIVATION, HEAL;
	}
	
	/**
	 * Method which updates an actor
	 * @param input
	 */
	public void update(Input input) {}
	
	/**
	 * Method which draws the actor
	 * @param input
	 * @param output
	 */
	public void draw(Input input , Output output) {}
	public abstract int getPriority();
	@Override
	public int compareTo(Actor other){
		if(this.getPriority()>other.getPriority()){
			return -1;
		}else if(this.getPriority()==other.getPriority()){
			return 0;
		}else{
			return 1;
		}
	}
	
	/**
	 * Method which returns a boolean variable indicating if the actor is solid or not (i.e if the player can stand on it or not, for example)
	 * @return boolean variable (initialized as false) indicating if the actor is solid or not 
	 */
	public boolean isSolid() {
		return false ;
		}
	
	/**
	 * Method which returns the actor's box
	 * @return
	 */
	public Box getBox() {
		return null ;
	}
	public Vector getPosition() {
		Box box = getBox() ;
		if (box == null){
			return null ;
		}
		return box.getCenter() ;
	}
	public Vector getVelocity(){
		return new Vector(0,0);
	}
	public void interact(Actor other) {}
	public void register(World world) {
		this.world = world ;
	}
	public void unregister() {
		world = null ;
	}
	protected World getWorld(){
		return world;
	}
	
	protected Sprite getSprite(String name) {
		return world.getLoader().getSprite(name);
	}
	public void preUpdate(Input input) {
		
	}
	
	public void postUpdate(Input input) {
		
	}
	public boolean hurt(Actor instigator , Damage type, double
		amount , Vector location) {
		return false ;
	}
	
}

