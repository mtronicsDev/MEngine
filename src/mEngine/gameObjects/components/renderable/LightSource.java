package mEngine.gameObjects.components.renderable;

import mEngine.gameObjects.GameObject;
import mEngine.graphics.Renderer;
import org.lwjgl.util.vector.Vector3f;

public class LightSource extends ComponentRenderable {

    public float strength;
    public Vector3f position;
    public Vector3f color;

    public LightSource(float strength) {

        this.strength = strength;
        color = new Vector3f(1, 1, 1);

    }

    public LightSource(float strength, Vector3f color) {

        this.strength = strength;
        this.color = color;

    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);
        position = parent.position;

    }

    public void onUpdate() {

        position = parent.position;

    }

    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addLightSource(this);

    }

}
