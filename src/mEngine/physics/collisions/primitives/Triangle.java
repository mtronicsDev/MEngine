package mEngine.physics.collisions.primitives;

import org.lwjgl.util.vector.Vector3f;

public class Triangle extends Plane {

    public Vector3f directionVectorA;
    public Vector3f directionVectorB;

    public Triangle(Vector3f position, Vector3f normal, Vector3f directionVectorA, Vector3f directionVectorB) {

        super(position, normal);

        this.directionVectorA = directionVectorA;
        this.directionVectorB = directionVectorB;

    }

    public Triangle(Vector3f position, Vector3f directionVectorA, Vector3f directionVectorB) {

        super(position, directionVectorA, directionVectorB);

        this.directionVectorA = directionVectorA;
        this.directionVectorB = directionVectorB;

    }

}
