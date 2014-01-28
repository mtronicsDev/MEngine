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

        ForceController.addForce(new Vector3f(0, 0, -5)); //Forward
        ForceController.addForce(new Vector3f(0, 0, 4)); //Backward
        ForceController.addForce(new Vector3f(4, 0, 0)); //Right
        ForceController.addForce(new Vector3f(-4, 0, 0)); //Left
        ForceController.addForce(new Vector3f(0, 4, 0)); //Up
        ForceController.addForce(new Vector3f(0, -4, 0)); //Down
        ForceController.addForce(new Vector3f(0, 10, 0)); //Jump force

    }

    public abstract void update();

}
