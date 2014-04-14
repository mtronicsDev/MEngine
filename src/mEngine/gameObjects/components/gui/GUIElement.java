package mEngine.gameObjects.components.gui;

import mEngine.gameObjects.components.rendering.ComponentRenderable;
import mEngine.gameObjects.components.gui.guiComponents.GUIComponent;
import mEngine.graphics.Renderer;
import org.lwjgl.util.vector.Vector2f;

import java.util.HashMap;

public class GUIElement extends ComponentRenderable {

    public Vector2f position;
    public Vector2f size;

    public HashMap<String, GUIComponent> components = new HashMap<String, GUIComponent>();

    public GUIElement(Vector2f pos) {

        this(pos, new Vector2f());

    }

    public GUIElement(Vector2f pos, Vector2f size) {

        position = pos;
        this.size = size;

    }

    @Override
    public void onSave() {

        super.onSave();
        for (GUIComponent component : components.values()) {

            component.onSave();

        }

    }

    @Override
    public void onLoad() {

        super.onLoad();
        for (GUIComponent component : components.values()) {

            component.onLoad();

        }

    }

    public GUIElement addComponent(String key, GUIComponent component) {

        components.put(key, component);
        getComponent(key).onCreation(this);
        return this;

    }

    public GUIComponent getComponent(String key) {
        return components.get(key);
    }

    public void render() {

        for (GUIComponent component : components.values()) {
            component.onUpdate();
        }

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addGUIElement(this);

    }

}
