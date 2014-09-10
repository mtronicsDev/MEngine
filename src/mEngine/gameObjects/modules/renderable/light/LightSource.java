package mEngine.gameObjects.modules.renderable.light;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.renderable.ModuleRenderable;
import mEngine.graphics.Renderer;
import mEngine.util.data.BinaryHelper;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public abstract class LightSource extends ModuleRenderable {

    public float strength;
    public Vector3f position;
    public Vector4f color;
    public int specularLighting = 1;
    public Vector3f direction;
    public boolean dependent = true;
    public int shadowThrowing = 1;

    public LightSource(float strength, Vector4f color, Vector3f direction) {

        this.strength = strength;
        Vector3f colorIntensity = VectorHelper.divideVectorByFloat(new Vector3f(color), 255f);
        this.color = new Vector4f(colorIntensity.x, colorIntensity.y, colorIntensity.z, color.w);
        this.direction = direction;

    }

    public LightSource setDependent(boolean dependent) {

        this.dependent = dependent;
        return this;

    }

    public LightSource setSpecularLighting(boolean specularLighting) {

        this.specularLighting = BinaryHelper.convertToBinaryInteger(specularLighting);
        return this;

    }

    public LightSource setShadowThrowing(boolean shadowThrowing) {

        this.shadowThrowing = BinaryHelper.convertToBinaryInteger(shadowThrowing);
        return this;

    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);
        position = parent.position;

    }

    public void onUpdate() {

        position = parent.position;

        if (dependent)
            direction = new Vector3f(parent.percentRotation.x, parent.percentRotation.y, -parent.percentRotation.z);

    }

    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addLightSource(this);

    }

}
