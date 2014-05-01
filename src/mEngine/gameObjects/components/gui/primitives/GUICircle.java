package mEngine.gameObjects.components.gui.primitives;

import org.lwjgl.util.vector.Vector2f;

public class GUICircle extends GUIEllipse {

    public GUICircle(float radius) {

        super(new Vector2f(radius, radius));

    }

}
