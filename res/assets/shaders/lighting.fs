varying vec3 vertex;
varying vec3 normal;

uniform vec3 lightPosition;
uniform float lightStrength;
uniform sampler2D texture;

void main(void) {

    vec3 fragColor;

    vec4 textureColor = texture2D(texture, vec2(gl_TexCoord[0]));
    vec3 darkTextureColor = vec3(vec3(textureColor) * 0.03);

    vec3 lightDirection = normalize(vertex - lightPosition);

    vec3 lightDifference = vertex - lightPosition;
    float difference = abs(sqrt(dot(lightDifference, lightDifference)));

    float diffuseLightIntensity = lightStrength / difference;

    fragColor = vec3(darkTextureColor * diffuseLightIntensity);

    fragColor += darkTextureColor;

    gl_FragColor = vec4(fragColor, 1);

}