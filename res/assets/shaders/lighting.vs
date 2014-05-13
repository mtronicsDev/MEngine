varying vec3 vertex;
varying vec3 normal;

uniform vec3 modelPosition;

void main(void) {

    vertex = vec3(gl_Vertex) + modelPosition;
    normal = vec3(gl_Normal);

    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    gl_TexCoord[0] = gl_MultiTexCoord0;

}