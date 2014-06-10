package mEngine.graphics;

import mEngine.gameObjects.components.gui.GUIElement;
import mEngine.gameObjects.components.renderable.Camera;
import mEngine.gameObjects.components.renderable.ComponentRenderable3D;
import mEngine.gameObjects.components.renderable.Particle;
import mEngine.gameObjects.components.renderable.Skybox;
import mEngine.gameObjects.components.renderable.light.LightSource;

import java.util.ArrayList;
import java.util.List;

public class RenderQueue {

    public List<LightSource> lightSources = new ArrayList<LightSource>();
    public Camera camera;
    private Skybox skybox;
    private List<ComponentRenderable3D> modelQueue = new ArrayList<ComponentRenderable3D>();
    private List<Particle> particleQueue = new ArrayList<Particle>();
    private List<GUIElement> guiQueue = new ArrayList<GUIElement>();

    public void addCamera(Camera camera) {

        this.camera = camera;

    }

    public void addSkybox(Skybox skybox) {

        this.skybox = skybox;

    }

    public void addModel(ComponentRenderable3D component) {

        modelQueue.add(component);

    }

    public void addParticle(Particle particle) {

        particleQueue.add(particle);

    }

    public void addGUIElement(GUIElement element) {

        guiQueue.add(element);

    }

    public void addLightSource(LightSource lightSource) {

        lightSources.add(lightSource);

    }

    public void render() {

        /*for (LightSource lightSource : lightSources) {

            lightSource.updateShadowMap();

        }*/

        GraphicsController.switchTo3D();
        if (camera != null) camera.render();
        for (ComponentRenderable3D component : modelQueue) {

            component.render();

        }

        for (Particle particle : particleQueue) {

            particle.render();

        }

        if (skybox != null) skybox.render();

        GraphicsController.switchTo2D();
        for (GUIElement element : guiQueue) {

            element.render();

        }

    }

}
