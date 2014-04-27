package mEngine.gameObjects.components.gui.primitives;

import org.lwjgl.util.vector.Vector2f;

public class GUICircle extends GUIEllipse {

    public GUICircle(Vector2f pos, float radius) {

        this(pos, radius, false);

    }

    public GUICircle(Vector2f pos, float radius, boolean addedAsLast) {

        super(pos, new Vector2f(radius, radius), addedAsLast);

    }

}
