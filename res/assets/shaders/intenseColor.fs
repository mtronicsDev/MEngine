uniform sampler2D texture;

void main(void) {

    vec2 textureCoord = vec2(gl_TexCoord[0]);
    vec4 textureColor = texture2D(texture, textureCoord);

    gl_FragColor = textureColor * textureColor;

}