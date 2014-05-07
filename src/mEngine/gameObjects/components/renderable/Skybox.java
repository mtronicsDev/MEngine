package mEngine.gameObjects.components.renderable;

import mEngine.graphics.GraphicsController;
import mEngine.graphics.Renderer;
import mEngine.util.rendering.TextureHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Skybox extends ComponentRenderable {

    protected Texture[] textures = new Texture[6];
    protected String textureName;
    protected int radius;
    protected boolean displayListsCreated = false;
    protected int[] displayListIndices = new int[6];

    public Skybox(String fileName) {

        textureName = fileName;
        radius = GraphicsController.renderDistance / 2;

    }

    public void render() {

        if (textures[0] == null) {
            textures[0] = TextureHelper.getTexture(textureName + "_bottom");
            textures[1] = TextureHelper.getTexture(textureName + "_top");
            textures[2] = TextureHelper.getTexture(textureName + "_back");
            textures[3] = TextureHelper.getTexture(textureName + "_front");
            textures[4] = TextureHelper.getTexture(textureName + "_left");
            textures[5] = TextureHelper.getTexture(textureName + "_right");
        }

        if (!displayListsCreated) {

            List<Vector3f> renderVertices = new ArrayList<Vector3f>();
            List<Vector3f> renderNormals = new ArrayList<Vector3f>();
            List<Vector2f> renderUVs = new ArrayList<Vector2f>();

            //bottom
            renderUVs.add(new Vector2f(0, 1));
            renderNormals.add(new Vector3f(0, 1, 0));
            renderVertices.add(new Vector3f(-radius, -radius, radius));
            renderUVs.add(new Vector2f(0, 0));
            renderNormals.add(new Vector3f(0, 1, 0));
            renderVertices.add(new Vector3f(radius, -radius, radius));
            renderUVs.add(new Vector2f(1, 0));
            renderNormals.add(new Vector3f(0, 1, 0));
            renderVertices.add(new Vector3f(radius, -radius, -radius));
            renderUVs.add(new Vector2f(1, 1));
            renderNormals.add(new Vector3f(0, 1, 0));
            renderVertices.add(new Vector3f(-radius, -radius, -radius));

            displayListIndices[0] = Renderer.displayListCounter;
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, textures[0], Renderer.RENDER_QUADS);

            renderVertices = new ArrayList<Vector3f>();
            renderNormals = new ArrayList<Vector3f>();
            renderUVs = new ArrayList<Vector2f>();

            //top
            renderUVs.add(new Vector2f(1, 0));
            renderNormals.add(new Vector3f(0, -1, 0));
            renderVertices.add(new Vector3f(-radius, radius, -radius));
            renderUVs.add(new Vector2f(1, 1));
            renderNormals.add(new Vector3f(0, -1, 0));
            renderVertices.add(new Vector3f(radius, radius, -radius));
            renderUVs.add(new Vector2f(0, 1));
            renderNormals.add(new Vector3f(0, -1, 0));
            renderVertices.add(new Vector3f(radius, radius, radius));
            renderUVs.add(new Vector2f(0, 0));
            renderNormals.add(new Vector3f(0, -1, 0));
            renderVertices.add(new Vector3f(-radius, radius, radius));

            displayListIndices[1] = Renderer.displayListCounter;
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, textures[1], Renderer.RENDER_QUADS);

            renderVertices = new ArrayList<Vector3f>();
            renderNormals = new ArrayList<Vector3f>();
            renderUVs = new ArrayList<Vector2f>();

            //back
            renderUVs.add(new Vector2f(0, 0));
            renderNormals.add(new Vector3f(0, 0, 1));
            renderVertices.add(new Vector3f(-radius, radius, radius));
            renderUVs.add(new Vector2f(1, 0));
            renderNormals.add(new Vector3f(0, 0, 1));
            renderVertices.add(new Vector3f(radius, radius, radius));
            renderUVs.add(new Vector2f(1, 1));
            renderNormals.add(new Vector3f(0, 0, 1));
            renderVertices.add(new Vector3f(radius, -radius, radius));
            renderUVs.add(new Vector2f(0, 1));
            renderNormals.add(new Vector3f(0, 0, 1));
            renderVertices.add(new Vector3f(-radius, -radius, radius));

            displayListIndices[2] = Renderer.displayListCounter;
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, textures[2], Renderer.RENDER_QUADS);

            renderVertices = new ArrayList<Vector3f>();
            renderNormals = new ArrayList<Vector3f>();
            renderUVs = new ArrayList<Vector2f>();

            //front
            renderUVs.add(new Vector2f(1, 1));
            renderNormals.add(new Vector3f(0, 0, -1));
            renderVertices.add(new Vector3f(-radius, -radius, -radius));
            renderUVs.add(new Vector2f(0, 1));
            renderNormals.add(new Vector3f(0, 0, -1));
            renderVertices.add(new Vector3f(radius, -radius, -radius));
            renderUVs.add(new Vector2f(0, 0));
            renderNormals.add(new Vector3f(0, 0, -1));
            renderVertices.add(new Vector3f(radius, radius, -radius));
            renderUVs.add(new Vector2f(1, 0));
            renderNormals.add(new Vector3f(0, 0, -1));
            renderVertices.add(new Vector3f(-radius, radius, -radius));

            displayListIndices[3] = Renderer.displayListCounter;
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, textures[3], Renderer.RENDER_QUADS);

            renderVertices = new ArrayList<Vector3f>();
            renderNormals = new ArrayList<Vector3f>();
            renderUVs = new ArrayList<Vector2f>();

            //left
            renderUVs.add(new Vector2f(1, 1));
            renderNormals.add(new Vector3f(1, 0, 0));
            renderVertices.add(new Vector3f(-radius, -radius, radius));
            renderUVs.add(new Vector2f(0, 1));
            renderNormals.add(new Vector3f(1, 0, 0));
            renderVertices.add(new Vector3f(-radius, -radius, -radius));
            renderUVs.add(new Vector2f(0, 0));
            renderNormals.add(new Vector3f(1, 0, 0));
            renderVertices.add(new Vector3f(-radius, radius, -radius));
            renderUVs.add(new Vector2f(1, 0));
            renderNormals.add(new Vector3f(1, 0, 0));
            renderVertices.add(new Vector3f(-radius, radius, radius));

            displayListIndices[4] = Renderer.displayListCounter;
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, textures[4], Renderer.RENDER_QUADS);

            renderVertices = new ArrayList<Vector3f>();
            renderNormals = new ArrayList<Vector3f>();
            renderUVs = new ArrayList<Vector2f>();

            //right
            renderUVs.add(new Vector2f(1, 1));
            renderNormals.add(new Vector3f(-1, 0, 0));
            renderVertices.add(new Vector3f(radius, -radius, -radius));
            renderUVs.add(new Vector2f(0, 1));
            renderNormals.add(new Vector3f(-1, 0, 0));
            renderVertices.add(new Vector3f(radius, -radius, radius));
            renderUVs.add(new Vector2f(0, 0));
            renderNormals.add(new Vector3f(-1, 0, 0));
            renderVertices.add(new Vector3f(radius, radius, radius));
            renderUVs.add(new Vector2f(1, 0));
            renderNormals.add(new Vector3f(-1, 0, 0));
            renderVertices.add(new Vector3f(radius, radius, -radius));

            displayListIndices[5] = Renderer.displayListCounter;
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, textures[5], Renderer.RENDER_QUADS);

            displayListsCreated = true;


        }

        for (int displayListIndex : displayListIndices) {

            Renderer.renderObject3D(displayListIndex, parent.position, new Vector3f(), true, 1);

        }

    }

    @Override
    public void onSave() {

        super.onSave();
        textures = null;

    }

    @Override
    public void onLoad() {

        super.onLoad();
        textures = new Texture[6];

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addSkybox(this);

    }
}
