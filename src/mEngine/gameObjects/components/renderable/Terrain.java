package mEngine.gameObjects.components.renderable;

import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.Face;
import mEngine.graphics.renderable.Model;
import mEngine.util.math.MathHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Terrain extends ComponentRenderable {

    private Model model;
    private Vector3f size;
    private List<Vector3f> vertices;
    private List<Face> faces;
    private float[][] heightmap;

    public Terrain(Vector3f size) {

        this.size = size;
        heightmap = new float[(int)size.x][(int)size.z];
        vertices = new ArrayList<Vector3f>();
        faces = new ArrayList<Face>();

    }

    public void setHeight(int x, int z, float height) {

        heightmap[x][z] = (float)MathHelper.clamp(height, 0, 1);

    }

    public void generateMesh() {

        int i = 0;
        int j = 0;

        for(int x = 0; x < size.x; x++) {

            for(int z = 0; z < size.z; x++) {

                i++;
                j++;

                if(i == 2) {

                    i = 0;
                    faces.add(new Face(
                            new Vector3f(j - 2, j - 1, j),
                            new Vector3f(j - 2, j - 1, j),
                            new Vector3f(j - 2, j - 1, j)
                    ));

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
