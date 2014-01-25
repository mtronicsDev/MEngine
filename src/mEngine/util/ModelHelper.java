package mEngine.util;

import mEngine.graphics.renderable.Face;
import mEngine.graphics.renderable.Model;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelHelper {

    private static Model loadModel(File modelFile, File textureFile) throws IOException {

        Texture texture = TextureHelper.loadTexture(textureFile);

        BufferedReader reader = new BufferedReader(new FileReader(modelFile));
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
