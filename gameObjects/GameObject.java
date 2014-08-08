package mEngine.gameObjects;

import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.physics.MovementComponent;
import mEngine.gameObjects.components.physics.PhysicComponent;
import mEngine.gameObjects.components.renderable.ComponentRenderable;
import mEngine.gameObjects.components.renderable.RenderComponent;
import mEngine.util.math.vectors.Matrix3f;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

import java.io.Serializable;
import java.util.*;

public class GameObject implements Serializable {

    public Vector3f position;
    public Vector3f rotation;
    public Vector3f percentRotation;
    public List<Component> components = new ArrayList<Component>();
    private long uuid = UUID.randomUUID().getMostSignificantBits();

    public GameObject(Vector3f pos, Vector3f rot) {

        position = pos;
        rotation = rot;

        percentRotation = new Vector3f(0, 0, 1);

        if (!VectorHelper.areEqual(rotation, new Vector3f())) {

            Matrix3f xAxisRotationMatrix = new Matrix3f(new Vector3f(1, 0, 0),
                    new Vector3f(0, (float) Math.cos(Math.toRadians(rotation.x)), (float) -Math.sin(Math.toRadians(rotation.x))),
                    new Vector3f(0, (float) Math.sin(Math.toRadians(rotation.x)), (float) Math.cos(Math.toRadians(rotation.x))));
            percentRotation = xAxisRotationMatrix.multiplyByVector(percentRotation);

            Matrix3f yAxisRotationMatrix = new Matrix3f(new Vector3f((float) Math.cos(Math.toRadians(rotation.y)), 0, (float) Math.sin(Math.toRadians(rotation.y))),
                    new Vector3f(0, 1, 0),
                    new Vector3f((float) -Math.sin(Math.toRadians(rotation.y)), 0, (float) Math.cos(Math.toRadians(rotation.y))));
            percentRotation = yAxisRotationMatrix.multiplyByVector(percentRotation);

        }

    }

    public GameObject(GameObject src) {

        position = src.position;
        rotation = src.rotation;
        percentRotation = src.percentRotation;

        for (Component component : src.components) {

            addComponent(component);

        }

    }

    public void update() {

        for (Component component : components)
            if (!(component instanceof PhysicComponent)) component.onUpdate();

        for (Component component : components)
            if (component instanceof PhysicComponent) component.onUpdate();

    }

    public void save() {

        for (Component component : components) {

            component.onSave();

        }

    }

    public void load() {

        for (Component component : components) {

            component.onLoad();

        }

    }

    public long getUuid() {
        return uuid;
    }

    public void addToRenderQueue() {

        //Adds this gameObject's models, particles and guiElements to the renderQueue
        for (Component component : components) {

            if (component instanceof ComponentRenderable) ((ComponentRenderable) component).addToRenderQueue();

        }

    }

    public GameObject addComponent(Component component) {

        components.add(component);

        return this;

    }

    public void removeAnyComponent(Class componentClass) {

        Component component = getAnyComponent(componentClass);

        component.onDestroy();
        components.remove(component);

    }

    public Component getAnyComponent(Class componentClass) {

        Component equalingComponent = null;

        for (Component componentInList : components) {

            if (componentClass.isInstance(componentInList)) equalingComponent = componentInList;

        }

        return equalingComponent;

    }

    public GameObject createAllComponents() {

        List<Component> components = new ArrayList<Component>();

        for (Component component : this.components)
            components.add(component);

        for (Component component : components) {

            if (component instanceof RenderComponent) component.onCreation(this);

        }

        for (Component component : components) {

            if (component instanceof MovementComponent) component.onCreation(this);

        }

        for (Component component : components) {

            if (!(component instanceof RenderComponent || component instanceof MovementComponent))
                component.onCreation(this);

        }

        return this;

    }

}