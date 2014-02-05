package mEngine.physics.forces;

import org.lwjgl.util.vector.Vector3f;

public class Force {

    public Vector3f direction;
    public boolean enabled;

    public Force(Vector3f direction) {

        this.direction = direction;
        enabled = false;

    }

    public Vector3f getDirectionalForceIfEnabled() {

        if(enabled) return direction;

        else return new Vector3f();

    }

}