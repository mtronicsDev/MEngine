package mEngine.graphics.renderable;

import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Model {

    List<Vector3f> vertices = new ArrayList<Vector3f>();
    List<Vector3f> normals = new ArrayList<Vector3f>();

    List<Face> faces = new ArrayList<Face>();

    Vector3f position = new Vector3f();
    Vector3f rotation = new Vector3f();

    public Model(String fileName, Vector3f pos, Vector3f rot) {

        Model model = ModelLoader.loadModelSafely(new File(fileName));
        this.vertices = model.vertices;
        this.normals = model.normals;
        this.faces = model.faces;

        position = pos;
        rotation = rot;

    }

    Model(List<Vector3f> vertices, List<Vector3f> normals, List<Face> faces) {

        this.vertices = vertices;
        this.normals = normals;

        this.faces = faces;

    }

    public void update(Vector3f pos, Vector3f rot) {

        position = pos;
        rotation = rot;

        //Rendering the model
        glBegin(GL_TRIANGLES);

        glRotatef(rotation.x, 1, 0, 0);
        glRotatef(rotation.y, 0, 1, 0);
        glRotatef(rotation.z, 0, 0, 1);

        glTranslatef(position.x, position.y, position.z);

        for (Face face : faces) {

            Vector3f n1 = normals.get((int)face.normalIndices.x - 1);
            glNormal3f(n1.x, n1.y, n1.z);

            Vector3f v1 = vertices.get((int)face.vertexIndices.x - 1);
            glVertex3f(v1.x, v1.y, v1.z);

            Vector3f n2 = normals.get((int)face.normalIndices.y - 1);
            glNormal3f(n2.x, n2.y, n2.z);

            Vector3f v2 = vertices.get((int)face.vertexIndices.y - 1);
            glVertex3f(v2.x, v2.y, v2.z);

            Vector3f n3 = normals.get((int)face.normalIndices.z - 1);
            glNormal3f(n3.x, n3.y, n3.z);

            Vector3f v3 = vertices.get((int)face.vertexIndices.z - 1);
            glVertex3f(v3.x, v3.y, v3.z);

        }
        glEnd();

    }

}

class Face {

    public Vector3f vertexIndices = new Vector3f();
    public Vector3f normalIndices = new Vector3f();

    public Face(Vector3f vertexIndices, Vector3f normalIndices) {

        this.vertexIndices = vertexIndices;
        this.normalIndices = normalIndices;

    }

}

class ModelLoader {

    private static Model loadModel(File file) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        List<Vector3f> vertices = new ArrayList<Vector3f>();
        List<Vector3f> normals = new ArrayList<Vector3f>();

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
            else if(line.startsWith("f ")) {

                //[0]: "f", [1]:([0]:vertexIndex, [1]: " ", [2]: normalIndex), [...]
                Vector3f vertexIndices = new Vector3f(Float.valueOf(line.split(" ")[1].split("/")[0]),
                        Float.valueOf(line.split(" ")[2].split("/")[0]),
                        Float.valueOf(line.split(" ")[3].split("/")[0]));

                Vector3f normalIndices = new Vector3f(Float.valueOf(line.split(" ")[1].split("/")[2]),
                        Float.valueOf(line.split(" ")[2].split("/")[2]),
                        Float.valueOf(line.split(" ")[3].split("/")[2]));

                faces.add(new Face(vertexIndices, normalIndices));

            }

        }

        reader.close();

        return new Model(vertices, normals, faces);

    }

    public static Model loadModelSafely(File file) {

        Model model = null;

        try {

            model = loadModel(file);

        }
        catch (IOException e) {

            e.printStackTrace();
            System.exit(1);

        }

        return model;

    }


}
