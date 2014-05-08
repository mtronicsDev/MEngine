package mEngine.gameObjects.components.gui.guiComponents;

import mEngine.graphics.Renderer;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GUIQuad extends GUIComponent {

    private List<Vector2f> uvs;

    public GUIQuad() {

        uvs = new ArrayList<Vector2f>();
        uvs.add(new Vector2f(0, 1));
        uvs.add(new Vector2f(1, 1));
        uvs.add(new Vector2f(1, 0));
        uvs.add(new Vector2f(0, 0));

    }

    public void render() {

        super.render();

        if (verticesToRender.size() == 0) {

            verticesToRender.add(new Vector2f(parent.getPosition().x, parent.getPosition().y + parent.getSize().y));
            verticesToRender.add(new Vector2f(parent.getPosition().x + parent.getSize().x, parent.getPosition().y + parent.getSize().y));
            verticesToRender.add(new Vector2f(parent.getPosition().x + parent.getSize().x, parent.getPosition().y));
            verticesToRender.add(new Vector2f(parent.getPosition().x, parent.getPosition().y));

        }

        Renderer.renderObject2D(verticesToRender, uvs, parent.material, Renderer.RENDER_QUADS);

    }

}