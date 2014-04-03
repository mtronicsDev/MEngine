package mEngine.gameObjects;

import mEngine.gameObjects.components.Camera;
import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.RenderComponent;
import mEngine.gameObjects.components.gui.GUIElement;
import mEngine.graphics.Renderer;
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
        percentRotation = new Vector3f(0, 0, 1);

    }

    public GameObject(GameObject src) {

        position = src.position;
        rotation = src.rotation;
        percentRotation = src.percentRotation;

        for(String key : src.components.keySet()) {

            addComponent(key, src.getComponent(key));

        }

    }

    public void update() {

        for (Component component : components.values()) {

            component.onUpdate();

        }

    }

    public void addToRenderQueue() {

        //Adds this gameObject's models and guiElements to the renderQueue
        for (Component component : components.values()) {

            if (component instanceof Camera) Renderer.currentRenderQueue.addCamera((Camera) component);
            if (component instanceof RenderComponent) {

                RenderComponent renderComponent = (RenderComponent) component;
                renderComponent.model.update(position, rotation);
                Renderer.currentRenderQueue.addModel(((RenderComponent) component).model);

            }
            if (component instanceof GUIElement) Renderer.currentRenderQueue.addGUIElement((GUIElement) component);

        }

    }

    public GameObject addComponent(String key, Component component) {

        components.put(key, component);
        getComponent(key).onCreation(this);
        return this;

    }

    public void removeComponent(String key) {

        components.remove(key);

    }

    public Component getComponent(String key) {

        return components.get(key);

    }

}