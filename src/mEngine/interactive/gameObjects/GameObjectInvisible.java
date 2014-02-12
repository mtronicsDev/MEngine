package mEngine.interactive.gameObjects;

import mEngine.interactive.controls.Controller;
import org.lwjgl.util.vector.Vector3f;

public class GameObjectInvisible extends GameObjectMovable {

    Controller controller;

    public GameObjectInvisible(Vector3f pos, Vector3f rot, float[] forceStrengths, Controller controller) {

        super(pos, rot, forceStrengths, controller, true, false, true);
        this.controller = controller;
        capableOfFlying = true;

    }

    public GameObjectInvisible(Vector3f pos, Vector3f rot, Controller controller) {

        super(pos, rot, controller, true, false, true);
        this.controller = controller;

    }

    protected void updateController() { controller.updateObject(this); }

    public void jump() {}
    public void sneak() {}
}
