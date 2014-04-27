package mEngine.graphics.renderable;

import mEngine.core.GameController;
import mEngine.core.ObjectController;
import mEngine.graphics.Renderer;
import mEngine.util.rendering.TextureHelper;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.List;

public class LoadingScreen {

    String textureName;
    Texture texture;
    List<Vector2f> vertices;
    List<Vector2f> uvs;

    public LoadingScreen(String textureName) {

        this.textureName = textureName;
        vertices = new ArrayList<Vector2f>();
        uvs = new ArrayList<Vector2f>();

        uvs.add(new Vector2f(0, 1));
        uvs.add(new Vector2f(1, 1));
        uvs.add(new Vector2f(1, 0));
        uvs.add(new Vector2f(0, 0));

    }

    private void calculateVertexPositions() {

        int ox = texture.getImageWidth() / 2; //Offset x
        int oy = texture.getImageHeight() / 2; //Offset y

        int cx = Display.getWidth() / 2; //Center x
        int cy = Display.getHeight() / 2; //Center y

        float pox = (float)ox / cx; //x offset in percent
        float poy = (float)oy / cy; //y offset in percent

        vertices.add(new Vector2f(-pox, -poy));
        vertices.add(new Vector2f(pox, -poy));
        vertices.add(new Vector2f(pox, poy));
        vertices.add(new Vector2f(-pox, poy));

    }

    public void render() {

        if(texture == null) {

            texture = TextureHelper.getTexture(textureName);
            calculateVertexPositions();

        }

        Renderer.renderObject2D(vertices, uvs, texture, Renderer.RENDER_QUADS);

    }

    public void update() {

        if(!GameController.isLoading) ObjectController.setLoadingScreen(null);

    }

}