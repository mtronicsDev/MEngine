package mEngine.gameObjects.components.gui.primitives;

import mEngine.gameObjects.components.gui.guiComponents.GUIComponent;
import mEngine.graphics.Renderer;
import mEngine.util.rendering.TextureHelper;
import org.lwjgl.util.vector.Vector2f;

import static java.lang.Math.*;

public class GUIEllipse extends GUIComponent {

    Vector2f radii;

    public GUIEllipse(Vector2f radii) {

        super();
        this.radii = radii;

    }

    public void render() {

        super.render();
        TextureHelper.getTexture("texturedStar").bind(); //Temporary fix

        if (verticesToRender.size() == 0) {

            verticesToRender.add(new Vector2f(parent.position.x, parent.position.y));

            for (int i = 360; i > 0; i--) {

                float radians = (float) toRadians(i);

                verticesToRender.add(new Vector2f(
                        parent.position.x + ((float) cos(radians) * radii.x),
                        parent.position.y + ((float) sin(radians) * radii.y)
                ));

            }

            verticesToRender.add(new Vector2f(
                    parent.position.x + ((float) cos(360) * radii.x),
                    parent.position.y + ((float) sin(360) * radii.y)
            ));

        }

        Renderer.renderObject2D(verticesToRender, Renderer.RENDER_TRIANGLE_FAN);

    }

}
