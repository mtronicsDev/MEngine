package mEngine.gameObjects.components.renderable.light;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class DirectionalLightSource extends LightSource {

    public float radius;

    public DirectionalLightSource(float strength, Vector3f direction) {

        this(strength, new Vector4f(1, 1, 1, 1), direction, -1);

    }

    public DirectionalLightSource(float strength, Vector3f direction, float radius) {

        this(strength, new Vector4f(1, 1, 1, 1), direction, radius);

    }

    public DirectionalLightSource(float strength, Vector4f color, Vector3f direction) {

        this(strength, color, true, direction, -1);

    }

    public DirectionalLightSource(float strength, Vector4f color, Vector3f direction, float radius) {

        this(strength, color, true, direction, radius);

    }

    public DirectionalLightSource(float strength, boolean specularLighting, Vector3f direction) {

        this(strength, new Vector4f(1, 1, 1, 1), specularLighting, direction, -1);

    }

    public DirectionalLightSource(float strength, boolean specularLighting, Vector3f direction, float radius) {

        this(strength, new Vector4f(1, 1, 1, 1), specularLighting, direction, radius);

    }

    public DirectionalLightSource(float strength, Vector4f color, boolean specularLighting, Vector3f direction) {

        this(strength, color, specularLighting, direction, -1);

    }

    public DirectionalLightSource(float strength, Vector4f color, boolean specularLighting, Vector3f direction, float radius) {

        super(strength, color, direction, specularLighting);

        this.radius = radius;

    }

}
