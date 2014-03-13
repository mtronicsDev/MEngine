package mEngine.interactive.gameObjects.components.gui;

import mEngine.graphics.GraphicsController;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.interactive.gameObjects.components.Component;
import mEngine.interactive.gameObjects.components.gui.guiComponents.GUIComponent;
import org.lwjgl.util.vector.Vector2f;

import java.util.HashMap;
import java.util.Map;

public class GUIElement extends Component{

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

    public void onUpdate(GameObject obj) {

        GraphicsController.switchTo2D();
        for(GUIComponent component : components.values()) { component.onUpdate(); }

    }

    public GUIElement addComponent(String key, GUIComponent component) {

        components.put(key, component);
        getComponent(key).onCreation(this);
        return this;

    }

    public GUIComponent getComponent(String key) { return components.get(key); }

}
