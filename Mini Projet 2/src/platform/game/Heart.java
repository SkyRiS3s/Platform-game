package platform.game;

import platform.util.Input;
import platform.util.Output;
import platform.util.Sound;
import platform.util.Sprite;
import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Vector;

public class Heart extends Actor {
	private final int PRIORITY=600;
	private final double AMOUNT=2.0;
	private Vector position;
	
	/**
	 * Time before the heart reappears
	 */
	private double cooldown;
	
	/**
	 * Counts the time for cooldown
	 */
	private double counter=0;
	
	/**
	 * Constructor of the class Heart
	 * @param position
	 * @param cooldown
	 */
	public Heart(Vector position, double cooldown){
		this.position=position;
		this.cooldown=cooldown;
	}
	
	@Override
	public int getPriority(){
		return PRIORITY;
	}
	
	
	@Override
	public void update(Input input) {
		super.update(input);
		counter-=input.getDeltaTime();
	}
	
	/**
	 * Mehthod indicating that the heart heals as another actor collides with it
	 */
	@Override
	public void interact(Actor other) {
		if (counter <= 0 && getBox().isColliding(other.getBox()) && other.hurt(this , Damage.HEAL, AMOUNT, position)) {
			counter = cooldown;
			Sound.music("heart.wav");
		}
	}
	
	/**
	 * Method which draws the heart if the counter is 0 (or less)
	 */
	@Override
	public void draw(Input input, Output output){
		if(counter<=0){
			Sprite sprite;
			sprite=getSprite("heart.full");
			output.drawSprite(sprite , getBox()) ;
		}
	}
	
	@Override
	public Box getBox(){
		return new Box(position, 0.8, 0.8);
	}
}
