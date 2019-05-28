package platform.game;

/**
 * Class that activates a signal if one out of two (or both) other signals are activated
 */
public class Or implements Signal{
	private Signal left; //First signal
	private Signal right; //Second signal

	public Or(Signal left, Signal right) {
		this.left = left;
		this.right=right;
	}
	
	/**
	 * Either left or right must be active for the signal to be active
	 */
	@Override
	public boolean isActive() {
		return left.isActive() || right.isActive();
	}

}