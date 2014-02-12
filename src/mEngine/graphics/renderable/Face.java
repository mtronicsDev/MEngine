package mEngine.graphics.renderable;

import mEngine.util.vectorHelper.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

public class Face {

    public Vector3f vertexIndices = new Vector3f();
    public Vector3f normalIndices = new Vector3f();
    public Vector3f uvIndices = new Vector3f();

    public Face(Vector3f vertexIndices, Vector3f normalIndices, Vector3f uvIndices) {

        this.vertexIndices = vertexIndices;
        this.vertexIndices = VectorHelper.subtractVectors(vertexIndices, new Vector3f(1, 1, 1));
        this.normalIndices = normalIndices;
        this.normalIndices = VectorHelper.subtractVectors(normalIndices, new Vector3f(1, 1, 1));
        this.uvIndices = uvIndices;
        this.uvIndices = VectorHelper.subtractVectors(uvIndices, new Vector3f(1, 1, 1));

    }

    public Face(Face faceSrc) {

        this.vertexIndices = faceSrc.vertexIndices;
        this.vertexIndices = VectorHelper.subtractVectors(vertexIndices, new Vector3f(1, 1, 1));
        this.normalIndices = faceSrc.normalIndices;
        this.normalIndices = VectorHelper.subtractVectors(normalIndices, new Vector3f(1, 1, 1));
        this.uvIndices = faceSrc.uvIndices;
        this.uvIndices = VectorHelper.subtractVectors(uvIndices, new Vector3f(1, 1, 1));

    }

}
