package mEngine.graphics.renderable;

import mEngine.core.GameController;
import mEngine.core.ObjectController;
import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.materials.Material2D;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class LoadingScreen {

    Material2D material;
    List<Vector2f> vertices;
    List<Vector2f> uvs;

    public LoadingScreen(String textureName) {

        material = new Material2D();
        material.setTextureName(textureName);
        vertices = new ArrayList<Vector2f>();
        uvs = new ArrayList<Vector2f>();

        uvs.add(new Vector2f(0, 1));
        uvs.add(new Vector2f(1, 1));
        uvs.add(new Vector2f(1, 0));
        uvs.add(new Vector2f(0, 0));

    }

    private void calculateVertexPositions() {

        int ox = material.getTexture().getTexture().getImageWidth() / 2; //Offset x
        int oy = material.getTexture().getTexture().getImageHeight() / 2; //Offset y

        int cx = Display.getWidth() / 2; //Center x
        int cy = Display.getHeight() / 2; //Center y

        float pox = (float) ox / cx; //x offset in percent
        float poy = (float) oy / cy; //y offset in percent

        vertices.add(new Vector2f(-pox, -poy));
        vertices.add(new Vector2f(pox, -poy));
        vertices.add(new Vector2f(pox, poy));
        vertices.add(new Vector2f(-pox, poy));

    }

    public void render() {

        if (material.getTexture() == null) {

            material.setTextureFromName();
            calculateVertexPositions();

        }

        Renderer.renderObject2D(vertices, uvs, material, Renderer.RENDER_QUADS);

    }

    public void update() {

        if (!GameController.isLoading) ObjectController.setLoadingScreen(null);

    }

}