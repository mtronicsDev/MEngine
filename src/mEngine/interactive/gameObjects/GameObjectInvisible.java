package mEngine.interactive.gameObjects;

import mEngine.interactive.controls.Controller;
import org.lwjgl.util.vector.Vector3f;

public class GameObjectInvisible extends GameObjectMovable {

    Controller controller;

    public GameObjectInvisible(Vector3f pos, Vector3f rot, float[] forceStrengths, Controller controller, boolean capableOfFlying) {

        super(pos, rot, forceStrengths, controller, capableOfFlying, false);
        this.controller = controller;
        capableOfFlying = true;

    }

    public GameObjectInvisible(Vector3f pos, Vector3f rot, Controller controller, boolean capableOfFlying) {

        super(pos, rot, controller, capableOfFlying, false);
        this.controller = controller;
        capableOfFlying = true;

    }

    protected void updateController() { controller.updateObject(this); }

    public void jump() {}
    public void sneak() {}
}
