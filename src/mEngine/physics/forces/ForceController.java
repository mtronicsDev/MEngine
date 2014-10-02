/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.physics.forces;

import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ForceController {

    public static final float UNIT_VECTOR_2D = 0.7068913f;
    public static final float UNIT_VECTOR_3D = 0.57765603f;
    public static Map<String, Force> generalForces = new HashMap<String, Force>();

    public static void addForce(String key, Vector3f direction) {
        generalForces.put(key, new Force(direction));
    }

    public static Vector3f sumForces(Collection<Force> forces) {

        Vector3f forceSum = new Vector3f();

        for (Force force : forces) {

            forceSum = VectorHelper.sumVectors(new Vector3f[]{forceSum, force.getDirectionalForceIfEnabled()});

        }

        return forceSum;

    }

    public static Vector3f getAcceleration(Vector3f forceDirection, float mass) {

        return VectorHelper.divideVectorByFloat(forceDirection, mass);

    }

    public static Vector3f getMovedSpace(Vector3f speed, float timeInSeconds) {

        return VectorHelper.multiplyVectorByFloat(speed, timeInSeconds);

    }

    public static Vector3f getSpeed(Vector3f acceleration, Vector3f startSpeed, float timeInSeconds) {

        return VectorHelper.sumVectors(new Vector3f[]{VectorHelper.multiplyVectorByFloat(acceleration, timeInSeconds), startSpeed});

    }

    public static Vector3f getForceDirectionInReverse(Vector3f acceleration, float mass) {

        return VectorHelper.multiplyVectorByFloat(acceleration, mass);

    }

    public static Vector3f getSpeedInReverse(Vector3f movedSpace, float timeInSeconds) {

        return VectorHelper.divideVectorByFloat(movedSpace, timeInSeconds);

    }

    public static Vector3f getAccelerationInReverse(Vector3f speed, float timeInSeconds) {

        return VectorHelper.divideVectorByFloat(speed, timeInSeconds);

    }

    public static Vector2f getCombinedForces(Vector2f direction) {

        return VectorHelper.multiplyVectorByFloat(direction, UNIT_VECTOR_2D);

    }

    public static Vector3f getCombinedForces(Vector3f direction) {

        return VectorHelper.multiplyVectorByFloat(direction, UNIT_VECTOR_3D);

    }

}
