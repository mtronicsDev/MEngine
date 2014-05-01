package mEngine.gameObjects.components.gui;

import mEngine.gameObjects.components.gui.guiComponents.GUIComponent;
import mEngine.gameObjects.components.renderable.ComponentRenderable;
import mEngine.graphics.Renderer;
import mEngine.util.data.BinaryHelper;
import mEngine.util.math.MathHelper;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import java.util.HashMap;

public class GUIElement extends ComponentRenderable {

    public Vector2f position;
    private Vector2f percentPosition;
    public Vector2f size;
    public Vector2f percentSize;
    public Vector2f independentSize;

    public HashMap<String, GUIComponent> components = new HashMap<String, GUIComponent>();

    public GUIElement(Vector2f pos, boolean independentSize) {

        this(pos, new Vector2f(), independentSize);

    }

    public GUIElement(Vector2f pos, boolean independentXSize, boolean independentYSize) {

        this(pos, new Vector2f(), independentXSize, independentYSize);

    }

    public GUIElement(Vector2f pos, Vector2f size, boolean independentSize) {

        this(pos, size, independentSize, independentSize);

    }

    public GUIElement(Vector2f pos, Vector2f size, boolean independentXSize, boolean independentYSize) {

        this.independentSize = new Vector2f(BinaryHelper.convertToBinaryInteger(independentXSize), BinaryHelper.convertToBinaryInteger(independentYSize));

        percentPosition = new Vector2f((float) MathHelper.clamp(pos.x, 0, 100) / 100, (float) MathHelper.clamp(pos.y, 0, 100) / 100);

        position = new Vector2f(Display.getWidth() * percentPosition.x, Display.getHeight() * percentPosition.y);

        if (independentSize.x == 0) {

            if (independentSize.y == 0) {

                percentSize = new Vector2f((float) MathHelper.clamp(size.x, 0, 100) / 100, (float) MathHelper.clamp(size.y, 0, 100) / 100);
                this.size = new Vector2f(Display.getWidth() * percentSize.x, Display.getHeight() * percentSize.y);

            } else {

                percentSize = new Vector2f((float) MathHelper.clamp(size.x, 0, 100) / 100, 0);
                this.size = new Vector2f(Display.getWidth() * percentSize.x, size.y);

            }

        } else {

            if (independentSize.y == 0) {

                percentSize = new Vector2f(0, (float) MathHelper.clamp(size.y, 0, 100) / 100);
                this.size = new Vector2f(size.x, Display.getHeight() * percentSize.y);

            } else {

                percentSize = new Vector2f();
                this.size = size;

            }

        }

    }

    public void onUpdate() {

        if (independentSize.x == 0) size.x = Display.getWidth() * percentSize.x;

        if (independentSize.y == 0) size.y = Display.getHeight() * percentSize.y;

        position.x = Display.getWidth() * percentPosition.x - size.x / 2;
        position.y = Display.getHeight() * percentPosition.y - size.y / 2;

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
