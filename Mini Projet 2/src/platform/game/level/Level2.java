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
import platform.game.Slime;
import platform.game.Spike;
import platform.game.Torch;
import platform.game.World;
import platform.util.Box;
import platform.util.Vector;
import platform.game.Heart;

public class Level2 extends Level {
	@Override
	public void register(World world) {
		super.register(world);
		// Register a new instance, to restart level automatically
		world.setNextLevel(new Level2());

		// Level 2:
		// Start:
		world.register(new Block(new Box(new Vector(2, -2), 7, 1), world.getLoader().getSprite("stone.2")));
		Player player = new Player(new Vector(0, -2), new Vector(0, -1), 10, 10);

		world.register(player);
		world.setPlayer(player);
		world.register(new Overlay(player));
		Slime s = new Slime(new Vector(3, -1.5), new Vector(1, 0), 10, 10, 0.7, new Vector(1, -1.5),
				new Vector(4, -1.5));
		world.register(s);
		world.register(new Limits(new Box(Vector.ZERO, 80, 150), true));
		world.register(new Heart(new Vector(2.5, -1), 10));
		world.register(new Block(new Box(new Vector(0, 6), 10, 1.5), world.getLoader().getSprite("stone.broken.5")));
		world.register(new Block(new Box(new Vector(-3.25, -3.75), 1.5, 1.5), world.getLoader().getSprite("stone.4")));
		world.register(new Block(new Box(new Vector(-4.75, -5), 1.5, 1.5), world.getLoader().getSprite("stone.4")));
		world.register(new Block(new Box(new Vector(-6.25, -6.25), 1.5, 1.5), world.getLoader().getSprite("stone.4")));
		world.register(new Block(new Box(new Vector(-7.75, -7.5), 1.5, 1.5), world.getLoader().getSprite("stone.4")));
		world.register(new Spike(new Vector(-6, -5)));
		world.register(new Spike(new Vector(-6.15, -5)));

		// Haut - droite:
		world.register(
				new Block(new Box(new Vector(6.25, 4.25), 2.5, 2.5), world.getLoader().getSprite("stone.broken.4")));
		world.register(
				new Block(new Box(new Vector(8.75, 2.25), 2.5, 2.5), world.getLoader().getSprite("stone.broken.4")));
		world.register(
				new Block(new Box(new Vector(13.25, 1.5), 6.5, 1), world.getLoader().getSprite("stone.broken.5")));
		world.register(new Block(new Box(new Vector(10.5, -2), 1, 6), world.getLoader().getSprite("stone.broken.7")));
		world.register(
				new Block(new Box(new Vector(13.25, -5.5), 6.5, 1), world.getLoader().getSprite("stone.broken.5")));
		world.register(new Block(new Box(new Vector(17, 4), 1, 6), world.getLoader().getSprite("stone.broken.7")));
		world.register(new Jumper(new Vector(15, 2), 3));
		Torch torch1 = new Torch(new Vector(17, 8.5), true);
		world.register(torch1);
		world.register(new Mover(new Box(new Vector(18, -5.5), 2, 2), world.getLoader().getSprite("stone.broken.4"),
				new Vector(18.5, 7), new Vector(18.5, -6), torch1));
		world.register(new Block(new Box(new Vector(16, -1.5), 1, 5), world.getLoader().getSprite("stone.broken.7")));
		Key blueKey = new Key(new Vector(9, 5.75), false, "blue");
		world.register(blueKey);
		world.register(new Spike(new Vector(8, 4)));
		world.register(new Spike(new Vector(9, 4)));
		world.register(
				new Door(new Box(new Vector(16, -4.5), 1, 1), world.getLoader().getSprite("lock.blue"), blueKey));
		world.register(new Exit(new Vector(12.5, -4.5), new Level3(), null));

		// Haut - gauche:
		world.register(
				new Block(new Box(new Vector(-6.25, 4.25), 2.5, 2.5), world.getLoader().getSprite("stone.broken.4")));
		world.register(
				new Block(new Box(new Vector(-8.75, 2.25), 2.5, 2.5), world.getLoader().getSprite("stone.broken.4")));
		world.register(
				new Block(new Box(new Vector(-11.25, 0.25), 2.5, 2.5), world.getLoader().getSprite("stone.broken.4")));
		world.register(
				new Block(new Box(new Vector(-13.75, -2.25), 2.5, 2.5), world.getLoader().getSprite("stone.broken.4")));
		world.register(new Spike(new Vector(-8.5, 4)));
		world.register(new Spike(new Vector(-8.35, 4)));
		world.register(new Spike(new Vector(-11, 2)));
		world.register(new Spike(new Vector(-10.85, 2)));
		world.register(
				new Block(new Box(new Vector(-14.5, -7.5), 1, 7), world.getLoader().getSprite("stone.broken.7")));
		world.register(new Jumper(new Vector(-18, -10.5), 3));
		world.register(new Block(new Box(new Vector(-18, -11), 1, 1), world.getLoader().getSprite("stone.1")));
		world.register(new Jumper(new Vector(-16, -6.5), 3));
		world.register(new Block(new Box(new Vector(-16, -7), 1, 1), world.getLoader().getSprite("stone.1")));
		Key redKey = new Key(new Vector(-0.5, -33), false, "red"); // (-0.5,
																	// -33)
		world.register(redKey);
		world.register(new Door(new Box(new Vector(-14.5, -11.75), 1.2, 1.2), world.getLoader().getSprite("lock.red"),
				redKey));
		world.register(
				new Block(new Box(new Vector(-11.75, -13), 4, 1.5), world.getLoader().getSprite("stone.broken.3")));
		world.register(new Jumper(new Vector(-16, -14), 3));
		world.register(new Block(new Box(new Vector(-16, -14.5), 1, 1), world.getLoader().getSprite("stone.1")));

		// Bas:
		Lever lever1 = new Lever(new Vector(-10.5, -11.75), true);
		world.register(lever1);
		world.register(new Heart(new Vector(-12.75, -11.5), 10));
		world.register(new Mover(new Box(new Vector(-7.75, -11), 2, 3), world.getLoader().getSprite("stone.broken.6"),
				new Vector(-7.75, -11), new Vector(-7.75, -18.5), lever1));
		world.register(new Block(new Box(new Vector(-3.5, -23), 2, 2), world.getLoader().getSprite("stone.4")));
		Slime slime=new Slime(new Vector(-8, -26.5), new Vector(-0.1, 0), 10, 10, 0.5, new Vector(-8-0.5,-26.+0.5), new Vector(-8+0.5,-26+0.5));
		world.register(slime);
		world.register(new Block(new Box(new Vector(-8, -26.5), 4.5, 1.5), world.getLoader().getSprite("stone.2")));
		
		world.register(new Block(new Box(new Vector(-13, -30), 1.5, 1.5), world.getLoader().getSprite("stone.1")));
		Torch torch2 = new Torch(new Vector(-13, -27.5), true);
		world.register(torch2);
		// world.register(new Mover(new Box(new Vector(-15, -30), 2, 2),
		// world.getLoader().getSprite("stone.broken.4"), new Vector(-15,
		// -28.5), new Vector(-15,-35), torch2));
		world.register(new Block(new Box(new Vector(-1, -35), 18, 2), world.getLoader().getSprite("stone.3")));
		Lever lever2 = new Lever(new Vector(7, -31.5), true);
		world.register(lever2);
		world.register(new Mover(new Box(new Vector(7.5, -33), 2, 2), world.getLoader().getSprite("stone.broken.4"),
				new Vector(7.5, -33), new Vector(7.5, -2), lever2));
		world.register(new Jumper(new Vector(-6, -34), 3));
		world.register(new Spike(new Vector(-4.75, -33.5)));
		world.register(new Spike(new Vector(-3.75, -33.5)));
		world.register(new Spike(new Vector(-2.75, -33.5)));
		world.register(new Spike(new Vector(-1.75, -33.5)));
		world.register(new Spike(new Vector(0.75, -33.5)));
		world.register(new Spike(new Vector(1.75, -33.5)));
		world.register(new Spike(new Vector(2.75, -33.5)));
		world.register(new Spike(new Vector(3.75, -33.5)));
		world.register(new Spike(new Vector(4.75, -33.5)));
		world.register(new Spike(new Vector(5.75, -33.5)));
		world.register(new Jumper(new Vector(-0.5, -34), 3));
	}
}
