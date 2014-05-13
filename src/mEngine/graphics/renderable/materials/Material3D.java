package mEngine.graphics.renderable.materials;

import org.lwjgl.util.vector.Vector3f;

public class Material3D extends Material {

    public float specularHighlightStrength = 90;
    public Vector3f ambientReflectivity = new Vector3f();
    public Vector3f diffuseReflectivity = new Vector3f(0.64f, 0.64f, 0.64f);
    public Vector3f specularReflectivity = new Vector3f(0.5f, 0.5f, 0.5f);
    public Vector3f emit;

    public Material3D() {
        super();
    }

}
