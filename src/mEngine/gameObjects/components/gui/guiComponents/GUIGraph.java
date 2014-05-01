package mEngine.gameObjects.components.gui.guiComponents;

import mEngine.graphics.Renderer;
import mEngine.util.math.MathHelper;
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

public class GUIGraph extends GUIComponent {

    public Vector2f size;
    public Graph graph;
    String textureName;
    Texture texture;
    boolean isTextureThere = true;

    public GUIGraph(Vector2f size, double[] values, String textureName) {

        this.size = size;
        this.graph = new Graph(values);
        if (values.length == 0) {

            this.graph = new Graph((int) size.x);

        }

        this.textureName = textureName;

    }

    public void onUpdate() {

        parent.onUpdate();

        if (parent.independentSize.x == 0) {

            if (parent.percentSize.x == 0) parent.percentSize.x = (float) MathHelper.clamp(size.x, 0, 100);

            size.x = Display.getWidth() * parent.percentSize.x;

        }

        if (parent.independentSize.y == 0) {

            if (parent.percentSize.y == 0) parent.percentSize.y = (float) MathHelper.clamp(size.y, 0, 100);

            size.y = Display.getHeight() * parent.percentSize.y;

        }

        float stepSize = size.x / graph.getLength();

        if (texture == null && isTextureThere) {

            File textureFile = ResourceHelper.getResource(textureName, ResourceHelper.RES_TEXTURE);

            if (!textureFile.exists()) isTextureThere = false;

            else texture = TextureHelper.getTexture(textureName);

        }

        if (isTextureThere) {

            List<Vector2f> vertices = new ArrayList<Vector2f>();
            List<Vector2f> uvs = new ArrayList<Vector2f>();

            for (int count = 0; count < graph.getLength(); count++) {

                uvs.add(new Vector2f(0, 1 - (float) (clamp(graph.getY(count), 0, size.y) / size.y)));

                vertices.add(new Vector2f(parent.position.x + stepSize * count,
                        parent.position.y + size.y - (float) clamp(graph.getY(count), 0, size.y)));

            }

            Renderer.renderObject2D(vertices, uvs, texture, Renderer.RENDER_LINE_STRIP);

        } else {

            List<Vector2f> vertices = new ArrayList<Vector2f>();

            for (int count = 0; count < graph.getLength(); count++) {

                vertices.add(new Vector2f(Display.getWidth() * parent.position.x + stepSize * count, parent.position.y + size.y - (float) clamp(graph.getY(count), 0, size.y)));

            }

            Renderer.renderObject2D(vertices, Renderer.RENDER_LINE_STRIP);

        }

    }

    public void onExternalUpdate(Object[] args) {

        super.onExternalUpdate(args);
        if (args[0] instanceof Graph) graph = (Graph) args[0];

    }

}
