package mEngine.interactive.controls;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public abstract class Controller {

    public Controller() {}

    //Controls used while playing
    public abstract boolean[] getInputKeys();

    public abstract Vector2f getRotation();

    //Artificial Intelligence
    public abstract Vector3f getIntelligentMovement();

}
