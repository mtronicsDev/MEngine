package mEngine.gameObjects.components.renderable;

import mEngine.gameObjects.GameObject;
import mEngine.graphics.Renderer;
import mEngine.util.data.BinaryHelper;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class LightSource extends ComponentRenderable {

    public float strength;
    public float radius;
    public Vector3f position;
    public Vector4f color;
    public Vector3f direction;
    public int specularLighting;

    public LightSource(float strength) {

        this(strength, new Vector4f(255, 255, 255, 1), new Vector3f(), 0, true);

    }

    public LightSource(float strength, boolean specularLighting) {

        this(strength, new Vector4f(255, 255, 255, 1), new Vector3f(), 0, specularLighting);

    }

    public LightSource(float strength, Vector4f color) {

        this(strength, color, new Vector3f(), 0, true);

    }

    public LightSource(float strength, Vector4f color, boolean specularLighting) {

        this(strength, color, new Vector3f(), 0, specularLighting);

    }

    public LightSource(float strength, Vector4f color, Vector3f direction) {

        this(strength, color, direction, -1, true);

    }

    public LightSource(float strength, Vector4f color, Vector3f direction, boolean specularLighting) {

        this(strength, color, direction, -1, specularLighting);

    }

    public LightSource(float strength, Vector3f direction) {

        this(strength, new Vector4f(255, 255, 255, 1), direction, -1, true);

    }

    public LightSource(float strength, Vector3f direction, boolean specularLighting) {

        this(strength, new Vector4f(255, 255, 255, 1), direction, -1, specularLighting);

    }

    public LightSource(float strength, Vector3f direction, float radius) {

        this(strength, new Vector4f(255, 255, 255, 1), direction, radius, true);

    }

    public LightSource(float strength, Vector3f direction, float radius, boolean specularLighting) {

        this(strength, new Vector4f(255, 255, 255, 1), direction, radius, specularLighting);

    }

    public LightSource(float strength, Vector4f color, Vector3f direction, float radius) {

        this(strength, color, direction, radius, true);

    }

    public LightSource(float strength, Vector4f color, Vector3f direction, float radius, boolean specularLighting) {

        this.strength = strength;
        Vector3f colorIntensity = VectorHelper.divideVectorByFloat(new Vector3f(color), 255f);
        this.color = new Vector4f(colorIntensity.x, colorIntensity.y, colorIntensity.z, color.w);
        this.direction = VectorHelper.normalizeVector(direction);
        this.radius = radius;
        this.specularLighting = BinaryHelper.convertToBinaryInteger(specularLighting);

    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);
        position = parent.position;

    }

    public void onUpdate() {

        position = parent.position;

        if (!VectorHelper.areEqual(direction, new Vector3f()) && !(radius == 0)) {

            //direction = parent.percentRotation;

        }

    }

    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addLightSource(this);

    }

}
