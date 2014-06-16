uniform vec4 color;
uniform sampler2D texture;
uniform int hasTexture;

void main(void) {

    vec4 fragColor;

    if (hasTexture == 1) fragColor = texture2D(texture, vec2(gl_TexCoord[0]));

    else fragColor = color;

    gl_FragColor = fragColor;

}