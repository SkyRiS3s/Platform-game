package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Loader;
import platform.util.Vector;
import platform.game.level.Level;
/**
 * Signal Interface: represents a signal that can be used to make decisions
 */
public interface Signal {
	public boolean isActive();
}
