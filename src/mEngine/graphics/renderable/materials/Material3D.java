package mEngine.graphics.renderable.materials;

import org.lwjgl.util.vector.Vector3f;

public class Material3D extends Material {

    private Vector3f ambientReflectivity;
    private Vector3f diffuseReflectivity;
    private Vector3f specularReflectivity;
    private Vector3f emit;

    public Material3D() {
        super();
    }

    public Vector3f getAmbientReflectivity() {
        return ambientReflectivity;
    }

    public void setAmbientReflectivity(Vector3f ambientReflectivity) {
        this.ambientReflectivity = ambientReflectivity;
    }

    public Vector3f getDiffuseReflectivity() {
        return diffuseReflectivity;
    }

    public void setDiffuseReflectivity(Vector3f diffuseReflectivity) {
        this.diffuseReflectivity = diffuseReflectivity;
    }

    public Vector3f getSpecularReflectivity() {
        return specularReflectivity;
    }

    public void setSpecularReflectivity(Vector3f specularReflectivity) {
        this.specularReflectivity = specularReflectivity;
    }

    public Vector3f getEmit() {
        return emit;
    }

    public void setEmit(Vector3f emit) {
        this.emit = emit;
    }

}
