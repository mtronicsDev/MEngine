package mEngine.graphics.renderable;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Face {

    public Vector3f vertexIndices = new Vector3f();
    public Vector3f normalIndices = new Vector3f();
    public Vector2f uvIndices = new Vector2f();

    public Face(Vector3f vertexIndices, Vector3f normalIndices, Vector2f uvIndices) {

        this.vertexIndices = vertexIndices;
        this.normalIndices = normalIndices;
        this.uvIndices = uvIndices;

    }

}
