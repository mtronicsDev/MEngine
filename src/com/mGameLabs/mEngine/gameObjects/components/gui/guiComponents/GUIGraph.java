package com.mGameLabs.mEngine.gameObjects.components.gui.guiComponents;

import com.mGameLabs.mEngine.gameObjects.components.gui.GUIElement;
import com.mGameLabs.mEngine.graphics.Renderer;
import com.mGameLabs.mEngine.util.math.graphs.Graph;
import com.mGameLabs.mEngine.util.rendering.TextureHelper;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import static com.mGameLabs.mEngine.util.math.MathHelper.clamp;

public class GUIGraph extends GUIComponent {

    protected Graph graph;
    protected String textureName;
    protected Texture texture;

    public GUIGraph(double[] values, String textureName) {

        this.graph = new Graph(values);
        this.textureName = textureName;

    }

    @Override
    public void onCreation(GUIElement element) {

        super.onCreation(element);

        if (graph.getValues().length == 0) {

            this.graph = new Graph((int) parent.getSize().x);

        }

    }

    public void render() {

        super.render();

        float stepSize = parent.getSize().x / graph.getLength();

        if (texture == null) texture = TextureHelper.getTexture(textureName).getTexture();

        for (int count = 0; count < graph.getLength(); count++) {

            verticesToRender.add(new Vector2f(parent.getPosition().x + stepSize * count, parent.getPosition().y + parent.getSize().y - (float) clamp(graph.getY(count), 0, parent.getSize().y)));

        }

        Renderer.renderObject2D(verticesToRender, Renderer.RENDER_LINE_STRIP);

    }

    public void onExternalUpdate(Object[] args) {

        super.onExternalUpdate(args);
        if (args[0] instanceof Graph) graph = (Graph) args[0];

    }

}
