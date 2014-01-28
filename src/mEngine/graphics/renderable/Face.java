package mEngine.graphics.renderable;

import org.lwjgl.util.vector.Vector3f;

public class Face {

    public Vector3f vertexIndices = new Vector3f();
    public Vector3f normalIndices = new Vector3f();
    public Vector3f uvIndices = new Vector3f();

    public Face(Vector3f vertexIndices, Vector3f normalIndices, Vector3f uvIndices) {

        this.vertexIndices = vertexIndices;
        this.normalIndices = normalIndices;
        this.uvIndices = uvIndices;

    }

}
