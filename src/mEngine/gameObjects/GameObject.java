/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameObject implements Serializable {

    public Vector3f position;
    public Vector3f rotation;
    public Vector3f percentRotation;
    public List<Module> modules = new ArrayList<Module>();
    private long uuid = UUID.randomUUID().getMostSignificantBits();

    /**
     * The default constructor for game objects
     *
     * @param pos The initial position of the object
     * @param rot The initial rotation
     */
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

    /**
     * This constructor is used to clone existing game objects
     *
     * @param src The game object to clone
     */
    public GameObject(GameObject src) {

        position = src.position;
        rotation = src.rotation;
        percentRotation = src.percentRotation;

        for (Module module : src.modules) {

            addModule(module);

        }

    }

    /**
     * Automatically called by the game loop, updates the object and its modules
     */
    public void update() {

        for (Module module : modules)
            if (!(module instanceof PhysicModule)) module.onUpdate();

        for (Module module : modules)
            if (module instanceof PhysicModule) module.onUpdate();

    }

    /**
     * This is used for preparing the game object for serialization (saving it in its current state)
     * It also takes care of all of the game objects modules
     */
    public void save() {

        for (Module module : modules) {

            module.onSave();

        }

    }

    /**
     * This prepares the game object and its components for de-serialization (loading)
     */
    public void load() {

        for (Module module : modules) {

            module.onLoad();

        }

    }

    /**
     * Allows for easier identification of game objects
     *
     * @return The Universally Unique Identifier of the game object
     */
    public long getUuid() {
        return uuid;
    }

    /**
     * Adds the game object and its modules to the current render queue.
     * This method is called by the render loop
     */
    public void addToRenderQueue() {

        //Adds this gameObject's models, particles and guiElements to the renderQueue
        for (Module module : modules) {

            if (module instanceof ModuleRenderable) ((ModuleRenderable) module).addToRenderQueue();

        }

    }

    /**
     * Adds the desired module to the game object's module list
     *
     * @param module The desired module
     * @return The game object, allows for chaining module additions
     */
    public GameObject addModule(Module module) {

        modules.add(module);

        return this;

    }

    /**
     * Removes the first module of the given class from the game object
     *
     * @param moduleClass The module's class
     */
    public void removeModule(Class moduleClass) {

        Module module = getModule(moduleClass);

        module.onDestroy();
        modules.remove(module);

    }

    /**
     * Returns the first module of the given class in the game object's module list
     *
     * @param moduleClass The class of the desired module
     * @return The first module with the desired class
     */
    public Module getModule(Class moduleClass) {

        Module equalingModule = null;

        for (Module moduleInList : modules) {

            if (moduleClass.isInstance(moduleInList)) equalingModule = moduleInList;

        }

        return equalingModule;

    }

    /**
     * This must be executed after all modules were added to the game object.
     * It calls the modules' onCreation() methods
     *
     * @return The game objects, allows for adding it to the object controller after executing this method
     */
    public GameObject createModules() {

        //Multiple for-loops because every module has to be in the gameObject's list first because of their dependencies
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