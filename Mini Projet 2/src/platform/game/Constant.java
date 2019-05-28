package platform.game;

/**
 * Class indicating that a signal is always active
 */
public class Constant implements Signal{
	public boolean isActive(){
		return true;
	}
}
