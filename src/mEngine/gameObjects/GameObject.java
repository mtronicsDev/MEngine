package mEngine.gameObjects;

import mEngine.gameObjects.modules.Module;
import mEngine.gameObjects.modules.physics.MovementModule;
import mEngine.gameObjects.modules.physics.PhysicModule;
import mEngine.gameObjects.modules.renderable.ModuleRenderable;
import mEngine.gameObjects.modules.renderable.RenderModule;
import mEngine.util.math.vectors.Matrix3f;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

import java.io.Serializable;
import java.util.*;

public class GameObject implements Serializable {

    public Vector3f position;
    public Vector3f rotation;
    public Vector3f percentRotation;
    public List<Module> modules = new ArrayList<Module>();
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

        for (Module module : src.modules) {

            addComponent(module);

        }

    }

    public void update() {

        for (Module module : modules)
            if (!(module instanceof PhysicModule)) module.onUpdate();

        for (Module module : modules)
            if (module instanceof PhysicModule) module.onUpdate();

    }

    public void save() {

        for (Module module : modules) {

            module.onSave();

        }

    }

    public void load() {

        for (Module module : modules) {

            module.onLoad();

        }

    }

    public long getUuid() {
        return uuid;
    }

    public void addToRenderQueue() {

        //Adds this gameObject's models, particles and guiElements to the renderQueue
        for (Module module : modules) {

            if (module instanceof ModuleRenderable) ((ModuleRenderable) module).addToRenderQueue();

        }

    }

    public GameObject addComponent(Module module) {

        modules.add(module);

        return this;

    }

    public void removeAnyComponent(Class componentClass) {

        Module module = getAnyComponent(componentClass);

        module.onDestroy();
        modules.remove(module);

    }

    public Module getAnyComponent(Class componentClass) {

        Module equalingModule = null;

        for (Module moduleInList : modules) {

            if (componentClass.isInstance(moduleInList)) equalingModule = moduleInList;

        }

        return equalingModule;

    }

    public GameObject createAllComponents() {

        List<Module> modules = new ArrayList<Module>();

        for (Module module : this.modules)
            modules.add(module);

        for (Module module : modules) {

            if (module instanceof RenderModule) module.onCreation(this);

        }

        for (Module module : modules) {

            if (module instanceof MovementModule) module.onCreation(this);

        }

        for (Module module : modules) {

            if (!(module instanceof RenderModule || module instanceof MovementModule))
                module.onCreation(this);

        }

        return this;

    }

}