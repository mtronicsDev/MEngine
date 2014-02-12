package mEngine.interactive.gameObjects;

import mEngine.graphics.renderable.Model;
import mEngine.interactive.controls.Controller;
import org.lwjgl.util.vector.Vector3f;

public class GameObjectRenderable extends GameObjectMovable {

    public GameObjectRenderable(Vector3f pos, Vector3f rot, String fileName, float[] forceStrengths, Controller controller, boolean capableOfFlying, boolean collidable) {

        super(pos, rot, forceStrengths, controller, capableOfFlying, collidable);
        model = new Model(fileName, pos, rot);

        mass = model.getMass();

    }

    public GameObjectRenderable(Vector3f pos, Vector3f rot, String fileName, Controller controller, boolean capableOfFlying, boolean collidable) {

        super(pos, rot, controller, capableOfFlying, collidable);
        model = new Model(fileName, pos, rot);

        mass = model.getMass();

    }

    protected void updateModel() { model.update(position, rotation); }

}
