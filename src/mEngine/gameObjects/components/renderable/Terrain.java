package mEngine.gameObjects.components.renderable;

import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.Face;
import mEngine.graphics.renderable.Model;
import mEngine.util.math.MathHelper;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Terrain extends ComponentRenderable {

    private Model model;
    private Vector3f size;
    private float[][] heightmap;

    public Terrain(Vector3f size) {

        this.size = size;
        heightmap = new float[(int)size.x][(int)size.z];

    }

    public void setHeight(int x, int z, float height) {

        heightmap[x][z] = (float)MathHelper.clamp(height, 0, 1);

    }

    public void generateMesh() {

        int i = 0;
        int j = 0;

        List<Vector3f> vertices = new ArrayList<Vector3f>();
        List<Face> faces = new ArrayList<Face>();
        List<Vector3f> normals = new ArrayList<Vector3f>();

        for(int x = 0; x < size.x; x++) {

            for(int z = 0; z < size.z; z++) {

                i++;
                j++;

                if(i == 2) {

                    i = 0;
                    faces.add(new Face(
                            new Vector3f(j - 2, j - 1, j),
                            new Vector3f(j - 2, j - 1, j),
                            new Vector3f(j - 2, j - 1, j)
                    ));

                    Face face = faces.get(faces.size() - 1);

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

                vertices.add(new Vector3f(x, heightmap[x][z], z));

            }

        }

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addModel(model);

    }

}
