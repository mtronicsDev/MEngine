package mEngine.graphics.renderable;

import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
