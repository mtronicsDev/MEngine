package mEngine.graphics.renderable;

import mEngine.util.ModelHelper;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Model {

    public List<Vector3f> vertices = new ArrayList<Vector3f>();
    public List<Vector3f> normals = new ArrayList<Vector3f>();
    public List<Vector2f> uvs = new ArrayList<Vector2f>();
    public List<Face> faces = new ArrayList<Face>();

    public float mass;

    Texture texture;

    Vector3f position = new Vector3f();
    Vector3f rotation = new Vector3f();

    public Model(String fileName, Vector3f pos, Vector3f rot) {

        Model model = ModelHelper.loadModelSafely(fileName);

        this.vertices = model.vertices;
        this.normals = model.normals;
        this.uvs = model.uvs;
        this.faces = model.faces;
        this.texture = model.texture;
        this.mass = model.getMass();

        Vector3f middle = getMiddle();

        for(int count = 0; count < this.vertices.size(); count ++) this.vertices.set(count, VectorHelper.subtractVectors(this.vertices.get(count), middle));

        position = pos;
        rotation = rot;

        //position = VectorHelper.sumVectors(new Vector3f[] {position, middle});

    }

    public Model(List<Vector3f> vertices, List<Vector3f> normals, List<Vector2f> uvs, List<Face> faces, Texture texture) {

        this.vertices = vertices;
        this.normals = normals;
        this.uvs = uvs;
        this.faces = faces;
        this.texture = texture;

        Vector3f middle = getMiddle();

        for(int count = 0; count < this.vertices.size(); count ++) this.vertices.set(count, VectorHelper.subtractVectors(this.vertices.get(count), middle));

    }

    public void update(Vector3f pos, Vector3f rot) {

        position = pos;
        rotation = rot;

        //Rendering the model

        glPushMatrix();

        glTranslatef(position.x, position.y, position.z);

        //glRotatef(rotation.x, 1, 0, 0);
        //glRotatef(rotation.y, 0, 1, 0);
        //glRotatef(rotation.z, 0, 0, 1);

        texture.bind();
        glBegin(GL_TRIANGLES);

        for (Face face : faces) {

            Vector3f n1 = normals.get((int)face.normalIndices.x);
            glNormal3f(n1.x, n1.y, n1.z);

            Vector2f uv1 = uvs.get((int)face.uvIndices.x);
            glTexCoord2f(uv1.x, 1 - uv1.y);

            Vector3f v1 = vertices.get((int)face.vertexIndices.x);
            glVertex3f(v1.x, v1.y, v1.z);


            Vector3f n2 = normals.get((int)face.normalIndices.y);
            glNormal3f(n2.x, n2.y, n2.z);

            Vector2f uv2 = uvs.get((int)face.uvIndices.y);
            glTexCoord2f(uv2.x, 1 - uv2.y);

            Vector3f v2 = vertices.get((int)face.vertexIndices.y);
            glVertex3f(v2.x, v2.y, v2.z);


            Vector3f n3 = normals.get((int)face.normalIndices.z);
            glNormal3f(n3.x, n3.y, n3.z);

            Vector2f uv3 = uvs.get((int)face.uvIndices.z);
            glTexCoord2f(uv3.x, 1 - uv3.y);

            Vector3f v3 = vertices.get((int)face.vertexIndices.z);
            glVertex3f(v3.x, v3.y, v3.z);

        }

        glEnd();

        glPopMatrix();

    }

    public Vector3f getSize() {

        Vector3f size = new Vector3f();
        Vector3f maxVertexPos = new Vector3f();
        Vector3f minVertexPos = new Vector3f();

        for(Vector3f vertex : vertices) {

            if(vertex.x > maxVertexPos.x) maxVertexPos.x = vertex.x;
            else if(vertex.x < minVertexPos.x) minVertexPos.x = vertex.x;

            if(vertex.y > maxVertexPos.y) maxVertexPos.y = vertex.y;
            else if(vertex.y < minVertexPos.y) minVertexPos.y = vertex.y;

            if(vertex.z > maxVertexPos.z) maxVertexPos.z = vertex.z;
            else if(vertex.z < minVertexPos.z) minVertexPos.z = vertex.z;

        }

        size.x = maxVertexPos.x - minVertexPos.x;
        size.y = maxVertexPos.y - minVertexPos.y;
        size.z = maxVertexPos.z - minVertexPos.z;

        return size;

    }

    public Vector3f[] getExtremeVertexPositions() {

        Vector3f maxVertexPos = new Vector3f();
        Vector3f minVertexPos = new Vector3f();

        for(Vector3f vertex : vertices) {

            if(vertex.x > maxVertexPos.x) maxVertexPos.x = vertex.x;
            else if(vertex.x < minVertexPos.x) minVertexPos.x = vertex.x;

            if(vertex.y > maxVertexPos.y) maxVertexPos.y = vertex.y;
            else if(vertex.y < minVertexPos.y) minVertexPos.y = vertex.y;

            if(vertex.z > maxVertexPos.z) maxVertexPos.z = vertex.z;
            else if(vertex.z < minVertexPos.z) minVertexPos.z = vertex.z;

        }

        return new Vector3f[] {minVertexPos, maxVertexPos};

    }

    public Vector3f getMiddle() {

        Vector3f minVertexPos = getExtremeVertexPositions()[0];

        for(int count = 0; count < vertices.size(); count ++) vertices.set(count, VectorHelper.subtractVectors(vertices.get(count), minVertexPos));

        return VectorHelper.divideVectors(getExtremeVertexPositions()[1], new Vector3f(2, 2, 2));

    }


    public float getMass() {

        return 60;

    }

}

