package mEngine.gameObjects.components.renderable.light;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.renderable.ComponentRenderable;
import mEngine.graphics.Renderer;
import mEngine.util.data.BinaryHelper;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public abstract class LightSource extends ComponentRenderable {

    public float strength;
    public Vector3f position;
    public Vector4f color;
    public int specularLighting;
    public Vector3f direction;

    public LightSource(float strength, Vector4f color, Vector3f direction, boolean specularLighting) {

        this.strength = strength;
        Vector3f colorIntensity = VectorHelper.divideVectorByFloat(new Vector3f(color), 255f);
        this.color = new Vector4f(colorIntensity.x, colorIntensity.y, colorIntensity.z, color.w);
        this.specularLighting = BinaryHelper.convertToBinaryInteger(specularLighting);
        this.direction = direction;

    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);
        position = parent.position;

    }

    public void onUpdate() {

        position = parent.position;

        direction = new Vector3f(parent.percentRotation.x, parent.percentRotation.y, -parent.percentRotation.z);

    }

    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addLightSource(this);

    }

}
