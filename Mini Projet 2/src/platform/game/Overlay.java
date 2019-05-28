package platform.game;

import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Box;
import platform.util.Vector;

public class Overlay extends Actor {
	private final int PRIORITY = 0;

	/**
	 * Player who will have an overlay
	 */
	private Player player;

	/**
	 * Constructor of the class Overlay
	 * 
	 * @param player
	 */
	public Overlay(Player player) {
		this.player = player;
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	@Override
	public void update(Input input) {
		super.update(input);
		if (player.getWorld() == null) {
			getWorld().unregister(this);
		}
	}

	/**
	 * Method which draws the overlay Draws "less" in function of the health of
	 * the player It draws half full hearts to indicate that the player took
	 * some damage
	 */
	@Override
	public void draw(Input input, Output output) {
		double health = 5.0 * player.getHealth() / player.getHealthMax();
		for (int i = 1; i <= 5; ++i) {
			String name;
			if (health >= i) {
				name = "heart.full";
			} else if (health >= i - 0.5) {
				name = "heart.half";
			} else {
				name = "heart.empty";
			}
			Sprite sprite = getSprite(name);
			Box box = new Box(
					new Vector(player.getPosition().getX() + i / 6.0 - 0.5, player.getPosition().getY() + 0.5), 0.15,
					0.15);
			output.drawSprite(sprite, box);
		}
	}

	@Override
	public Box getBox() {
		return player.getBox();
	}
}
