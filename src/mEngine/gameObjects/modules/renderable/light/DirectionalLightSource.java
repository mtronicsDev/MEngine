package mEngine.gameObjects.modules.renderable.light;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class DirectionalLightSource extends LightSource {

    public float radius;

    public DirectionalLightSource(float strength, Vector3f direction) {

        this(strength, new Vector4f(255, 255, 255, 1), direction, -1);

    }

    public DirectionalLightSource(float strength, Vector3f direction, float radius) {

        this(strength, new Vector4f(255, 255, 255, 1), direction, radius);

    }

    public DirectionalLightSource(float strength, Vector4f color, Vector3f direction) {

        this(strength, color, direction, -1);

    }

    public DirectionalLightSource(float strength, Vector4f color, Vector3f direction, float radius) {

        super(strength, color, direction);

        this.radius = radius;

    }

}
