package mEngine.physics.forces;

import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.*;

public class ForceController {

    public static Map<String, Force> generalForces = new HashMap<String, Force>();

    public static final float X_Y_RATIO = 0.7068913f;

    public static final float X_Y_Z_RATIO = 0.57765603f;

    public static void addForce(String key, Vector3f direction) { generalForces.put(key, new Force(direction)); }

    public static Vector3f sumForces(Collection<Force> forces) {

        Vector3f forceSum = new Vector3f();

        for(Force force : forces) {

            forceSum = VectorHelper.sumVectors(new Vector3f[] {forceSum, force.getDirectionalForceIfEnabled()});

        }

        return forceSum;

    }

    public static Vector3f getAcceleration(Vector3f forceDirection, float mass) {

        Vector3f acceleration;

        acceleration = VectorHelper.divideVectorByFloat(forceDirection, mass);

        return acceleration;

    }

    public static Vector3f getMovedSpace(Vector3f speed, float timeInSeconds) {

        Vector3f movedSpace = new Vector3f();

        movedSpace.x = speed.x * timeInSeconds;
        movedSpace.y = speed.y * timeInSeconds;
        movedSpace.z = speed.z * timeInSeconds;

        return movedSpace;

    }

    public static Vector3f getSpeed(Vector3f acceleration, Vector3f startSpeed, float timeInSeconds) {

        Vector3f speed = new Vector3f();

        speed.x = acceleration.x * timeInSeconds + startSpeed.x;
        speed.y = acceleration.y * timeInSeconds + startSpeed.y;
        speed.z = acceleration.z * timeInSeconds + startSpeed.z;

        return speed;

    }

    public static Vector2f getCombinedForces(Vector2f direction) {

        Vector2f combinedForce;

        combinedForce = VectorHelper.multiplyVectorByFloat(direction, X_Y_RATIO);

        return combinedForce;

    }

    public static Vector3f getCombinedForces(Vector3f direction) {

        Vector3f combinedForce;

        combinedForce = VectorHelper.multiplyVectorByFloat(direction, X_Y_Z_RATIO);

        return combinedForce;

    }

}
