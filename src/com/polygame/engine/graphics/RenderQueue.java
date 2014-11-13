/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.graphics;

import com.polygame.engine.gameObjects.modules.gui.GUIElement;
import com.polygame.engine.gameObjects.modules.renderable.Camera;
import com.polygame.engine.gameObjects.modules.renderable.ModuleRenderable3D;
import com.polygame.engine.gameObjects.modules.renderable.Particle;
import com.polygame.engine.gameObjects.modules.renderable.Skybox;
import com.polygame.engine.gameObjects.modules.renderable.light.LightSource;

import java.util.ArrayList;
import java.util.List;

public class RenderQueue {

    public List<LightSource> lightSources = new ArrayList<LightSource>();
    public Camera camera;
    private Skybox skybox;
    private List<ModuleRenderable3D> modelQueue = new ArrayList<ModuleRenderable3D>();
    private List<Particle> particleQueue = new ArrayList<Particle>();
    private List<GUIElement> guiQueue = new ArrayList<GUIElement>();

    /**
     * Sets the active camera.
     *
     * @param camera The desired camera
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Sets the active skybox.
     *
     * @param skybox The desired skybox
     */
    public void setSkybox(Skybox skybox) {
        this.skybox = skybox;
    }

    /**
     * Adds a 3D model to the current render queue.
     *
     * @param module The desired model
     */
    public void addModel(ModuleRenderable3D module) {
        modelQueue.add(module);
    }

    /**
     * Adds a particle to the current render queue.
     *
     * @param particle The desired particle
     */
    public void addParticle(Particle particle) {
        particleQueue.add(particle);
    }

    /**
     * Adds a GUI element to the current render queue.
     *
     * @param element The desired GUI element
     */
    public void addGUIElement(GUIElement element) {
        guiQueue.add(element);
    }

    /**
     * Adds a light source to the current render queue.
     *
     * @param lightSource The desired light source
     */
    public void addLightSource(LightSource lightSource) {
        lightSources.add(lightSource);
    }

    /**
     * Renders all objects in the current render queue.
     */
    public void render() {

        GraphicsController.switchTo3D();
        if (camera != null) camera.render();
        modelQueue.forEach(ModuleRenderable3D::render);
        particleQueue.forEach(Particle::render);
        if (skybox != null) skybox.render();

        GraphicsController.switchTo2D();
        guiQueue.forEach(GUIElement::render);

    }

}
