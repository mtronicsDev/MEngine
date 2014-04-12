package mEngine.gameObjects.components.gui.primitives;

import org.lwjgl.util.vector.Vector2f;

import java.io.Serializable;

public class GUICircle extends GUIEllipse implements Serializable {

    public GUICircle(Vector2f pos, float radius) {

        super(pos, new Vector2f(radius, radius));

    }

}
