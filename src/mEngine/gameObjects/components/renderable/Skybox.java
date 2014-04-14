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

        glPushMatrix();

        glTranslatef(parent.position.x, parent.position.y, parent.position.z);

        List<Vector3f> renderVertices = new ArrayList<Vector3f>();
        List<Vector2f> renderUVs = new ArrayList<Vector2f>();

        //bottom
        renderUVs.add(new Vector2f(0, 1));
        renderVertices.add(new Vector3f(-radius, -radius, radius));
        renderUVs.add(new Vector2f(0, 0));
        renderVertices.add(new Vector3f(radius, -radius, radius));
        renderUVs.add(new Vector2f(1, 0));
        renderVertices.add(new Vector3f(radius, -radius, -radius));
        renderUVs.add(new Vector2f(1, 1));
        renderVertices.add(new Vector3f(-radius, -radius, -radius));

        Renderer.renderObject3D(renderVertices, renderUVs, textures[0], Renderer.RENDER_QUADS);
        renderVertices = new ArrayList<Vector3f>();
        renderUVs = new ArrayList<Vector2f>();

        //top
        renderUVs.add(new Vector2f(1, 0));
        renderVertices.add(new Vector3f(-radius, radius, -radius));
        renderUVs.add(new Vector2f(1, 1));
        renderVertices.add(new Vector3f(radius, radius, -radius));
        renderUVs.add(new Vector2f(0, 1));
        renderVertices.add(new Vector3f(radius, radius, radius));
        renderUVs.add(new Vector2f(0, 0));
        renderVertices.add(new Vector3f(-radius, radius, radius));

        Renderer.renderObject3D(renderVertices, renderUVs, textures[1], Renderer.RENDER_QUADS);
        renderVertices = new ArrayList<Vector3f>();
        renderUVs = new ArrayList<Vector2f>();

        //back
        renderUVs.add(new Vector2f(0, 0));
        renderVertices.add(new Vector3f(-radius, radius, radius));
        renderUVs.add(new Vector2f(1, 0));
        renderVertices.add(new Vector3f(radius, radius, radius));
        renderUVs.add(new Vector2f(1, 1));
        renderVertices.add(new Vector3f(radius, -radius, radius));
        renderUVs.add(new Vector2f(0, 1));
        renderVertices.add(new Vector3f(-radius, -radius, radius));

        Renderer.renderObject3D(renderVertices, renderUVs, textures[2], Renderer.RENDER_QUADS);
        renderVertices = new ArrayList<Vector3f>();
        renderUVs = new ArrayList<Vector2f>();

        //front
        renderUVs.add(new Vector2f(1, 1));
        renderVertices.add(new Vector3f(-radius, -radius, -radius));
        renderUVs.add(new Vector2f(0, 1));
        renderVertices.add(new Vector3f(radius, -radius, -radius));
        renderUVs.add(new Vector2f(0, 0));
        renderVertices.add(new Vector3f(radius, radius, -radius));
        renderUVs.add(new Vector2f(1, 0));
        renderVertices.add(new Vector3f(-radius, radius, -radius));

        Renderer.renderObject3D(renderVertices, renderUVs, textures[3], Renderer.RENDER_QUADS);
        renderVertices = new ArrayList<Vector3f>();
        renderUVs = new ArrayList<Vector2f>();

        //left
        renderUVs.add(new Vector2f(1, 1));
        renderVertices.add(new Vector3f(-radius, -radius, radius));
        renderUVs.add(new Vector2f(0, 1));
        renderVertices.add(new Vector3f(-radius, -radius, -radius));
        renderUVs.add(new Vector2f(0, 0));
        renderVertices.add(new Vector3f(-radius, radius, -radius));
        renderUVs.add(new Vector2f(1, 0));
        renderVertices.add(new Vector3f(-radius, radius, radius));

        Renderer.renderObject3D(renderVertices, renderUVs, textures[4], Renderer.RENDER_QUADS);
        renderVertices = new ArrayList<Vector3f>();
        renderUVs = new ArrayList<Vector2f>();

        //right
        renderUVs.add(new Vector2f(1, 1));
        renderVertices.add(new Vector3f(radius, -radius, -radius));
        renderUVs.add(new Vector2f(0, 1));
        renderVertices.add(new Vector3f(radius, -radius, radius));
        renderUVs.add(new Vector2f(0, 0));
        renderVertices.add(new Vector3f(radius, radius, radius));
        renderUVs.add(new Vector2f(1, 0));
        renderVertices.add(new Vector3f(radius, radius, -radius));

        Renderer.renderObject3D(renderVertices, renderUVs, textures[5], Renderer.RENDER_QUADS);

        glPopMatrix();

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
