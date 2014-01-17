package mEngine.interactive.gameObjects;

import mEngine.physics.Force;
import mEngine.physics.ForceController;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public abstract class GameObject {

    public Vector3f position;
    public Vector3f rotation;
    public List<Force> forces;

    public GameObject(Vector3f pos, Vector3f rot) {

        position = pos;
        rotation = rot;

        forces = ForceController.forces;

    }

    public abstract void update();

}
