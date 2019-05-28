package platform.game.level;

import platform.game.And;

import platform.game.Block;
import platform.game.Door;
import platform.game.Exit;
import platform.game.Jumper;
import platform.game.Key;
import platform.game.Lever;
import platform.game.Limits;
import platform.game.Missile;
import platform.game.Mover;
import platform.game.Overlay;
import platform.game.Player;
import platform.util.Sound;
import platform.game.Spike;
import platform.game.Torch;
import platform.game.World;
import platform.util.Box;
import platform.util.Vector;
import platform.game.Heart;

public class Level1 extends Level {
	@Override
	public void register(World world) {
		super.register(world);
		// Register a new instance, to restart level automatically
		world.setNextLevel(new Level1());
		// Create blocks
		// Level 1:
		// Start (center):
		world.register(new Block(new Box(new Vector(0, 0), 8, 2), world.getLoader().getSprite("stone.2")));
		Player player = new Player(new Vector(2, 0), new Vector(0, -1), 10, 10);
		world.register(player);
		world.setPlayer(player);
		world.register(new Overlay(player));
		world.register(new Limits(new Box(Vector.ZERO, 80, 90), true));
		world.register(new Spike(new Vector(-2.5, 1.5)));
		world.register(new Spike(new Vector(-1.5, 1.5)));
		world.register(new Spike(new Vector(-0.5, 1.5)));
		world.register(new Spike(new Vector(0.5, 1.5)));
		// world.register(new Heart(new Vector(1, 1.5), 2));
		world.register(new Block(new Box(new Vector(6, 3.6), 0.5, 3), world.getLoader().getSprite("stone.7")));
		world.register(new Jumper(new Vector(3, 1), 3));
		world.register(new Block(new Box(new Vector(13, -1), 1.5, 5), world.getLoader().getSprite("stone.7")));
		Lever lever1 = new Lever(new Vector(13, 1.8), false);
		world.register(lever1);
		world.register(new Mover(new Box(new Vector(-1, 5), 1, 6), world.getLoader().getSprite("stone.broken.7"),
				new Vector(15, 4), new Vector(15, -4.7), lever1));

		// Droite:
		world.register(new Block(new Box(new Vector(19.5, -9), 8, 0.5), world.getLoader().getSprite("stone.3")));
		// world.register(new Block(new Box(new Vector(22, -8.25), 2, 2),
		// world.getLoader().getSprite("stone.4")));
		world.register(new Spike(new Vector(20, -8.25)));
		world.register(new Spike(new Vector(19, -8.25)));
		world.register(new Spike(new Vector(18, -8.25)));
		Lever lever2 = new Lever(new Vector(22, -6.75), true);
		world.register(lever2);
		world.register(new Mover(new Box(new Vector(22, -8.25), 2, 2), world.getLoader().getSprite("stone.broken.4"),
				new Vector(22, -8.25), new Vector(22, -3), lever2));
		world.register(new Block(new Box(new Vector(25, -0.5), 2, 2), world.getLoader().getSprite("stone.4")));
		Torch torch1 = new Torch(new Vector(25, 2), false);
		world.register(torch1);
		world.register(new Missile(new Vector(25, 8), new Vector(0, -7), null, torch1, 5.0));
		world.register(new Mover(new Box(new Vector(-2, 5), 2, 1), world.getLoader().getSprite("stone.broken.2"),
				new Vector(28, -8.5), new Vector(27, 14), torch1));

		// Haut:
		world.register(new Block(new Box(new Vector(22, 14), 8, 0.5), world.getLoader().getSprite("stone.3")));
		world.register(new Block(new Box(new Vector(19, 16.5), 1.5, 5), world.getLoader().getSprite("stone.7")));
		world.register(new Jumper(new Vector(21, 16), 3));
		world.register(new Block(new Box(new Vector(21, 15.5), 1, 1), world.getLoader().getSprite("stone.4")));
		world.register(new Block(new Box(new Vector(12, 11), 2, 2), world.getLoader().getSprite("stone.4")));
		world.register(new Heart(new Vector(12, 14), 10));
		world.register(new Block(new Box(new Vector(8, 11), 2, 2), world.getLoader().getSprite("stone.4")));
		world.register(new Block(new Box(new Vector(3.5, 8.5), 2, 2), world.getLoader().getSprite("stone.4")));

		// Bas:
		world.register(new Block(new Box(new Vector(13, -13.5), 2, 5), world.getLoader().getSprite("stone.6")));
		world.register(new Block(new Box(new Vector(21.5, -11.25), 13, 0.5), world.getLoader().getSprite("stone.3")));
		Key redKey = new Key(new Vector(23, 15.5), false, "red");
		world.register(redKey);
		world.register(
				new Door(new Box(new Vector(14.5, -11.25), 1, 1), world.getLoader().getSprite("lock.red"), redKey));
		world.register(new Block(new Box(new Vector(18, -15.75), 8, 0.5), world.getLoader().getSprite("stone.3")));
		world.register(new Block(new Box(new Vector(16, -13.1), 0.5, 3.25), world.getLoader().getSprite("stone.7")));
		Key blueKey = new Key(new Vector(-4, -17.5), false, "blue");
		world.register(blueKey);
		world.register(
				new Door(new Box(new Vector(16, -15.15), 0.8, 0.8), world.getLoader().getSprite("lock.blue"), blueKey));
		world.register(new Exit(new Vector(20, -15), new Level2(), null));

		// Gauche
		world.register(new Block(new Box(new Vector(-9, -6), 4, 1), world.getLoader().getSprite("stone.2")));
		world.register(new Heart(new Vector(-8, -5), 10));
		Lever lever3 = new Lever(new Vector(-11, -5), true);
		world.register(lever3);
		world.register(new Mover(new Box(new Vector(-1, 5), 1, 6), world.getLoader().getSprite("stone.broken.7"),
				new Vector(-11.5, -16), new Vector(-11.5, -8.38), lever3));
		world.register(new Block(new Box(new Vector(-8, -13.5), 4, 0.5), world.getLoader().getSprite("stone.3")));
		Key greenKey = new Key(new Vector(8, 12.5), false, "green");
		world.register(greenKey);
		world.register(
				new Door(new Box(new Vector(-10.5, -13.5), 1, 1), world.getLoader().getSprite("lock.green"), greenKey));
		world.register(new Block(new Box(new Vector(-4, -18.5), 16, 0.5), world.getLoader().getSprite("stone.3")));
		world.register(new Block(new Box(new Vector(-5.25, -11.25), 1.5, 5), world.getLoader().getSprite("stone.7")));
		world.register(new Spike(new Vector(-5.25, -8.25)));
		world.register(new Spike(new Vector(-5.15, -8.25)));
		world.register(new Spike(new Vector(-5.3, -8.25)));
		world.register(new Spike(new Vector(-5.1, -8.25)));
		world.register(new Spike(new Vector(-5.35, -8.25)));
		world.register(new Spike(new Vector(-5.05, -8.25)));
		world.register(new Spike(new Vector(-5.4, -8.25)));
		Lever lever4 = new Lever(new Vector(5.5, -15.75), true);
		world.register(lever4);
		// world.register(new Block(new Box(new Vector(-4, -14.5), 6, 0.5),
		// world.getLoader().getSprite("stone.3")));
		world.register(new Mover(new Box(new Vector(5.5, -17.25), 2, 2), world.getLoader().getSprite("stone.broken.4"),
				new Vector(5.5, -17.25), new Vector(5.5, 0), lever4));
	}
}
