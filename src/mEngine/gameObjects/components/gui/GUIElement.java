package mEngine.gameObjects.components.gui;

import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.gui.guiComponents.GUIComponent;
import org.lwjgl.util.vector.Vector2f;

import java.util.HashMap;
import java.util.Map;

public class GUIElement extends Component {

    public Vector2f position;
    public Vector2f size;

    public Map<String, GUIComponent> components = new HashMap<String, GUIComponent>();

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

}
