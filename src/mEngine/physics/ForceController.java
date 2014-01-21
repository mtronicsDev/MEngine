package mEngine.physics;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ForceController {

    public static List<Force> forces = new ArrayList<Force>();

    public static final float X_Y_RATIO = calculateXYRatio();

    public static final float X_Y_Z_RATIO = calculateXYZRatio();

    private static float calculateXYRatio() {

        float xYRatio = 0;

        for(float x = 0; x <= 1; x += 0.00001f) {

            float y = (float)Math.sqrt(1 - Math.pow(x, 2));

            if(x == y) xYRatio = x;

            else {

                for(float count = -0.00001f; count <= 0.00001f; count += 0.00000001f) {

                    y += count;

                    if(x == y) xYRatio = x;

                }

            }

        }

        return xYRatio;

    }

    private static float calculateXYZRatio() {

        float xYZRatio = 0;

        for(float x = 0; x <= 1; x += 0.00001f) {

            for(float y = 0; y <= 1; y += 0.00001f) {

                float dif = (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

                float z = (float)Math.sqrt(1 - Math.pow(dif, 2));

                if(x == y && y == z) xYZRatio = x;

                else {

                    for(float yAlternative = -0.00001f; yAlternative <= 0.00001f; yAlternative += 0.0000001f) {

                        float difAlternative = (float)Math.sqrt(Math.pow(x, 2) + Math.pow(yAlternative, 2));

                        float zAlternative = (float)Math.sqrt(1 - Math.pow(difAlternative, 2));

                        if(x == yAlternative && yAlternative == zAlternative) xYZRatio = x;

                        else {

                            for(float count = -0.00001f; count <= 0.00001f; count += 0.0000001f) {

                                zAlternative += count;

                                if(x == yAlternative && yAlternative == zAlternative) xYZRatio = x;

                            }

                        }

                    }

                }

            }

        }

        return xYZRatio;

    }

    public static void addForce(Vector3f direction) { forces.add(new Force(direction)); }

    public static Vector3f sumForces(List<Force> forces) {

        Vector3f forceSum = new Vector3f();

        for(Force force : forces) {

            forceSum.x += force.getDirectionalForceIfEnabled().x;
            forceSum.y += force.getDirectionalForceIfEnabled().y;
            forceSum.z += force.getDirectionalForceIfEnabled().z;

        }

        return forceSum;

    }

    public static Vector3f getAcceleration(Vector3f forceDirection, float mass) {

        Vector3f acceleration = new Vector3f();

        acceleration.x = forceDirection.x / mass;
        acceleration.y = forceDirection.y / mass;
        acceleration.z = forceDirection.z / mass;

        return acceleration;

    }

    public static Vector3f getMovedSpace(Vector3f acceleration, Vector3f speed, float timeInSeconds) {

        Vector3f movedSpace = new Vector3f();

        movedSpace.x = 0.5f * acceleration.x * (float)Math.pow(timeInSeconds, 2) + speed.x * timeInSeconds;
        movedSpace.y = 0.5f * acceleration.y * (float)Math.pow(timeInSeconds, 2) + speed.y * timeInSeconds;
        movedSpace.z = 0.5f * acceleration.z * (float)Math.pow(timeInSeconds, 2) + speed.z * timeInSeconds;

        return movedSpace;

    }

    public static Vector3f getSpeed(Vector3f acceleration, Vector3f startSpeed, float timeInSeconds) {

        Vector3f speed = new Vector3f();

        speed.x = acceleration.x * timeInSeconds + startSpeed.x;
        speed.y = acceleration.y * timeInSeconds + startSpeed.y;
        speed.z = acceleration.z * timeInSeconds + startSpeed.z;

        return speed;

    }

    public static Vector2f getCombinedForces(float directionA, float directionB) {

        Vector2f combinedForce = new Vector2f();

        combinedForce.x = directionA * X_Y_RATIO;
        combinedForce.y = directionB * X_Y_RATIO;

        return combinedForce;

    }

    public static Vector3f getCombinedForces(float directionA, float directionB, float directionC) {

        Vector3f combinedForce = new Vector3f();

        combinedForce.x = directionA * X_Y_Z_RATIO;
        combinedForce.y = directionB * X_Y_Z_RATIO;
        combinedForce.z = directionC * X_Y_Z_RATIO;

        return combinedForce;

    }

}
