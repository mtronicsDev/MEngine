package mEngine.gameObjects.components.renderable;

import mEngine.graphics.GraphicsController;
import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.materials.Material;
import mEngine.graphics.renderable.materials.Material3D;
import mEngine.graphics.renderable.textures.StaticTexture;
import mEngine.util.rendering.TextureHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.List;

public class Skybox extends ComponentRenderable3D {

    protected Material3D[] materials = new Material3D[6];
    protected String textureName;
    protected int radius;
    protected boolean displayListsCreated = false;
    protected int[] displayListIndices = new int[6];

    public Skybox(String fileName) {

        textureName = fileName;
        radius = GraphicsController.renderDistance / 2;
        material = new Material3D();

    }

    public void render() {

        if (!displayListsCreated) {

            materials[0] = new Material3D();
            materials[0].setTextureName(textureName + "_bottom");
            materials[0].setTextureFromName();

            materials[1] = new Material3D();
            materials[1].setTextureName(textureName + "_top");
            materials[1].setTextureFromName();

            materials[2] = new Material3D();
            materials[2].setTextureName(textureName + "_back");
            materials[2].setTextureFromName();

            materials[3] = new Material3D();
            materials[3].setTextureName(textureName + "_front");
            materials[3].setTextureFromName();

            materials[4] = new Material3D();
            materials[4].setTextureName(textureName + "_left");
            materials[4].setTextureFromName();

            materials[5] = new Material3D();
            materials[5].setTextureName(textureName + "_right");
            materials[5].setTextureFromName();

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
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, materials[0], Renderer.RENDER_QUADS);

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
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, materials[1], Renderer.RENDER_QUADS);

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
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, materials[2], Renderer.RENDER_QUADS);

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
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, materials[3], Renderer.RENDER_QUADS);

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
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, materials[4], Renderer.RENDER_QUADS);

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
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, materials[5], Renderer.RENDER_QUADS);

            displayListsCreated = true;

        }

        int materialCount = 0;

        for (int displayListIndex : displayListIndices) {

            Renderer.renderObject3D(displayListIndex, parent.position, parent.rotation, materials[materialCount], 1);
            materialCount++;

        }

    }

    @Override
    public void onSave() {

        super.onSave();

    }

    @Override
    public void onLoad() {

        super.onLoad();

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addSkybox(this);

    }
}
