package mEngine.interactive.gui;

import org.lwjgl.util.vector.Vector2f;

public abstract class GUIElement {

    Vector2f position;
    Vector2f rotation;

    public GUIElement(Vector2f pos, Vector2f rot) {

        position = pos;
        rotation = rot;

    }

    public abstract void update();

}
