package platform.game;

/**
 * Class that activates a signal if 2 other signal are both active
 */
public class And implements Signal{
	private Signal left; //First Signal	
	private Signal right; //Second Signal
	
	/**
	 * Constructor
	 * @param left
	 * @param right
	 */
	public And(Signal left, Signal right){
		this.left=left;
		this.right=right;
	}
	
	/**
	 * Method indicating that both signals (i.e left and right) must be active
	 */
	@Override
	public boolean isActive() {
		return left.isActive() && right.isActive();
	}

}
