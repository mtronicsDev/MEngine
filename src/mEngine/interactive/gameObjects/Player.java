package mEngine.interactive.gameObjects;

import mEngine.interactive.controls.Controller;
import org.lwjgl.util.vector.Vector3f;

public class Player extends Entity {

    public Player(Vector3f pos, Vector3f rot, String fileName, float[] forceStrengths, Controller controller, boolean capableOfFlying) { super(pos, rot, fileName, forceStrengths, controller, capableOfFlying); }

    public Player(Vector3f pos, Vector3f rot, String fileName, Controller controller, boolean capableOfFlying) { super(pos, rot, fileName, controller, capableOfFlying); }

}
