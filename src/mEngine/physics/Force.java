package mEngine.physics;

import org.lwjgl.util.vector.Vector3f;

import java.util.List;

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

class ForceHelper {

    public static Vector3f addForces(List<Force> forces) {

        Vector3f forceSum = new Vector3f();

        for(Force force : forces) {

            forceSum.x += force.getDirectionalForceIfEnabled().x;
            forceSum.y += force.getDirectionalForceIfEnabled().y;
            forceSum.z += force.getDirectionalForceIfEnabled().z;

        }

        return forceSum;

    }

}

class Movement {

    public static Vector3f getAcceleration(Force force, float mass) {

        Vector3f acceleration = new Vector3f();

        acceleration.x = force.direction.x / mass;
        acceleration.y = force.direction.y / mass;
        acceleration.z = force.direction.z / mass;

        return acceleration;

    }

    public static Vector3f getMovedSpace(Vector3f acceleration, Vector3f speed, float timeInSeconds) {

        Vector3f movedSpace = new Vector3f();

        movedSpace.x = 0.5f * acceleration.x * (float)Math.pow(timeInSeconds, 2) + speed.x * timeInSeconds;
        movedSpace.y = 0.5f * acceleration.y * (float)Math.pow(timeInSeconds, 2) + speed.y * timeInSeconds;
        movedSpace.z = 0.5f * acceleration.z * (float)Math.pow(timeInSeconds, 2) + speed.z * timeInSeconds;

        return movedSpace;

    }

}
