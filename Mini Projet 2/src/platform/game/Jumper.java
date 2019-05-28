package platform.game;
import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Vector;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Sound;

/**
 * Actor which throws up anyone who steps on it
 */
public class Jumper extends Actor{
	private double cooldown;
	private Vector position;
	private final int PRIORITY=100;
	
	/**
	 * Constructor of the class jumper
	 * @param position
	 * @param cooldown
	 */
	public Jumper(Vector position, double cooldown){
		this.position=position;
		this.cooldown=cooldown;
	}
	
	@Override
	public void update(Input input) {
		super.update(input) ;
		cooldown -= input.getDeltaTime() ;
	}
	
	/**
	 * Method which tells what kind of damage the jumper causes to an other actor
	 * Typically, the jumper throws the other actor in the air (i.e Damage.Air)
	 * @param other
	 */
	@Override
	public void interact(Actor other) {
		super.interact(other) ;
		if (cooldown <= 0 && getBox().isColliding(other.getBox())) {
			Vector below = new Vector(position.getX(), position.getY() -1.0) ;
			if (other.hurt(this , Damage.AIR, 10.0, below))
				cooldown = 0.5 ;
			new Sound();
			Sound.music("jump.wav");
		}
	}

	/**
	 * Method which draws the jumper It is either extended or normal in function
	 * of the cooldown
	 * 
	 * @param input
	 * @param output
	 */
	@Override
	public void draw(Input input, Output output){
		Sprite sprite;
		if(cooldown>0){
			sprite=getSprite("jumper.extended");
		}else{
			sprite=getSprite("jumper.normal");
		}
		if(sprite==null){
			super.draw(input, output);
		}else{
			output.drawSprite(sprite , getBox()) ;
		}
	}
	
	@Override
	public Box getBox() {
		return new Box(new Vector(position.getX()-0.5, position.getY()) , new Vector(position.getX()+0.5, position.getY()+1)) ;
	}
	@Override
	public int getPriority(){
		return PRIORITY;
	}
	
}
