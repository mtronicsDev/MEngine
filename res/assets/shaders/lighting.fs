varying vec3 vertex;
varying vec3 normal;

uniform int lightSourceCount;
uniform vec3[8] lightPositions;
uniform vec3[8] lightColors;
uniform float[8] lightStrengths;
uniform vec3[8] lightDirections;
uniform float[8]lightRadii;
uniform sampler2D texture;
uniform vec3 color;
uniform float emissiveLightStrength;
uniform vec3 cameraPosition;

const float shininess = 50;
const float ambientColorMultiplier = 0.05;

void main(void) {

    vec3 fragColor;

    if (emissiveLightStrength != 0) {

        if (color == vec3(0, 0, 0)) fragColor = vec3(vec3(texture2D(texture, vec2(gl_TexCoord[0]))) * emissiveLightStrength);

        else fragColor = color * emissiveLightStrength;

    } else {

        vec3 ambientLightedTextureColor;

        if (color == vec3(0, 0, 0)) {

            vec4 textureColor = texture2D(texture, vec2(gl_TexCoord[0]));
            ambientLightedTextureColor = vec3(vec3(textureColor) * ambientColorMultiplier);

        }

        else ambientLightedTextureColor = vec3(color * ambientColorMultiplier);

        for (int count = 0; count < lightSourceCount; count++) {

            if (lightRadii[count] == 0) {

                vec3 lightDifference = vertex - lightPositions[count];

                vec3 lightDirection = normalize(lightDifference);

                float difference = length(lightDifference);

                float diffuseLightIntensity = lightStrengths[count] / difference;
                diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

                fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * lightColors[count]);

                vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                vec3 idealReflectionDirection = vec3(normalize(cameraPosition - vertex));

                float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                specularLightIntensity = pow(specularLightIntensity, shininess);

                fragColor += specularLightIntensity * lightColors[count];

            } else {

                vec3 lightDirection = lightDirections[count];

                float difference = dot(lightDirection, vertex) + dot(-lightDirection, lightPositions[count]);

                if (difference > 0) {

                    if (lightRadii[count] == -1) {

                        float diffuseLightIntensity = lightStrengths[count] / difference;
                        diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

                        fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * lightColors[count]);

                        vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                        vec3 idealReflectionDirection = normalize(cameraPosition - vertex);

                        float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                        specularLightIntensity = pow(specularLightIntensity, shininess);

                        fragColor += specularLightIntensity * lightColors[count];

                    } else {

                        vec3 differenceVector = normalize(vec3(lightDirection * difference));

                    }

                }

            }

        }

        fragColor += ambientLightedTextureColor;

    }

    gl_FragColor = vec4(fragColor, 1);

}