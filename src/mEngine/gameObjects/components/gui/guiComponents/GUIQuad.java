package mEngine.gameObjects.components.gui.guiComponents;

import mEngine.graphics.Renderer;
import mEngine.util.rendering.TextureHelper;
import mEngine.util.resources.ResourceHelper;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class GUIQuad extends GUIComponent {

    protected String textureName;
    protected Texture texture;
    boolean isTextureThere = true;

    public GUIQuad(String fileName) {

        textureName = fileName;

    }

    public void onUpdate() {

        super.onUpdate();

        if (texture == null && isTextureThere) {

            File textureFile = ResourceHelper.getResource(textureName, ResourceHelper.RES_TEXTURE);

            if (!textureFile.exists()) isTextureThere = false;

            else texture = TextureHelper.getTexture(textureName);

        }

        if (isTextureThere) {

            List<Vector2f> vertices = new ArrayList<Vector2f>();
            List<Vector2f> uvs = new ArrayList<Vector2f>();

            vertices.add(new Vector2f(parent.position.x, parent.position.y + parent.size.y));
            vertices.add(new Vector2f(parent.position.x + parent.size.x, parent.position.y + parent.size.y));
            vertices.add(new Vector2f(parent.position.x + parent.size.x, parent.position.y));
            vertices.add(new Vector2f(parent.position.x, parent.position.y));

            uvs.add(new Vector2f(0, 1));
            uvs.add(new Vector2f(1, 1));
            uvs.add(new Vector2f(1, 0));
            uvs.add(new Vector2f(0, 0));

            Renderer.renderObject2D(vertices, uvs, texture, Renderer.RENDER_QUADS);

        } else {

            List<Vector2f> vertices = new ArrayList<Vector2f>();

            vertices.add(new Vector2f(parent.position.x, parent.position.y + parent.size.y));
            vertices.add(new Vector2f(parent.position.x + parent.size.x, parent.position.y + parent.size.y));
            vertices.add(new Vector2f(parent.position.x + parent.size.x, parent.position.y));
            vertices.add(new Vector2f(parent.position.x, parent.position.y));

            Renderer.renderObject2D(vertices, Renderer.RENDER_QUADS);

        }

    }

    @Override
    public void onSave() {

        super.onSave();
        texture = null;

    }
}