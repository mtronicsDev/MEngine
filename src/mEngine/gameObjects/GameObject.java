package mEngine.gameObjects;

import mEngine.gameObjects.components.Component;
import org.lwjgl.util.vector.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class GameObject {

    public Vector3f position;
    public Vector3f rotation;
    public Vector3f percentRotation;
    public Map<String, Component> components = new HashMap<String, Component>();

    public GameObject(Vector3f pos, Vector3f rot) {

        position = pos;
        rotation = rot;
        percentRotation = new Vector3f();

    }

    public void update() {

        for (Component component : components.values()) {
            component.onUpdate(this);
        }

    }

    public GameObject addComponent(String key, Component component) {

        components.put(key, component);
        getComponent(key).onCreation(this);
        return this;

    }

    public Component getComponent(String key) {
        return components.get(key);
    }

}