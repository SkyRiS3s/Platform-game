package platform.game.level;

import platform.util.Input;
import platform.util.Output;
import platform.util.Sound;
import platform.util.Sprite;
import platform.game.Block;
import platform.game.Limits;
import platform.game.Overlay;
import platform.game.Player;
import platform.game.World;
import platform.util.Box;
import platform.util.Vector;

public class EndLevel extends Level{
	@Override
	public void register(World world) {
		super.register(world);
		// Register a new instance, to restart level automatically
		world.setNextLevel(new Level1());
		world.register(new Block(new Box(new Vector(0, 0), 8, 2), world.getLoader().getSprite("stone.2")));
		Player player = new Player(new Vector(0, 0), new Vector(0, -1), 10, 10);
		world.register(player);
		world.setPlayer(player);
		world.register(new Overlay(player));
		world.register(new Limits(new Box(Vector.ZERO, 80, 90), true));
		world.register(new Block(new Box(new Vector(0.25, 7), 10, 8), world.getLoader().getSprite("Congratulations")));
	}

}
