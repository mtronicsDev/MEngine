varying vec3 vertex;
varying vec3 normal;
//varying float shininess;

uniform vec3 modelPosition;

attribute float materialShininess;

void main(void) {

    vertex = vec3(gl_Vertex) + modelPosition;
    normal = vec3(gl_Normal);
    //shininess = materialShininess;

    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    gl_TexCoord[0] = gl_MultiTexCoord0;

}