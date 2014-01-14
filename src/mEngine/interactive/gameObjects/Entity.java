package mEngine.interactive.gameObjects;

import org.lwjgl.util.vector.Vector3f;

public class Entity extends GameObjectRenderable {

    public Entity(Vector3f pos, Vector3f rot, String modelFileName) { super(pos, rot, modelFileName); }

    public void updateEntity() {

        //Entity update code here

        updatePlayer();

    }

    public void updatePlayer() {}

}
