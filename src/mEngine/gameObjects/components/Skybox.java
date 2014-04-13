package mEngine.gameObjects.components;

import mEngine.graphics.GraphicsController;
import mEngine.graphics.Renderer;
import mEngine.util.rendering.TextureHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

public class Skybox extends Component {

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
        textures[0].bind();
        renderUVs.add(new Vector2f(0, 0));
        renderVertices.add(new Vector3f(-radius, -radius, radius));
        renderUVs.add(new Vector2f(0, 1));
        renderVertices.add(new Vector3f(radius, -radius, radius));
        renderUVs.add(new Vector2f(1, 1));
        renderVertices.add(new Vector3f(radius, -radius, -radius));
        renderUVs.add(new Vector2f(1, 0));
        renderVertices.add(new Vector3f(-radius, -radius, -radius));

        Renderer.renderObject3D(renderVertices, Renderer.RENDER_QUADS);
        renderVertices = new ArrayList<Vector3f>();

        //top
        textures[1].bind();
        renderUVs.add(new Vector2f(0, 0));
        renderVertices.add(new Vector3f(-radius, radius, -radius));
        renderUVs.add(new Vector2f(0, 1));
        renderVertices.add(new Vector3f(radius, radius, -radius));
        renderUVs.add(new Vector2f(1, 1));
        renderVertices.add(new Vector3f(radius, radius, radius));
        renderUVs.add(new Vector2f(1, 0));
        renderVertices.add(new Vector3f(-radius, radius, radius));

        Renderer.renderObject3D(renderVertices, Renderer.RENDER_QUADS);
        renderVertices = new ArrayList<Vector3f>();

        //back
        textures[2].bind();
        renderUVs.add(new Vector2f(0, 0));
        renderVertices.add(new Vector3f(-radius, radius, radius));
        renderUVs.add(new Vector2f(0, 1));
        renderVertices.add(new Vector3f(radius, radius, radius));
        renderUVs.add(new Vector2f(1, 1));
        renderVertices.add(new Vector3f(radius, -radius, radius));
        renderUVs.add(new Vector2f(1, 0));
        renderVertices.add(new Vector3f(-radius, -radius, radius));

        Renderer.renderObject3D(renderVertices, Renderer.RENDER_QUADS);
        renderVertices = new ArrayList<Vector3f>();

        //front
        textures[3].bind();
        renderUVs.add(new Vector2f(0, 0));
        renderVertices.add(new Vector3f(-radius, -radius, -radius));
        renderUVs.add(new Vector2f(0, 1));
        renderVertices.add(new Vector3f(radius, -radius, -radius));
        renderUVs.add(new Vector2f(1, 1));
        renderVertices.add(new Vector3f(radius, radius, -radius));
        renderUVs.add(new Vector2f(1, 0));
        renderVertices.add(new Vector3f(-radius, radius, -radius));

        Renderer.renderObject3D(renderVertices, Renderer.RENDER_QUADS);
        renderVertices = new ArrayList<Vector3f>();

        //left
        textures[4].bind();
        renderUVs.add(new Vector2f(0, 0));
        renderVertices.add(new Vector3f(-radius, -radius, radius));
        renderUVs.add(new Vector2f(0, 1));
        renderVertices.add(new Vector3f(-radius, -radius, -radius));
        renderUVs.add(new Vector2f(1, 1));
        renderVertices.add(new Vector3f(-radius, radius, -radius));
        renderUVs.add(new Vector2f(1, 0));
        renderVertices.add(new Vector3f(-radius, radius, radius));

        Renderer.renderObject3D(renderVertices, Renderer.RENDER_QUADS);
        renderVertices = new ArrayList<Vector3f>();

        //right
        textures[5].bind();
        renderUVs.add(new Vector2f(0, 0));
        renderVertices.add(new Vector3f(radius, -radius, -radius));
        renderUVs.add(new Vector2f(0, 1));
        renderVertices.add(new Vector3f(radius, -radius, radius));
        renderUVs.add(new Vector2f(1, 1));
        renderVertices.add(new Vector3f(radius, radius, radius));
        renderUVs.add(new Vector2f(1, 0));
        renderVertices.add(new Vector3f(radius, radius, -radius));

        Renderer.renderObject3D(renderVertices, Renderer.RENDER_QUADS);

        glPopMatrix();

    }

}
