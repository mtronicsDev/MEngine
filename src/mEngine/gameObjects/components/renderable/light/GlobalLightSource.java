package mEngine.gameObjects.components.renderable.light;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class GlobalLightSource extends LightSource {

    public GlobalLightSource(float strength, Vector3f direction) {

        this(strength, new Vector4f(1, 1, 1, 1), direction);

    }

    public GlobalLightSource(float strength, Vector4f color, Vector3f direction) {

        this(strength, color, true, direction);

    }

    public GlobalLightSource(float strength, boolean specularLighting, Vector3f direction) {

        this(strength, new Vector4f(1, 1, 1, 1), specularLighting, direction);

    }

    public GlobalLightSource(float strength, Vector4f color, boolean specularLighting, Vector3f direction) {

        super(strength, color, direction, specularLighting);

    }

}
