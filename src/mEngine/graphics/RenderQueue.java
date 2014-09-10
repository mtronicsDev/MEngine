package mEngine.graphics;

import mEngine.gameObjects.modules.gui.GUIElement;
import mEngine.gameObjects.modules.renderable.Camera;
import mEngine.gameObjects.modules.renderable.ModuleRenderable3D;
import mEngine.gameObjects.modules.renderable.Particle;
import mEngine.gameObjects.modules.renderable.Skybox;
import mEngine.gameObjects.modules.renderable.light.LightSource;

import java.util.ArrayList;
import java.util.List;

public class RenderQueue {

    public List<LightSource> lightSources = new ArrayList<LightSource>();
    public Camera camera;
    private Skybox skybox;
    private List<ModuleRenderable3D> modelQueue = new ArrayList<ModuleRenderable3D>();
    private List<Particle> particleQueue = new ArrayList<Particle>();
    private List<GUIElement> guiQueue = new ArrayList<GUIElement>();

    public void addCamera(Camera camera) {

        this.camera = camera;

    }

    public void addSkybox(Skybox skybox) {

        this.skybox = skybox;

    }

    public void addModel(ModuleRenderable3D component) {

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

        GraphicsController.switchTo3D();
        if (camera != null) camera.render();
        for (ModuleRenderable3D component : modelQueue) {

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
