package mEngine.interactive.gameObjects;

import mEngine.interactive.controls.Controller;
import org.lwjgl.util.vector.Vector3f;

public class Entity extends GameObjectRenderable {

    private Controller controller;

    public Entity(Vector3f pos, Vector3f rot, String modelFileName, Controller controller) {

        super(pos, rot, modelFileName);

        this.controller = controller;

    }

    public void updateEntity() {

        //Entity update code here

        updatePlayer();

    }

    public void updatePlayer() {}

}
