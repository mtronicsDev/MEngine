package mEngine.interactive.controls;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector2f;

public class Gamepad extends Controller {

    public Gamepad() {}

    public boolean[] getInputKeys() {

        boolean[] movement = new boolean[7];

        return movement;

    }

    public Vector2f getRotation() {

        Vector2f rotation = new Vector2f();

        return rotation;

    }

    //Not needed
    public Vector3f getIntelligentMovement() { return new Vector3f(); }

}
