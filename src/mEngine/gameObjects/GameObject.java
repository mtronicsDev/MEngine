package mEngine.gameObjects;

import mEngine.gameObjects.components.Camera;
import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.RenderComponent;
import mEngine.gameObjects.components.gui.GUIElement;
import mEngine.graphics.Renderer;
import mEngine.util.math.vectors.Matrix3d;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameObject implements Serializable {

    public Vector3f position;
    public Vector3f rotation;
    public Vector3f percentRotation;
    public Map<String, Component> components = new HashMap<String, Component>();
    private long uuid = UUID.randomUUID().getMostSignificantBits();

    public GameObject(Vector3f pos, Vector3f rot) {

        position = pos;
        rotation = rot;

        percentRotation = new Vector3f(0, 0, 1);

        if (!VectorHelper.areEqual(rotation, new Vector3f())) {

            Matrix3d xAxisRotationMatrix = new Matrix3d(new Vector3f(1, 0, 0),
                    new Vector3f(0, (float) Math.cos(Math.toRadians(rotation.x)), (float) -Math.sin(Math.toRadians(rotation.x))),
                    new Vector3f(0, (float) Math.sin(Math.toRadians(rotation.x)), (float) Math.cos(Math.toRadians(rotation.x))));
            percentRotation = xAxisRotationMatrix.multiplyByVector(percentRotation);

            Matrix3d yAxisRotationMatrix = new Matrix3d(new Vector3f((float) Math.cos(Math.toRadians(rotation.y)), 0, (float) Math.sin(Math.toRadians(rotation.y))),
                    new Vector3f(0, 1, 0),
                    new Vector3f((float) -Math.sin(Math.toRadians(rotation.y)), 0, (float) Math.cos(Math.toRadians(rotation.y))));
            percentRotation = yAxisRotationMatrix.multiplyByVector(percentRotation);

        }

    }

    public GameObject(GameObject src) {

        position = src.position;
        rotation = src.rotation;
        percentRotation = src.percentRotation;

        for (String key : src.components.keySet()) {

            addComponent(key, src.getComponent(key));

        }

    }

    public void update() {

        for (Component component : components.values()) {

            component.onUpdate();

        }

    }

    public void save() {

        for (Component component : components.values()) {

            component.onSave();

        }

    }

    public void load() {

        for (Component component : components.values()) {

            component.onLoad();

        }

    }

    public long getUuid() {
        return uuid;
    }

    public void addToRenderQueue() {

        //Adds this gameObject's models and guiElements to the renderQueue
        for (Component component : components.values()) {

            if (component instanceof Camera) Renderer.currentRenderQueue.addCamera((Camera) component);
            if (component instanceof RenderComponent) {

                RenderComponent renderComponent = (RenderComponent) component;

                if (renderComponent.model != null) {
                    renderComponent.model.update(position, rotation);
                    Renderer.currentRenderQueue.addModel(((RenderComponent) component).model);
                }

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