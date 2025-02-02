package platform.game.level;

import platform.game.World;


import platform.game.Block;
import platform.game.Bomb;
import platform.util.Box;
import platform.util.Vector;
import platform.game.Jumper;
import platform.game.Player;
import platform.game.Slime;
import platform.util.Sound;
import platform.game.Limits;
import platform.game.Missile;
import platform.game.Heart;
import platform.game.Overlay;
import platform.game.Particle;
import platform.game.Spike;
import platform.game.Torch;
import platform.game.Key;
import platform.game.Door;
import platform.game.Exit;
import platform.game.Grenade;
import platform.game.And;
import platform.game.Mover;
import platform.game.Lever;
import platform.game.And;
import platform.game.Destructible;

public class BasicLevel extends Level {

    @Override
    public void register(World world) {
        super.register(world);
        // Register a new instance, to restart level automatically
        // Create blocks
        world.register(new Block(new Box(new Vector(0, 0), 4, 2), world.getLoader().getSprite("stone.broken.2")));
        world.register(new Block(new Box(new Vector(-1.5, 1.5), 1, 1), world.getLoader().getSprite("stone.broken.1")));
        world.register(new Jumper(new Vector(0, 1), 3));
        Player player=new Player(new Vector(2, 3), new Vector(0, -1), 40, 40);
        world.register(player);
        world.setPlayer(player);
        world.register(new Overlay(player));
        world.register(new Limits(new Box(Vector.ZERO, 80, 50), true)) ;
        //world.register(new Heart(new Vector(-1, 2.5), 2));
        world.register(new Spike(new Vector(1, 1.5)));
        Torch torch1=new Torch(new Vector(2, 9), false);
        world.register(torch1);
        world.register(new Door(new Box(new Vector(3, 10.5), 0.5, 4), world.getLoader().getSprite("stone.broken.4"), torch1));
        Lever lever=new Lever(new Vector(-1.5, 4.2), false);
        world.register(lever);
        world.register(new Mover(new Box(new Vector(-2, 5), 2, 1), world.getLoader().getSprite("stone.broken.2"), new Vector(-2,3), new Vector(-2,7), lever));
        world.register(new Block(new Box(new Vector(2, 8), 4, 1), world.getLoader().getSprite("stone.broken.2")));
        //world.register(new Exit(new Vector(0.0, 8.0), new Level1(), null)) ;
        world.register(new Block(new Box(new Vector(7, 9), 3, 1), world.getLoader().getSprite("stone.broken.2")));
        world.register(new Heart(new Vector(7, 10), 10));
        world.register(new Block(new Box(new Vector(14, 8), 4.5, 1), world.getLoader().getSprite("stone.broken.2")));
        world.register(new Jumper(new Vector(14, 8.5), 3));
        world.register(new Spike(new Vector(13, 9)));
        world.register(new Spike(new Vector(15, 9)));
        //Key blue = new Key(new Vector(15, 11), false, "blue") ;
        Key red = new Key(new Vector(19, 13.5), false, "red");
        //world.register(blue);
        world.register(red);
        world.register(new Mover(new Box(new Vector(-2, 5), 4, 1), world.getLoader().getSprite("stone.broken.2"), new Vector(19,12), new Vector(19,3), red));
        //world.register(new Destructible(new Box(new Vector(0, 4), 1, 1), world.getLoader().getSprite("box.double")));
        world.register(new Block(new Box(new Vector(24, 3), 5, 1), world.getLoader().getSprite("stone.broken.2")));
        world.register(new Block(new Box(new Vector(26, 7), 3, 6), world.getLoader().getSprite("stone.broken.2")));
        world.register(new Destructible(new Box(new Vector(25, 3.8), 0.5, 0.5), world.getLoader().getSprite("box.double"), false));
        world.register(new Destructible(new Box(new Vector(25.5, 3.8), 0.5, 0.5), world.getLoader().getSprite("box.double"), false));
        world.register(new Destructible(new Box(new Vector(26, 3.8), 0.6, 0.6), world.getLoader().getSprite("box.double"), false));
        world.register(new Block(new Box(new Vector(30, 2), 10, 1), world.getLoader().getSprite("stone.broken.2")));
        Key blue = new Key(new Vector(31, 5), false, "blue");
        world.register(blue);
        world.register(new Block(new Box(new Vector(32, 4), 2, 1), world.getLoader().getSprite("stone.broken.2")));
        world.register(new Block(new Box(new Vector(33, 7), 1, 10), world.getLoader().getSprite("stone.broken.2")));
        world.register(new Door(new Box(new Vector(31.5, 3), 1, 1), world.getLoader().getSprite("lock.blue"), blue));
        world.register(new Exit(new Vector(33, 3), new Level1(), null)) ;
        Missile m=new Missile(new Vector(6.25,5.25), new Vector(-2,-2), null, null, 5.0);
        world.register(m);

    }
}
