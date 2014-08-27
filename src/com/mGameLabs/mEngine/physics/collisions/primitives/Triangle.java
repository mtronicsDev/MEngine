package com.mGameLabs.mEngine.physics.collisions.primitives;

import com.mGameLabs.mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

public class Triangle extends Plane {

    public Vector3f vertexB;
    public Vector3f vertexC;

    public Triangle(Vector3f vertexA, Vector3f vertexB, Vector3f vertexC, Vector3f normal) {

        super(vertexA, normal);

        this.vertexB = vertexB;
        this.vertexC = vertexC;

    }

    public Triangle(Vector3f vertexA, Vector3f vertexB, Vector3f vertexC) {

        super(vertexA, VectorHelper.subtractVectors(vertexB, vertexA), VectorHelper.subtractVectors(vertexC, vertexA));

        this.vertexB = vertexB;
        this.vertexC = vertexC;

    }

}
