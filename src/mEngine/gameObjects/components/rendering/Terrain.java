package mEngine.gameObjects.components.rendering;

import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.Model;
import org.lwjgl.util.vector.Vector3f;

public class Terrain extends ComponentRenderable {

    protected Model model;
    protected Vector3f size;
    protected float[][] heightmap;

    public Terrain(Vector3f size) {

        this.size = size;
        heightmap = new float[(int)size.x][(int)size.z];

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addModel(model);

    }

}
