package mEngine.physics.collisions.primitives;

import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

public class Plane {

    public Vector3f position;
    public Vector3f normal;

    public Plane(Vector3f position, Vector3f normal) {

        this.position = position;
        this.normal = normal;

    }

    public Plane(Vector3f position, Vector3f directionVectorA, Vector3f directionVectorB) {

        this.position = position;

        directionVectorA = VectorHelper.normalizeVector(VectorHelper.subtractVectors(directionVectorA, position));
        directionVectorB = VectorHelper.normalizeVector(VectorHelper.subtractVectors(directionVectorB, position));

        normal = VectorHelper.getVectorProduct(directionVectorA, directionVectorB);

    }

}
