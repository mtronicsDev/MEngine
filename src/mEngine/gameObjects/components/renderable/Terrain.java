package mEngine.gameObjects.components.renderable;

import mEngine.gameObjects.GameObject;
import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.models.Face;
import mEngine.graphics.renderable.models.Model;
import mEngine.util.math.MathHelper;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Terrain extends ComponentRenderable3D {

    public Model model;
    private Vector3f size;
    private float[][] heightmap;

    public Terrain(Vector3f size, boolean structured) {

        this.size = size;
        heightmap = new float[(int) size.x][(int) size.z];

        Random rand = new Random();

        if (structured) {

            for (int x = 0; x < size.x; x++) {

                for (int z = 0; z < size.z; z++) {

                    setHeight(x, z, (float) rand.nextInt(11) / 1000);

                }

            }

        } else {

            for (int x = 0; x < size.x; x++) {

                for (int z = 0; z < size.z; z++) {

                    setHeight(x, z, 0);

                }

            }

        }

    }

    public Terrain(float[][] heightmap, float maxHeight) {

        this.heightmap = heightmap;
        size = new Vector3f(heightmap.length, maxHeight, heightmap[0].length);

    }

    @Override
    public void onCreation(GameObject obj) {

        super.onCreation(obj);
        generateMesh();

    }

    public void setHeight(int x, int z, float height) {

        heightmap[x][z] = (float) MathHelper.clamp(height, 0, 1);

    }

    public void generateMesh() {

        List<Vector3f> vertices = new ArrayList<Vector3f>();
        List<Face> faces = new ArrayList<Face>();
        List<Vector3f> normals = new ArrayList<Vector3f>();
        List<Vector2f> uvs = new ArrayList<Vector2f>();

        int i = 0; //Counts all vertices

        for (int x = 0; x < size.x; x++) {

            for (int z = 0; z < size.z; z++) {

                vertices.add(new Vector3f(x, size.y * heightmap[x][z], z));
                uvs.add(new Vector2f(0, 0));

                if (x < (size.x - 1) && z < (size.z - 1)) {

                    faces.add(new Face(
                            new Vector3f(i + 1, i + 2, i + (int) size.x + 1),
                            new Vector3f((x * z) + x + 1, (x * z) + x + 2, (x * z) + x + (int) size.x + 1),
                            new Vector3f((x * z) + x + 1, (x * z) + x + 2, (x * z) + x + (int) size.x + 1)
                    ));

                }

                if (z < size.z && i < 9900) { //Don't ask me about this, it seems to be random

                    faces.add(new Face(
                            new Vector3f(i + 1, i + (int) size.x + 1, i + (int) size.x),
                            new Vector3f((x * z) + 1, (x * z) + (int) size.x + 1, (x * z) + (int) size.x),
                            new Vector3f((x * z) + 1, (x * z) + (int) size.x + 1, (x * z) + (int) size.x)
                    ));

                }

                i++;

            }

        }


        for (Face face : faces) {

            Vector3f vertexA = vertices.get((int) face.vertexIndices.x);
            Vector3f vertexB = vertices.get((int) face.vertexIndices.y);
            Vector3f vertexC = vertices.get((int) face.vertexIndices.z);

            Vector3f directionVectorA = VectorHelper.subtractVectors(vertexB, vertexA);
            Vector3f directionVectorB = VectorHelper.subtractVectors(vertexC, vertexA);

            Vector3f normal = VectorHelper.getVectorProduct(directionVectorA, directionVectorB);
            normal = VectorHelper.normalizeVector(normal);

            if (VectorHelper.getScalarProduct(normal, new Vector3f(0, 1, 0)) < 0)
                normal = VectorHelper.negateVector(normal);

            normals.add(normal);
            normals.add(normal);
            normals.add(normal);

        }

        model = new Model(vertices, normals, uvs, faces, this, true);

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addModel(model);

    }

}
