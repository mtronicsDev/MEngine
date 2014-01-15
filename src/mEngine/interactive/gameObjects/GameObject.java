package mEngine.interactive.gameObjects;

import org.lwjgl.util.vector.Vector3f;

public abstract class GameObject {

    public Vector3f position;
    public Vector3f rotation;

    public GameObject(Vector3f pos, Vector3f rot) {

        position = pos;
        rotation = rot;

    }

    public abstract void update();

}
