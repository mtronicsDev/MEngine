package mEngine.gameObjects.components.renderable.light;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class SpotLightSource extends LightSource {

    public Vector3f direction;
    public float angle;

    public SpotLightSource(float strength) {

        this(strength, new Vector4f(1, 1, 1, 1), true);

    }

    public SpotLightSource(float strength, Vector4f color) {

        this(strength, color, true);

    }

    public SpotLightSource(float strength, boolean specularLighting) {

        this(strength, new Vector4f(1, 1, 1, 1), specularLighting);

    }

    public SpotLightSource(float strength, Vector4f color, boolean specularLighting) {

        this(strength, color, specularLighting, new Vector3f(0, -1, 0), -1);

    }
    
    public SpotLightSource(float strength, Vector3f direction, float angle) {

        this(strength, new Vector4f(1, 1, 1, 1), direction, angle);

    }

    public SpotLightSource(float strength, Vector4f color, Vector3f direction, float angle) {

        this(strength, color, true, direction, angle);

    }

    public SpotLightSource(float strength, boolean specularLighting, Vector3f direction, float angle) {

        this(strength, new Vector4f(1, 1, 1, 1), specularLighting, direction, angle);

    }

    public SpotLightSource(float strength, Vector4f color, boolean specularLighting, Vector3f direction, float angle) {

        super(strength, color, specularLighting);

        this.direction = direction;

        if (angle == -1) this.angle = -1;

        else this.angle = (float) Math.toRadians(angle);

    }

    public void onUpdate() {

        super.onUpdate();

        direction = new Vector3f(parent.percentRotation.x, parent.percentRotation.y, -parent.percentRotation.z);

    }

}
