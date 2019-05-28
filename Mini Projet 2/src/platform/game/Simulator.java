package platform.game;

import platform.game.Actor.Damage;
import platform.game.level.Level;
import java.util.ArrayList;
import java.util.List;
import platform.util.Box;

import platform.util.Input;
import platform.util.Loader;
import platform.util.Output;
import platform.util.SortedCollection;
import platform.util.Vector;
import platform.util.View;

import platform.util.Sprite;

/**
 * Basic implementation of world, managing a complete collection of actors.
 */
public class Simulator implements World {

	private Loader loader;
	private Vector currentCenter;
	private double currentRadius;
	private Vector expectedCenter;
	private double expectedRadius;
	private SortedCollection<Actor> actors = new SortedCollection<Actor>();
	protected List<Actor> registered;
	protected List<Actor> unregistered;
	private Level next;
	private boolean transition;
	private Player player;

	/**
	 * Create a new simulator.
	 * 
	 * @param loader
	 *            associated loader, not null
	 * @param args
	 *            level arguments, not null
	 */
	public Simulator(Loader loader, String[] args) {
		if (loader == null) {
			throw new NullPointerException();
		}
		this.loader = loader;
		currentCenter = Vector.ZERO;
		currentRadius = 10.0;
		expectedCenter = currentCenter;
		expectedRadius = currentRadius;
		registered = new ArrayList<Actor>();
		unregistered = new ArrayList<Actor>();
		next=Level.createDefaultLevel();
		transition=true;
	}
	
	/**
	 * Method which sets the camera correctly
	 * @param center is the vector indicating the camera's position
	 * @param radius is a double variable which represents the size of the view (i.e the bigger the raduis, the more you see)
	 */
	@Override
	public void setView(Vector center, double radius) {
		if (center == null)
			throw new NullPointerException();
		if (radius <= 0.0)
			throw new IllegalArgumentException("radius must be positive");
		expectedCenter = center;
		expectedRadius = radius;
	}

	/**
     * Simulate a single step of the simulation.
     * @param input input object to use, not null
     * @param output output object to use, not null
     */
	public void update(Input input, Output output) {	
		double factor = 0.001 ;
		currentCenter = currentCenter.mul(1.0 -
		factor).add(expectedCenter.mul(factor)) ;
		currentRadius = currentRadius * (1.0 - factor) +
		expectedRadius * factor ;
		
		preUpdate(input, output);
		
		View view = new View(input , output) ;
		if(player!=null){
			currentCenter=player.getBox().getCenter();
		}
		view.setTarget(currentCenter , currentRadius) ;

		for (Actor actor : actors){
	    	for (Actor other : actors){
		    	if (actor.getPriority() > other.getPriority()){
		    		Box box=other.getBox();
		    		actor.interact(other) ;
		    	}
	    	}
	    }
		
		for (Actor a : actors){
			a.update(view) ;
		}
		
		
		// Draw everything
		for (Actor a : actors.descending())
		a.draw(view, view) ;
		
		// Add registered actors
		
		for (int i = 0 ; i < registered.size() ; ++i) {
			Actor actor = registered.get(i) ;
			if ( !actors.contains(actor)) {
				actors.add(actor) ;
				actor.register(this);
			}
		}
		registered.clear() ;
		// Remove unregistered actors
		for (int i = 0 ; i < unregistered.size() ; ++i) {
			Actor actor = unregistered.get(i) ;
			actors.remove(actor) ;
			actor.unregister();
		}
		unregistered.clear() ;
		
		if (transition) {
			if (next == null) {
				next = Level.createDefaultLevel() ;
			}
			// si un acteur a appelé setNextLevel , next ne sera pas null :
			Level level = next ;
			transition = false ;
			next = null ;
			actors.clear() ;
			registered.clear() ;
			// tous les anciens acteurs sont désenregistrés ,
			// y compris le Level précédent :
			unregistered.clear() ;
			register(level) ;
		}
		
		postUpdate(input, output);
		
	}

	@Override
	public Loader getLoader() {
		return loader;
	}

	@Override
	public void register(Actor actor) {
		registered.add(actor);
	}

	@Override
	public void unregister(Actor actor) {
		unregistered.add(actor);
	}

	public void preUpdate(Input input, Output output) {
		View view = new View(input, output);
		for (Actor a : actors) {
			a.preUpdate(view);
		}
	}

	public void postUpdate(Input input, Output output) {
		View view = new View(input, output);
		for (Actor a : actors) {
			a.postUpdate(view);
		}
	}
	
	@Override
	public int hurt(Box area, Actor instigator, Damage type, double amount, Vector location) {
		int victims = 0;
		for (Actor actor : actors){
			if (area.isColliding(actor.getBox())){
				if (actor.hurt(instigator, type, amount, location)){
					++victims;
				}
			}
		}
		return victims;
	}

	@Override
	public void nextLevel() {
		transition = true;
	}

	@Override
	public void setNextLevel(Level level) {
		next = level;
	}

	public void transition() {

	}
	@Override
	public void setPlayer(Player player){
		this.player=player;
	}
}

