varying vec3 vertex;
varying vec3 normal;

uniform int lightSourceCount;
uniform vec3[] lightPositions;
uniform float[] lightStrengths;
uniform sampler2D texture;

void main(void) {

    vec3 fragColor;

    vec4 textureColor = texture2D(texture, vec2(gl_TexCoord[0]));
    vec3 darkTextureColor = vec3(vec3(textureColor) * 0.05);

    for (int count = 0; count < lightSourceCount; count += 1) {

        vec3 lightDifference = vertex - lightPositions[0];

        vec3 lightDirection = normalize(lightDifference);

        float difference = length(lightDifference);

        float diffuseLightIntensity = lightStrengths[0] / difference;

        fragColor += vec3(darkTextureColor * diffuseLightIntensity);

    }

    fragColor += darkTextureColor;

    gl_FragColor = vec4(fragColor, 1);

}