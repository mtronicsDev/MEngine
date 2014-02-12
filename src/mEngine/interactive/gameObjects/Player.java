package mEngine.interactive.gameObjects;

import mEngine.interactive.controls.Controller;
import org.lwjgl.util.vector.Vector3f;

public class Player extends Entity {

    public Player(Vector3f pos, Vector3f rot, String fileName, float[] forceStrengths, Controller controller, boolean capableOfFlying, boolean collidable, boolean movable) {

        super(pos, rot, fileName, forceStrengths, controller, capableOfFlying, collidable, movable);

    }

    public Player(Vector3f pos, Vector3f rot, String fileName, Controller controller, boolean capableOfFlying, boolean collidable, boolean movable) {

        super(pos, rot, fileName, controller, capableOfFlying, collidable, movable);

    }

}
