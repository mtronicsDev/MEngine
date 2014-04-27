varying vec3 vertex;
varying vec3 normal;

uniform int lightSourceCount;
uniform vec3[32] lightPositions;
uniform vec3[32] lightColors;
uniform float[32] lightStrengths;
uniform vec3[32] lightDirections;
uniform float[32]lightRadii;
uniform sampler2D texture;
uniform vec4 color;
uniform float emissiveLightStrength;
uniform vec3 cameraPosition;

const float shininess = 50;
const float ambientColorMultiplier = 0.05;

void main(void) {

    vec3 fragColor;

    vec4 previousFragmentColor;

    if (color == vec4(0, 0, 0, 0)) previousFragmentColor = texture2D(texture, vec2(gl_TexCoord[0]));

    else previousFragmentColor = color;

    if (emissiveLightStrength != 0) {

        fragColor = vec3(vec3(previousFragmentColor) * emissiveLightStrength);

    } else {

        vec3 ambientLightedTextureColor;

        ambientLightedTextureColor = vec3(vec3(previousFragmentColor) * ambientColorMultiplier);

        int count = 0;

        while (count < lightSourceCount) {

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

                        vec3 differenceVector = vec3(-lightDirection * difference);
                        differenceVector += vertex;

                        float differenceToLightSource = length(differenceVector - lightPositions[count]);

                        if (differenceToLightSource <= lightRadii[count]) {

                            float diffuseLightIntensity = lightStrengths[count] / difference;
                            diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

                            fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * lightColors[count]);

                            vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                            vec3 idealReflectionDirection = normalize(cameraPosition - vertex);

                            float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                            specularLightIntensity = pow(specularLightIntensity, shininess);

                            fragColor += specularLightIntensity * lightColors[count];

                        }

                    }

                }

            }

            count++;

        }

        fragColor += ambientLightedTextureColor;

    }

    gl_FragColor = vec4(fragColor, previousFragmentColor.w);

}