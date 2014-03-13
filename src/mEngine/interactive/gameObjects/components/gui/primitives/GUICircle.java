package mEngine.interactive.gameObjects.components.gui.primitives;

import org.lwjgl.util.vector.Vector2f;

public class GUICircle extends GUIEllipse {

    public GUICircle(Vector2f pos, float radius) {

        super(pos, new Vector2f(radius, radius));

    }

}
