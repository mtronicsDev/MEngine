package mEngine.graphics.renderable;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Model {

    List<Vector3f> vertices = new ArrayList<Vector3f>();
    List<Vector3f> normals = new ArrayList<Vector3f>();
    List<Vector2f> uvs = new ArrayList<Vector2f>();
    List<Face> faces = new ArrayList<Face>();

    Texture texture;

    Vector3f position = new Vector3f();
    Vector3f rotation = new Vector3f();

    public Model(String fileName, String textureFileName, Vector3f pos, Vector3f rot) {

        Model model = ModelLoader.loadModelSafely(new File(fileName), new File(textureFileName));
        this.vertices = model.vertices;
        this.normals = model.normals;
        this.uvs = model.uvs;
        this.faces = model.faces;
        this.texture = model.texture;

        position = pos;
        rotation = rot;

    }

    Model(List<Vector3f> vertices, List<Vector3f> normals, List<Vector2f> uvs, List<Face> faces, Texture texture) {

        this.vertices = vertices;
        this.normals = normals;
        this.uvs = uvs;
        this.faces = faces;
        this.texture = texture;

    }

    public void update(Vector3f pos, Vector3f rot) {

        position = pos;
        rotation = rot;

        //Rendering the model

        glPushMatrix();

        /*glRotatef(rotation.x, 1, 0, 0);
        glRotatef(rotation.y, 0, 1, 0);
        glRotatef(rotation.z, 0, 0, 1);*/

        glTranslatef(position.x, position.y, position.z);

        Color.white.bind();
        texture.bind();

        glBegin(GL_TRIANGLES);

        for (Face face : faces) {

            Vector3f n1 = normals.get((int)face.normalIndices.x - 1);
            glNormal3f(n1.x, n1.y, n1.z);

            Vector2f uv1 = uvs.get((int)face.uvIndices.x - 1);
            glTexCoord2f(uv1.x, uv1.y);

            Vector3f v1 = vertices.get((int)face.vertexIndices.x - 1);
            glVertex3f(v1.x, v1.y, v1.z);

            Vector3f n2 = normals.get((int)face.normalIndices.y - 1);
            glNormal3f(n2.x, n2.y, n2.z);

            Vector2f uv2 = uvs.get((int)face.uvIndices.x - 1);
            glTexCoord2f(uv2.x, uv2.y);

            Vector3f v2 = vertices.get((int)face.vertexIndices.y - 1);
            glVertex3f(v2.x, v2.y, v2.z);

            Vector3f n3 = normals.get((int)face.normalIndices.z - 1);
            glNormal3f(n3.x, n3.y, n3.z);

            Vector2f uv3 = uvs.get((int)face.uvIndices.x - 1);
            glTexCoord2f(uv3.x, uv3.y);

            Vector3f v3 = vertices.get((int)face.vertexIndices.z - 1);
            glVertex3f(v3.x, v3.y, v3.z);

        }

        glEnd();

        texture.release();

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

    public float getMass() {

        return 60;

    }

}

class Face {

    public Vector3f vertexIndices = new Vector3f();
    public Vector3f normalIndices = new Vector3f();
    public Vector2f uvIndices = new Vector2f();

    public Face(Vector3f vertexIndices, Vector3f normalIndices, Vector2f uvIndices) {

        this.vertexIndices = vertexIndices;
        this.normalIndices = normalIndices;
        this.uvIndices = uvIndices;

    }

}

class ModelLoader {

    private static Model loadModel(File file, File textureFile) throws IOException {

        Texture texture = null;

        try {

            texture = TextureLoader.getTexture("PNG", new FileInputStream(textureFile));

        } catch(IOException e) {

            e.printStackTrace();
            System.exit(1);

        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        List<Vector3f> vertices = new ArrayList<Vector3f>();
        List<Vector3f> normals = new ArrayList<Vector3f>();
        List<Vector2f> uvs = new ArrayList<Vector2f>();
        List<Face> faces = new ArrayList<Face>();

        while((line = reader.readLine()) != null) {

            if(line.startsWith("v ")){

                //First: "v", Second: x, Third: y, Fourth: z
                float x = Float.valueOf(line.split(" ")[1]);
                float y = Float.valueOf(line.split(" ")[2]);
                float z = Float.valueOf(line.split(" ")[3]);

                vertices.add(new Vector3f(x, y, z));

            }
            else if(line.startsWith("vn ")){

                //First: "vn", Second: x, Third: y, Fourth: z
                float x = Float.valueOf(line.split(" ")[1]);
                float y = Float.valueOf(line.split(" ")[2]);
                float z = Float.valueOf(line.split(" ")[3]);

                normals.add(new Vector3f(x, y, z));

            }
            else if(line.startsWith("vt ")){

                //First: "vt", Second: x, Third: y
                float x = Float.valueOf(line.split(" ")[1]);
                float y = Float.valueOf(line.split(" ")[2]);

                uvs.add(new Vector2f(x, y));

            }
            else if(line.startsWith("f ")) {

                //[0]: "f", [1]:([0]:vertexIndex, [1]:uvIndex, [2]: normalIndex), [...]
                Vector3f vertexIndices = new Vector3f(Float.valueOf(line.split(" ")[1].split("/")[0]),
                        Float.valueOf(line.split(" ")[2].split("/")[0]),
                        Float.valueOf(line.split(" ")[3].split("/")[0]));

                Vector3f normalIndices = new Vector3f(Float.valueOf(line.split(" ")[1].split("/")[2]),
                        Float.valueOf(line.split(" ")[2].split("/")[2]),
                        Float.valueOf(line.split(" ")[3].split("/")[2]));

                Vector2f uvIndices = new Vector2f(Float.valueOf(line.split(" ")[1].split("/")[1]),
                        Float.valueOf(line.split(" ")[2].split("/")[1]));

                faces.add(new Face(vertexIndices, normalIndices, uvIndices));

            }

        }

        reader.close();

        return new Model(vertices, normals, uvs, faces, texture);

    }

    public static Model loadModelSafely(File file, File textureFile) {

        Model model = null;

        try {

            model = loadModel(file, textureFile);

        }
        catch (IOException e) {

            e.printStackTrace();
            System.exit(1);

        }

        return model;

    }


}
