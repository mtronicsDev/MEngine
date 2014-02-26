package mEngine.interactive.gui;

import mEngine.interactive.gui.guiComponents.GUIComponent;
import org.lwjgl.util.vector.Vector2f;

import java.util.HashMap;
import java.util.Map;

public class GUIElement {

    public Vector2f position;
    public Vector2f rotation;

    public Map<String, GUIComponent> components = new HashMap<String, GUIComponent>();

    public GUIElement(Vector2f pos) {

        this(pos, new Vector2f());

    }

    public GUIElement(Vector2f pos, Vector2f rot) {

        position = pos;
        rotation = rot;

    }

    public void update() {

        for(GUIComponent component : components.values()) { component.onUpdate(this); }

    }

    public GUIElement addComponent(String key, GUIComponent component) {

        components.put(key, component);
        getComponent(key).onCreation(this);
        return this;

    }

    public GUIComponent getComponent(String key) { return components.get(key); }

}
