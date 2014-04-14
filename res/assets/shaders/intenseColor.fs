uniform sampler2D Texture;

void main(void) {

     vec2 TexCoord = vec2(gl_TexCoord[0]);
     vec4 RGB = texture2D(Texture, TexCoord);

     gl_FragColor = RGB * RGB;

}