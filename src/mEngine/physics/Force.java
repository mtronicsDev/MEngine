package mEngine.physics;

import org.lwjgl.util.vector.Vector3f;

public class Force {

    public Vector3f direction;
    private boolean enabled = false;

    public Force(Vector3f direction) {

        this.direction = direction;

    }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public Vector3f getDirectionalForceIfEnabled() {

        if(enabled) return direction;

        else return new Vector3f();

    }

    public void setDirection(Vector3f direction) { this.direction = direction; }

}
