package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Loader;
import platform.util.Vector;
import platform.game.level.Level;

/**
 * Class which gets the negation of the signal
 */
public class Not implements Signal {
	private final Signal signal;

	public Not(Signal signal) {
		if (signal == null)
			throw new NullPointerException();
		this.signal = signal;
	}

	@Override
	public boolean isActive() {
		return !signal.isActive();
	}
}