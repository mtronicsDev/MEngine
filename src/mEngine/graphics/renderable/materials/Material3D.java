package mEngine.graphics.renderable.materials;

import org.lwjgl.util.vector.Vector3f;

public class Material3D extends Material {

    public float specularHighlightStrength = 90;
    public Vector3f ambientReflectivity;
    public Vector3f diffuseReflectivity;
    public Vector3f specularReflectivity;
    public Vector3f emit;

    public Material3D() {
        super();
    }

}
