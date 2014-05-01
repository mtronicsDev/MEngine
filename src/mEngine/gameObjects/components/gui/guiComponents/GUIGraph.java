package mEngine.gameObjects.components.gui.guiComponents;

import mEngine.graphics.GraphicsController;
import mEngine.graphics.Renderer;
import mEngine.util.math.graphs.Graph;
import mEngine.util.math.vectors.VectorHelper;
import mEngine.util.rendering.TextureHelper;
import mEngine.util.resources.ResourceHelper;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static mEngine.util.math.MathHelper.clamp;
import static org.lwjgl.opengl.GL11.*;

public class GUIGraph extends GUIComponent {

    public Vector2f size;
    public Graph graph;
    String textureName;
    Texture texture;

    public GUIGraph(Vector2f size, double[] values, String textureName) {

        this.size = size;
        this.graph = new Graph(values);
        if (values.length == 0) {

            this.graph = new Graph((int) size.x);

        }

        this.textureName = textureName;

    }

    public void render() {

        super.render();

        parent.position = new Vector2f(0, Display.getHeight() - size.y);
        size.x = Display.getWidth();

        float stepSize = size.x / graph.getLength();

        if (texture == null) texture = TextureHelper.getTexture(textureName);

        for (int count = 0; count < graph.getLength(); count++) {

            verticesToRender.add(new Vector2f(parent.position.x + stepSize * count, parent.position.y + size.y - (float) clamp(graph.getY(count), 0, size.y)));

        }

        Renderer.renderObject2D(verticesToRender, Renderer.RENDER_LINE_STRIP);

    }

    public void onExternalUpdate(Object[] args) {

        super.onExternalUpdate(args);
        if (args[0] instanceof Graph) graph = (Graph) args[0];

    }

}
