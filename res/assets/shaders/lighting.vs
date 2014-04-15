varying vec3 vertex;
varying vec3 normal;

void main(void) {

    vertex = vec3(gl_Vertex);
    normal = vec3(gl_Normal * gl_NormalMatrix);

    gl_Position = ftransform();
    gl_TexCoord[0] = gl_MultiTexCoord0;

}