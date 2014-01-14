package mEngine.interactive.gameObjects;

import mEngine.core.GameController;
import mEngine.graphics.renderable.Model;
import org.lwjgl.util.vector.Vector3f;

public class GameObjectRenderable extends GameObject {

    Model model;

    public GameObjectRenderable(Vector3f pos, Vector3f rot, String modelFileName) {

        super(pos, rot);
        model = new Model(modelFileName, pos, rot);

    }

    public void update() {

        model.update(position, rotation);

        if(!GameController.isGamePaused) {

            updateEntity();

        }

    }

    public void updateEntity() {};

}
