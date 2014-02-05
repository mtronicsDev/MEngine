package mEngine.interactive.gameObjects;

import mEngine.physics.forces.Force;
import mEngine.physics.forces.ForceController;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public abstract class GameObject {

    public Vector3f position;
    public Vector3f rotation;
    public Vector3f percentRotation = new Vector3f();
    public List<Force> forces;

    public GameObject(Vector3f pos, Vector3f rot, float[] forceStrengths) {

        position = pos;
        rotation = rot;

        forces = ForceController.forces;

        forces.add(new Force(new Vector3f(0, 0, -forceStrengths[0]))); //Forward
        forces.add(new Force(new Vector3f(0, 0, forceStrengths[1]))); //Backward
        forces.add(new Force(new Vector3f(forceStrengths[2], 0, 0))); //Right
        forces.add(new Force(new Vector3f(-forceStrengths[3], 0, 0))); //Left
        forces.add(new Force(new Vector3f(0, forceStrengths[4], 0))); //Up
        forces.add(new Force(new Vector3f(0, -forceStrengths[5], 0))); //Down
        forces.add(new Force(new Vector3f(0, forceStrengths[6], 0))); //Jump force

    }

    public GameObject(Vector3f pos, Vector3f rot) {

        position = pos;
        rotation = rot;

        forces = ForceController.forces;

        forces.add(new Force(new Vector3f(0, 0, 0))); //Forward
        forces.add(new Force(new Vector3f(0, 0, 0))); //Backward
        forces.add(new Force(new Vector3f(0, 0, 0))); //Right
        forces.add(new Force(new Vector3f(0, 0, 0))); //Left
        forces.add(new Force(new Vector3f(0, 0, 0))); //Up
        forces.add(new Force(new Vector3f(0, 0, 0))); //Down
        forces.add(new Force(new Vector3f(0, 0, 0))); //Jump force

    }

    public abstract void update();

}
