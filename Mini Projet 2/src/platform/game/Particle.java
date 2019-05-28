package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

public class Particle extends Actor{
	private Vector position;
	private Sprite sprite;
	
	/**
	 * Double variable indicating how transparent the smoke is
	 */
	private double transparency;
	
	/**
	 * Double variable indficating the time of dissipation of the smoke
	 */
	private double duration;
	private double angle;
	private double size;
	private final int PRIORITY=1337;
	private double time=0.0;//counts the time to respect the duration
	
	/**
	 * Constructor of the class Particle
	 * @param p
	 * @param s
	 * @param transp
	 * @param angle
	 * @param size
	 * @param d
	 */
	public Particle(Vector p, Sprite s, double transp, double angle, double size, double d){
		position=p;
		sprite=s;
		duration=d;
		transparency=transp;
		this.angle=angle;
		this.size=size;
	}
	@Override
	public int getPriority() {
		return PRIORITY;
	}
	@Override
	public Box getBox(){
		return new Box(position, size, size);
	}
	public void setPosition(Vector p){
		position=p;
	}
	public void setSize(double s){//set initial size
		size=s;
	}
	public void setDuration(double d){
		duration=d;
	}
	public void setSprite(Sprite s){
		sprite=s;
	}
	public void setTransparency(double t){
		transparency=t;
	}
	public void setAngle(double angle){
		this.angle=angle;
	}
	
	/**
	 * Updates the smoke in function of time (as time is equal to 0, the smoke disappears)
	 */
	@Override
	public void update(Input input) {
		super.update(input) ;
		time += input.getDeltaTime() ;
		if (time >= duration){
			getWorld().unregister(this) ;
		}
	}
	
	/**
	 * Method that draws the smoke, taking the transparency and the angle into consideration
	 */
	@Override
	public void draw(Input input, Output output){
		if(sprite==null){
			super.draw(input, output);
		}else{
			output.drawSprite(sprite , getBox(), angle, transparency) ;
		}
	}
}
