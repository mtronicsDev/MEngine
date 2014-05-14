varying vec3 vertex;
varying vec3 normal;

uniform float shininess;
uniform vec3 ambientReflectivity;
uniform vec3 diffuseReflectivity;
uniform vec3 specularReflectivity;
uniform int lightSourceCount;
uniform int[32] lightSourceTypes;
uniform vec3[32] lightPositions;
uniform vec3[32] lightColors;
uniform float[32] lightStrengths;
uniform vec3[32] lightDirections;
uniform float[32] lightRadii;
uniform int[32] specularLighting;
uniform float[32] lightAngles;
uniform int[32] shadowThrowing;
uniform float[32] transitions;
uniform sampler2D texture;
uniform vec4 color;
uniform float emissiveLightStrength;
uniform vec3 cameraPosition;

void main(void) {

    vec3 fragColor;

    vec4 previousFragmentColor;

    if (color == vec4(0, 0, 0, 0)) previousFragmentColor = texture2D(texture, vec2(gl_TexCoord[0]));

    else previousFragmentColor = color;

    if (emissiveLightStrength != 0) {

        fragColor = vec3(vec3(previousFragmentColor) * emissiveLightStrength);

    } else {

        vec3 ambientLightedTextureColor;

        vec3 ambientColorMultiplier;

        if (ambientReflectivity == vec3(0, 0, 0)) ambientColorMultiplier = vec3(0.05, 0.05, 0.05);

        else ambientColorMultiplier = ambientReflectivity;

        ambientLightedTextureColor = vec3(vec3(previousFragmentColor) * ambientColorMultiplier);

        int count = 0;

        while (count < lightSourceCount) {

            if (lightSourceTypes[count] == 0) {

                if (lightAngles[count] == -1) {

                    vec3 lightDifference = vertex - lightPositions[count];

                    vec3 lightDirection = normalize(lightDifference);

                    float difference = length(lightDifference);

                    float diffuseLightIntensity = lightStrengths[count] / difference;
                    diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

                    fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * diffuseReflectivity * lightColors[count]);

                    if (specularLighting[count] == 1) {

                        vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                        vec3 idealReflectionDirection = vec3(normalize(cameraPosition - vertex));

                        float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                        specularLightIntensity = pow(specularLightIntensity, shininess);

                        fragColor += vec3(specularLightIntensity * lightColors[count] * specularReflectivity);

                    }

                } else {

                    vec3 lightDifference = vertex - lightPositions[count];

                    vec3 lightDirection = normalize(lightDifference);

                    float actualAngle = acos(dot(lightDirections[count], lightDirection));

                    if (actualAngle <= lightAngles[count]) {

                        float difference = length(lightDifference);

                        float diffuseLightIntensity = lightStrengths[count] / difference;
                        diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

                        fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * diffuseReflectivity * lightColors[count]);

                        if (specularLighting[count] == 1) {

                            vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                            vec3 idealReflectionDirection = vec3(normalize(cameraPosition - vertex));

                            float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                            specularLightIntensity = pow(specularLightIntensity, shininess);

                            fragColor += vec3(specularLightIntensity * lightColors[count] * specularReflectivity);

                        }

                    } else if (transitions[count] != 0) {

                        float newLightAngle = lightAngles[count] + lightAngles[count] * 0.5;

                        if (actualAngle <= newLightAngle) {

                            float relativeAngleDifference = (1 - (actualAngle - lightAngles[count]) / (newLightAngle - lightAngles[count])) * transitions[count];

                            float difference = length(lightDifference);

                            float diffuseLightIntensity = lightStrengths[count] / difference;
                            diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

                            fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * diffuseReflectivity * lightColors[count] * relativeAngleDifference);

                            if (specularLighting[count] == 1) {

                                vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                                vec3 idealReflectionDirection = vec3(normalize(cameraPosition - vertex));

                                float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                                specularLightIntensity = pow(specularLightIntensity, shininess);

                                fragColor += vec3(specularLightIntensity * lightColors[count] * specularReflectivity * relativeAngleDifference);

                            }

                        }

                    }

                }

            } else if (lightSourceTypes[count] == 1) {

                vec3 lightDirection = lightDirections[count];

                float difference = dot(lightDirection, vertex) + dot(-lightDirection, lightPositions[count]);

                if (difference > 0) {

                    if (lightRadii[count] == -1) {

                        float diffuseLightIntensity = lightStrengths[count] / difference;
                        diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

                        fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * diffuseReflectivity * lightColors[count]);

                        if (specularLighting[count] == 1) {

                            vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                            vec3 idealReflectionDirection = normalize(cameraPosition - vertex);

                            float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                            specularLightIntensity = pow(specularLightIntensity, shininess);

                            fragColor += vec3(specularLightIntensity * lightColors[count] * specularReflectivity);

                        }

                    } else {

                        vec3 differenceVector = vec3(-lightDirection * difference);
                        differenceVector += vertex;

                        float differenceToLightSource = length(differenceVector - lightPositions[count]);

                        if (differenceToLightSource <= lightRadii[count]) {

                            float diffuseLightIntensity = lightStrengths[count] / difference;
                            diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

                            fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * diffuseReflectivity * lightColors[count]);

                            if (specularLighting[count] == 1) {

                                vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                                vec3 idealReflectionDirection = normalize(cameraPosition - vertex);

                                float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                                specularLightIntensity = pow(specularLightIntensity, shininess);

                                fragColor += vec3(specularLightIntensity * lightColors[count] * specularReflectivity);

                            }

                        }

                    }

                }

            } else if (lightSourceTypes[count] == 2) {

                vec3 lightDirection = lightDirections[count];

                float diffuseLightIntensity = lightStrengths[count];
                diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

                vec3 diffuseComponents = vec3(diffuseLightIntensity * diffuseReflectivity);
                fragColor += vec3(ambientLightedTextureColor * diffuseComponents * lightColors[count]);

                if (specularLighting[count] == 1) {

                    vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                    vec3 idealReflectionDirection = normalize(cameraPosition - vertex);

                    float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                    specularLightIntensity = pow(specularLightIntensity, shininess);

                    fragColor += vec3(specularLightIntensity * lightColors[count] * specularReflectivity);

                }

            }

            count++;

        }

        fragColor += ambientLightedTextureColor;

    }

    gl_FragColor = vec4(fragColor, previousFragmentColor.a);

}