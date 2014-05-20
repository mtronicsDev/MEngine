varying vec3 vertex;
varying vec3 normal;

const int maxLightSourceCount = 32;

uniform float materialShininess;
uniform int lightSourceCount;
uniform int[maxLightSourceCount] lightSourceTypes;
uniform vec3[maxLightSourceCount] lightPositions;
uniform vec3[maxLightSourceCount] lightColors;
uniform float[maxLightSourceCount] lightStrengths;
uniform vec3[maxLightSourceCount] lightDirections;
uniform float[maxLightSourceCount] lightRadii;
uniform int[maxLightSourceCount] specularLighting;
uniform float[maxLightSourceCount] lightAngles;
uniform int[maxLightSourceCount] shadowThrowing;
uniform float[maxLightSourceCount] transitions;
uniform sampler2D texture;
uniform vec4 color;
uniform float emissiveLightStrength;
uniform vec3 cameraPosition;
uniform vec3[3] reflectionAssets;
uniform int materialType;
uniform float materialTransparency;

void main(void) {

    vec3 fragColor;

    vec4 previousFragmentColor;

    if (color == vec4(0, 0, 0, 0)) previousFragmentColor = texture2D(texture, vec2(gl_TexCoord[0]));

    else previousFragmentColor = color;

    previousFragmentColor.a *= materialTransparency;

    if (emissiveLightStrength != 0) fragColor = vec3(vec3(previousFragmentColor) * emissiveLightStrength);

    else {

        vec3 ambientLightedTextureColor;

        vec3 ambientColorMultiplier;

        if (reflectionAssets[0] == vec3(0, 0, 0)) ambientColorMultiplier = vec3(0.05, 0.05, 0.05);

        else ambientColorMultiplier = reflectionAssets[0];

        ambientLightedTextureColor = vec3(vec3(previousFragmentColor) * ambientColorMultiplier);

        float shininess;

        if (materialShininess == 0) shininess = 90;

        else shininess = materialShininess;

        int count = 0;

        if (materialType == 0) {



        } else if (materialType == 1) {



        } else if (materialType == 2) {

            while (count < lightSourceCount) {

                if (lightSourceTypes[count] == 0) {

                    if (lightAngles[count] == -1) {

                        vec3 lightDifference = vertex - lightPositions[count];

                        vec3 lightDirection = normalize(lightDifference);

                        float difference = length(lightDifference);

                        float diffuseLightIntensity = lightStrengths[count] / difference;
                        diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

                        fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * reflectionAssets[1] * lightColors[count]);

                        if (specularLighting[count] == 1) {

                            vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                            vec3 idealReflectionDirection = vec3(normalize(cameraPosition - vertex));

                            float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                            specularLightIntensity = pow(specularLightIntensity, shininess);

                            fragColor += vec3(specularLightIntensity * lightColors[count] * reflectionAssets[2]);

                        }

                    } else {

                        vec3 lightDifference = vertex - lightPositions[count];

                        vec3 lightDirection = normalize(lightDifference);

                        float actualAngle = acos(dot(lightDirections[count], lightDirection));

                        if (actualAngle <= lightAngles[count]) {

                            float difference = length(lightDifference);

                            float diffuseLightIntensity = lightStrengths[count] / difference;
                            diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

                            fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * reflectionAssets[1] * lightColors[count]);

                            if (specularLighting[count] == 1) {

                                vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                                vec3 idealReflectionDirection = vec3(normalize(cameraPosition - vertex));

                                float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                                specularLightIntensity = pow(specularLightIntensity, shininess);

                                fragColor += vec3(specularLightIntensity * lightColors[count] * reflectionAssets[2]);

                            }

                        } else {

                            if (transitions[count] != 0) {

                                float newLightAngle = lightAngles[count] * 1.5;

                                if (actualAngle <= newLightAngle) {

                                    float relativeAngleDifference = (1 - (actualAngle - lightAngles[count]) / (newLightAngle - lightAngles[count])) * transitions[count];

                                    float difference = length(lightDifference);

                                    float diffuseLightIntensity = lightStrengths[count] / difference;
                                    diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

                                    fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * reflectionAssets[1] * lightColors[count] * relativeAngleDifference);

                                    if (specularLighting[count] == 1) {

                                        vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                                        vec3 idealReflectionDirection = vec3(normalize(cameraPosition - vertex));

                                        float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                                        specularLightIntensity = pow(specularLightIntensity, shininess);

                                        fragColor += vec3(specularLightIntensity * lightColors[count] * reflectionAssets[2] * relativeAngleDifference);

                                    }

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

                            fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * reflectionAssets[1] * lightColors[count]);

                            if (specularLighting[count] == 1) {

                                vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                                vec3 idealReflectionDirection = normalize(cameraPosition - vertex);

                                float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                                specularLightIntensity = pow(specularLightIntensity, shininess);

                                fragColor += vec3(specularLightIntensity * lightColors[count] * reflectionAssets[2]);

                            }

                        } else {

                            vec3 differenceVector = vec3(-lightDirection * difference);
                            differenceVector += vertex;

                            float differenceToLightSource = length(differenceVector - lightPositions[count]);

                            if (differenceToLightSource <= lightRadii[count]) {

                                float diffuseLightIntensity = lightStrengths[count] / difference;
                                diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

                                fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * reflectionAssets[1] * lightColors[count]);

                                if (specularLighting[count] == 1) {

                                    vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                                    vec3 idealReflectionDirection = normalize(cameraPosition - vertex);

                                    float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                                    specularLightIntensity = pow(specularLightIntensity, shininess);

                                    fragColor += vec3(specularLightIntensity * lightColors[count] * reflectionAssets[2]);

                                }

                            }

                        }

                    }

                } else if (lightSourceTypes[count] == 2) {

                    vec3 lightDirection = lightDirections[count];

                    float diffuseLightIntensity = lightStrengths[count];
                    diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

                    fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * reflectionAssets[1] * lightColors[count]);

                    if (specularLighting[count] == 1) {

                        vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                        vec3 idealReflectionDirection = normalize(cameraPosition - vertex);

                        float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                        specularLightIntensity = pow(specularLightIntensity, shininess);

                        fragColor += vec3(specularLightIntensity * lightColors[count] * reflectionAssets[2]);

                    }

                }

                count++;

            }

        } else if (materialType == 3) {


        }

        fragColor += ambientLightedTextureColor;

    }

    gl_FragColor = vec4(fragColor, previousFragmentColor.a);

}