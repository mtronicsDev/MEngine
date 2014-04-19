varying vec3 vertex;
varying vec3 normal;

uniform int lightSourceCount;
uniform vec3[100] lightPositions;
uniform vec3[100] lightColors;
uniform float[100] lightStrengths;
uniform vec3[100] lightDirections;
uniform float[100]lightRadii;
uniform sampler2D texture;
uniform vec3 color;
uniform float emissiveLightStrength;

const float shininess = 90;

void main(void) {

    vec3 fragColor;

    if (emissiveLightStrength != 0) {

        if (color == vec3(0, 0, 0)) fragColor = vec3(vec3(texture2D(texture, vec2(gl_TexCoord[0]))) * emissiveLightStrength);

        else fragColor = color * emissiveLightStrength;

    } else {

        vec3 ambientLightedTextureColor;

        if (color == vec3(0, 0, 0)) {

            vec4 textureColor = texture2D(texture, vec2(gl_TexCoord[0]));
            ambientLightedTextureColor = vec3(vec3(textureColor) * 0.05);

        }

        else ambientLightedTextureColor = vec3(color * 0.05);

        for (int count = 0; count < lightSourceCount; count++) {

            vec3 lightDifference = vertex - lightPositions[count];

            vec3 lightDirection;

            /*if (lightDirections[count] == vec3(0, 0, 0))*/ lightDirection = normalize(lightDifference);

            //else lightDirection = lightDirections[count];

            float difference = length(lightDifference);

            float diffuseLightIntensity = lightStrengths[count] / difference;
            diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

            fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * lightColors[count]);

            vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

            float specularLightIntensity = max(0, dot(reflectionDirection, normal));
            specularLightIntensity = pow(specularLightIntensity, shininess);

            fragColor += specularLightIntensity * lightColors[count];

        }

        fragColor += ambientLightedTextureColor;

    }

    gl_FragColor = vec4(fragColor, 1);

}