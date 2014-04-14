package mEngine.gameObjects.components.renderable;

import mEngine.gameObjects.GameObject;
import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.Face;
import mEngine.graphics.renderable.Model;
import mEngine.util.math.MathHelper;
import mEngine.util.math.vectors.VectorHelper;
import mEngine.util.rendering.TextureHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Terrain extends ComponentRenderable {

    private Model model;
    private Vector3f size;
    private float[][] heightmap;

    public Terrain(Vector3f size) {

        this.size = size;
        heightmap = new float[(int)size.x][(int)size.z];

    }

    @Override
    public void onCreation(GameObject obj) {

        super.onCreation(obj);
        setHeight(0, 0, 0.3f);
        setHeight(0, 3, 0.3f);
        setHeight(0, 45, 0.2f);
        setHeight(0, 56, 0.1f);
        setHeight(0, 97, 0.3f);
        setHeight(1, 32, 0.9f);
        setHeight(1, 23, 0.5f);
        setHeight(1, 57, 0.4f);
        setHeight(1, 56, 0.7f);
        setHeight(1, 89, 0.2f);
        generateMesh();

    }

    public void setHeight(int x, int z, float height) {

        heightmap[x][z] = (float)MathHelper.clamp(height, 0, 1);

    }

    public void generateMesh() {

        List<Vector3f> vertices = new ArrayList<Vector3f>();
        List<Face> faces = new ArrayList<Face>();
        List<Vector3f> normals = new ArrayList<Vector3f>();
        List<Vector2f> uvs = new ArrayList<Vector2f>();

        for (int x = 0; x < size.x; x++) {

            for (int z = 0; z < size.z; z++) {

                vertices.add(new Vector3f(x, size.y * heightmap[x][z], z));
                uvs.add(new Vector2f(0, 0));

                if(x < (size.x - 1) && z < (size.z - 1)) {

                    faces.add(new Face(
                            new Vector3f((x * z) + 1, (x * z) + 2, (x *  z) + (int)size.x + 1),
                            new Vector3f((x * z) + 1, (x *  z) + 2, (x *  z) + (int)size.x + 1),
                            new Vector3f((x * z) + 1, (x *  z) + 2, (x *  z) + (int)size.x + 1)
                    ));

                }

                if((x *  z) > 0 && z > 0) {

                    faces.add(new Face(
                            new Vector3f((x *  z) + 1, (x *  z) + (int)size.x + 1, (x *  z) + (int)size.x),
                            new Vector3f((x *  z) + 1, (x *  z) + (int)size.x + 1, (x *  z) + (int)size.x),
                            new Vector3f((x *  z) + 1, (x *  z) + (int)size.x + 1, (x *  z) + (int)size.x)
                    ));

                }

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

            normals.add(normal);
            normals.add(normal);
            normals.add(normal);

        }

        model = new Model(vertices, normals, uvs, faces, "background");

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addModel(model);

    }

}
