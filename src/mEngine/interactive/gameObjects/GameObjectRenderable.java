package mEngine.interactive.gameObjects;

import mEngine.graphics.renderable.Model;
import mEngine.interactive.controls.Controller;
import org.lwjgl.util.vector.Vector3f;

public class GameObjectRenderable extends GameObjectMovable {

    public Model model;

    public GameObjectRenderable(Vector3f pos, Vector3f rot, String modelFileName, String textureFileName, Controller controller) {

        super(pos, rot, controller);
        model = new Model(modelFileName, textureFileName, pos, rot);

        mass = model.getMass();

    }

    protected void updateModel() { model.update(position, rotation); }

}
