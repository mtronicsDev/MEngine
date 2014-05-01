package mEngine.gameObjects.components.gui.guiComponents;

import mEngine.graphics.GraphicsController;
import mEngine.graphics.Renderer;
import mEngine.util.math.vectors.VectorHelper;
import mEngine.util.rendering.TextureHelper;
import mEngine.util.resources.ResourceHelper;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class GUIQuad extends GUIComponent {

    private String textureName;
    private Texture texture;
    private List<Vector2f> uvs;

    public GUIQuad(String fileName) {

        textureName = fileName;
        uvs = new ArrayList<Vector2f>();
        uvs.add(new Vector2f(0, 1));
        uvs.add(new Vector2f(1, 1));
        uvs.add(new Vector2f(1, 0));
        uvs.add(new Vector2f(0, 0));

    }

    public void render() {

        super.render();

        if (texture == null) texture = TextureHelper.getTexture(textureName);

        if(verticesToRender.size() == 0) {

            verticesToRender.add(new Vector2f(parent.position.x, parent.position.y + parent.size.y));
            verticesToRender.add(new Vector2f(parent.position.x + parent.size.x, parent.position.y + parent.size.y));
            verticesToRender.add(new Vector2f(parent.position.x + parent.size.x, parent.position.y));
            verticesToRender.add(new Vector2f(parent.position.x, parent.position.y));

        }

        Renderer.renderObject2D(verticesToRender, uvs, texture, Renderer.RENDER_QUADS);

    }

    @Override
    public void onSave() {

        super.onSave();
        texture = null;

    }
}