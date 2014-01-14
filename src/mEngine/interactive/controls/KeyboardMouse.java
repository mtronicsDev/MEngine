package mEngine.interactive.controls;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector2f;

public class KeyboardMouse extends Controller {

    public KeyboardMouse() {}

    public boolean[] getMovement() {



        return new boolean[1];

    }

    public Vector2f getRotation() {


        return new Vector2f();

    }

    //Not needed
    public Vector3f getIntelligentMovement() { return new Vector3f(); }

}
