package mEngine.interactive.controls;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public abstract class Controller {

    public Controller() {}

    public abstract boolean[] getMovement();

    public abstract Vector2f getRotation();

    public abstract Vector3f getIntelligentMovement();

}
