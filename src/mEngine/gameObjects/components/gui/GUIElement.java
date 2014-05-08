package mEngine.gameObjects.components.gui;

import mEngine.gameObjects.components.gui.guiComponents.GUIComponent;
import mEngine.gameObjects.components.renderable.ComponentRenderable;
import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.materials.Material2D;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import java.util.HashMap;

public class GUIElement extends ComponentRenderable {

    public Material2D material;
    public HashMap<String, GUIComponent> components = new HashMap<String, GUIComponent>();
    private Vector2f position; //Values from 0 to 1
    private Vector2f size; //Values from 0 to 1

    public GUIElement(Vector2f posInPixels) {

        this(posInPixels, new Vector2f());

    }

    public GUIElement(Vector2f posInPixels, Vector2f sizeInPixels) {

        this(posInPixels, sizeInPixels, new Material2D());

    }

    public GUIElement(Vector2f posInPixels, Vector2f sizeInPixels, Material2D material) {

        //Absolute size to relative size
        position = new Vector2f();
        position.x = posInPixels.x / Display.getWidth();
        position.y = posInPixels.y / Display.getHeight();

        //Absolute size to relative size
        size = new Vector2f();
        size.x = sizeInPixels.x / Display.getWidth();
        size.y = sizeInPixels.y / Display.getHeight();

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

    public Vector2f getSize() {

        return new Vector2f(size.x * Display.getWidth(), size.y * Display.getHeight()); //TODO: Make this lag-free

    }

    public void setSize(Vector2f sizeInPixels) {

        size = new Vector2f(sizeInPixels.x / Display.getWidth(), sizeInPixels.y / Display.getHeight());

    }

    public Vector2f getPosition() {

        return new Vector2f(position.x * Display.getWidth(), position.y * Display.getHeight()); //TODO: Make this lag-free

    }

    public void setPosition(Vector2f positionInPixels) {

        position = new Vector2f(positionInPixels.x / Display.getWidth(), positionInPixels.y / Display.getHeight());

    }

}
