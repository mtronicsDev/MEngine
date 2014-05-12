varying vec3 vertex;
varying vec3 normal;

varying float shininess;
varying vec3 ambientReflectivity;
varying vec3 diffuseReflectivity;
varying vec3 specularReflectivity;

uniform vec3 modelPosition;

attribute float materialShininess;
attribute vec3 materialAmbientReflectivity;
attribute vec3 materialDiffuseReflectivity;
attribute vec3 materialSpecularReflectivity;

void main(void) {

    vertex = vec3(gl_Vertex) + modelPosition;
    normal = vec3(gl_Normal);

    shininess = materialShininess;
    ambientReflectivity = materialAmbientReflectivity;
    diffuseReflectivity = materialDiffuseReflectivity;
    specularReflectivity = materialSpecularReflectivity;

    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    gl_TexCoord[0] = gl_MultiTexCoord0;

}