package mEngine.graphics.renderable;

import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

public class Face {

    public Vector3f vertexIndices = new Vector3f();
    public Vector3f normalIndices = new Vector3f();
    public Vector3f uvIndices = new Vector3f();

    public Face(Vector3f vertexIndices, Vector3f normalIndices, Vector3f uvIndices) {

        this.vertexIndices = VectorHelper.subtractVectorAndFloat(vertexIndices, 1);
        this.normalIndices = VectorHelper.subtractVectorAndFloat(normalIndices, 1);
        this.uvIndices = VectorHelper.subtractVectorAndFloat(uvIndices, 1);

    }

    public Face(Face faceSrc) {

        this.vertexIndices = VectorHelper.subtractVectorAndFloat(faceSrc.vertexIndices, 1);
        this.normalIndices = VectorHelper.subtractVectorAndFloat(faceSrc.normalIndices, 1);
        this.uvIndices = VectorHelper.subtractVectorAndFloat(faceSrc.uvIndices, 1);

    }

}
