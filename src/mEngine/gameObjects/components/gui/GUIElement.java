package mEngine.gameObjects.components.gui;

import mEngine.gameObjects.components.gui.guiComponents.GUIComponent;
import mEngine.gameObjects.components.renderable.ComponentRenderable;
import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.Material;
import mEngine.graphics.renderable.Material2D;
import org.lwjgl.util.vector.Vector2f;

import java.util.HashMap;

public class GUIElement extends ComponentRenderable {

    public Vector2f position;
    public Vector2f size;
    public Material2D material;

    public HashMap<String, GUIComponent> components = new HashMap<String, GUIComponent>();

    public GUIElement(Vector2f pos) {

        this(pos, new Vector2f());

    }

    public GUIElement(Vector2f pos, Vector2f size) {

        this(pos, size, new Material2D());

    }

    public GUIElement(Vector2f pos, Vector2f size, Material2D material) {

        position = pos;
        this.size = size;
        this.material = material;

    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        for (GUIComponent component : components.values()) {
            component.onUpdate();
        }

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
            component.render();
        }

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addGUIElement(this);

    }

}
