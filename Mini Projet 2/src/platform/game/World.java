package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Loader;
import platform.util.Vector;
import platform.game.level.Level;
/**
 * Represents an environment populated by actors.
 */
public interface World {

    /** @return associated loader, not null */
    public Loader getLoader();
    public void setView(Vector center , double radius) ;
    
    public void register(Actor actor);
    public void unregister(Actor actor);
    
    public default Vector  getGravity(){
    	return  new Vector(0.0, -9.81);
    }
    
    public int hurt(Box area, Actor instigator , Damage type, double amount , Vector location);
    
    public void nextLevel() ;
    
    public void setNextLevel(Level level) ;
    
    /**
     * Sets the view so that it is always centered where the player is
     */
    public abstract void setPlayer(Player player);
}
