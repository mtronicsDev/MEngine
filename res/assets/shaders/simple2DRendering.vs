void main(void) {

    gl_Position = vec4(gl_Vertex);
    gl_TextCoord[0] = gl_MultiTexCoord;

}