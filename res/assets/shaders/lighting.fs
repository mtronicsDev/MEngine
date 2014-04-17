varying vec3 vertex;
varying vec3 normal;

uniform int lightSourceCount;
uniform vec3[] lightPositions;
uniform float[] lightStrengths;
uniform sampler2D texture;
uniform vec3 color;

void main(void) {

    vec3 fragColor;

    vec4 textureColor = texture2D(texture, vec2(gl_TexCoord[0]));
    vec3 ambientLightedTextureColor;

    if (color == vec3(0, 0, 0)) ambientLightedTextureColor = vec3(vec3(textureColor) * 0.05);

    else ambientLightedTextureColor = vec3(color * 0.05);

    //don't wonder about the ifs, glsl doesn't seem to understand what lightPositions[count] means
    for (int count = 0; count < lightSourceCount; count += 1) {

        if (count == 0) {

            vec3 lightDifference = vertex - lightPositions[0];

            vec3 lightDirection = normalize(lightDifference);

            float difference = length(lightDifference);

            float diffuseLightIntensity = lightStrengths[0] / difference;
            diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

            fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity);

        } else if (count == 1) {

            vec3 lightDifference = vertex - lightPositions[1];

            vec3 lightDirection = normalize(lightDifference);

            float difference = length(lightDifference);

            float diffuseLightIntensity = lightStrengths[1] / difference;
            diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

            fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity);

        } else if (count == 2) {

            vec3 lightDifference = vertex - lightPositions[2];

            vec3 lightDirection = normalize(lightDifference);

            float difference = length(lightDifference);

            float diffuseLightIntensity = lightStrengths[2] / difference;
            diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

            fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity);

        } else if (count == 3) {

            vec3 lightDifference = vertex - lightPositions[3];

            vec3 lightDirection = normalize(lightDifference);

            float difference = length(lightDifference);

            float diffuseLightIntensity = lightStrengths[3] / difference;
            diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

            fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity);

        } else if (count == 4) {

            vec3 lightDifference = vertex - lightPositions[4];

            vec3 lightDirection = normalize(lightDifference);

            float difference = length(lightDifference);

            float diffuseLightIntensity = lightStrengths[4] / difference;
            diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

            fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity);

        } else if (count == 5) {

            vec3 lightDifference = vertex - lightPositions[5];

            vec3 lightDirection = normalize(lightDifference);

            float difference = length(lightDifference);

            float diffuseLightIntensity = lightStrengths[5] / difference;
            diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

            fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity);

        } else if (count == 6) {

            vec3 lightDifference = vertex - lightPositions[6];

            vec3 lightDirection = normalize(lightDifference);

            float difference = length(lightDifference);

            float diffuseLightIntensity = lightStrengths[6] / difference;
            diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

            fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity);

        } else if (count == 7) {

            vec3 lightDifference = vertex - lightPositions[7];

            vec3 lightDirection = normalize(lightDifference);

            float difference = length(lightDifference);

            float diffuseLightIntensity = lightStrengths[7] / difference;
            diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

            fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity);

        }

    }

    fragColor += ambientLightedTextureColor;

    gl_FragColor = vec4(fragColor, 1);

}