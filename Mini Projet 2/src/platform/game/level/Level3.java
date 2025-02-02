package platform.game.level;

import platform.game.And;
import platform.game.Block;
import platform.game.Destructible;
import platform.game.Door;
import platform.game.Exit;
import platform.game.Jumper;
import platform.game.Key;
import platform.game.Lever;
import platform.game.Limits;
import platform.game.Mover;
import platform.game.Overlay;
import platform.game.Player;
import platform.game.Slime;
import platform.game.Spike;
import platform.game.Torch;
import platform.game.World;
import platform.util.Box;
import platform.util.Vector;
import platform.game.Heart;

public class Level3 extends Level {
	@Override
	public void register(World world) {
		super.register(world);
		// Register a new instance, to restart level automatically
		world.setNextLevel(new Level3());

		Player player = new Player(new Vector(18, 2), new Vector(0, -1), 10, 10);
		world.register(player);
		world.setPlayer(player);
		world.register(new Overlay(player));
		world.register(new Limits(new Box(Vector.ZERO, 80, 70), true));
		double x = 2, y = 2;
		for (int i = 0; i < 6; i++) {
			world.register(new Block(new Box(new Vector(x, y), 2, 2), world.getLoader().getSprite("stone.broken.4")));
			x += 2;
			y += 2;
		}
		x = 14;
		y = 10;
		for (int i = 0; i < 5; i++) {
			world.register(new Block(new Box(new Vector(x, y), 2, 2), world.getLoader().getSprite("stone.broken.4")));
			Slime s = new Slime(new Vector(x, y + .5), new Vector(-0.2, 0), 3, 3, 0.5, new Vector(x - 0.5, y + 0.5),
					new Vector(x + 0.5, y + 0.5));
			world.register(s);
			x += 2;
			y -= 2;
		}

		x = 4;
		y = 0;
		for (int i = 0; i < 5; i++) {
			world.register(new Block(new Box(new Vector(x, y), 2, 2), world.getLoader().getSprite("stone.broken.4")));
			Slime s = new Slime(new Vector(x, y + .5), new Vector(-0.5, 0), 5, 5, 0.5, new Vector(x - 0.5, y + 0.5),
					new Vector(x + 0.5, y + 0.5));
			world.register(s);
			x += 2;
			y -= 2;
		}

		x = 14;
		y = -10;
		for (int i = 0; i < 5; i++) {
			world.register(new Block(new Box(new Vector(x, y), 2, 2), world.getLoader().getSprite("stone.broken.4")));
			x += 2;
			y += 2;
		}
		world.register(new Heart(new Vector(23.5, -0.25), 10));
		world.register(
				new Block(new Box(new Vector(26.25, -2), 6.5, 2), world.getLoader().getSprite("stone.broken.3")));
		world.register(new Block(new Box(new Vector(28.5, 3), 1.5, 8), world.getLoader().getSprite("stone.broken.7")));
		world.register(new Block(new Box(new Vector(22, 0.5), 1, 1), world.getLoader().getSprite("stone.broken.7")));
		Key blueKey = new Key(new Vector(4, 2), false, "blue");
		world.register(blueKey);
		world.register(
				new Door(new Box(new Vector(22, -0.5), 1, 1), world.getLoader().getSprite("lock.blue"), blueKey));
		world.register(new Jumper(new Vector(26.5, -1), 3));
		Torch torch1 = new Torch(new Vector(2, 4), true);
		world.register(torch1);
		world.register(new Mover(new Box(new Vector(-1, 4), 4, 0.5), world.getLoader().getSprite("stone.broken.4"),
				new Vector(-1, 4), new Vector(-1, -14), torch1));
		world.register(new Block(new Box(new Vector(14, -14), 26, 0.5), world.getLoader().getSprite("stone.3")));
		x = 16;
		y = -13.25;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 10; j++) {
				world.register(new Destructible(new Box(new Vector(x, y), 1, 1),
						world.getLoader().getSprite("box.double"), false));
				x++;
			}
			x = 16;
			y++;
		}

		world.register(new Jumper(new Vector(26.5, -13.75), 3));
		world.register(new Jumper(new Vector(31, -11), 3));
		world.register(new Block(new Box(new Vector(31, -11.5), 1, 1), world.getLoader().getSprite("stone.1")));
		world.register(new Jumper(new Vector(36, -8.25), 3));
		world.register(new Block(new Box(new Vector(36, -8.75), 1, 1), world.getLoader().getSprite("stone.1")));
		world.register(new Jumper(new Vector(31, -4), 3));
		world.register(new Block(new Box(new Vector(31, -4.5), 1, 1), world.getLoader().getSprite("stone.1")));
		world.register(new Jumper(new Vector(36, -1), 3));
		world.register(new Block(new Box(new Vector(36, -1.5), 1, 1), world.getLoader().getSprite("stone.1")));
		world.register(new Jumper(new Vector(31, 4), 3));
		world.register(new Block(new Box(new Vector(31, 3.5), 1, 1), world.getLoader().getSprite("stone.1")));
		Lever lever1 = new Lever(new Vector(28.5, 9), true);
		world.register(lever1);
		world.register(new Mover(new Box(new Vector(28.5, 7.75), 1.5, 1.5),
				world.getLoader().getSprite("stone.broken.4"), new Vector(28.5, 7.75), new Vector(28.5, 20), lever1));
		world.register(
				new Block(new Box(new Vector(32.5, 20), 6.5, 1.5), world.getLoader().getSprite("stone.broken.3")));
		world.register(new Block(new Box(new Vector(30.25, 24), 1, 4), world.getLoader().getSprite("stone.broken.7")));
		Key redKey = new Key(new Vector(17, -10.5), false, "red");
		world.register(redKey);
		world.register(
				new Door(new Box(new Vector(30.25, 21.65), 1, 1), world.getLoader().getSprite("lock.red"), redKey));
		world.register(new Exit(new Vector(32, 21), new EndLevel(), null));
	}
}