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

    //don't wonder about the ifs, glsl doesn't seem to understand what lightPosition[count] means
    for (int count = 0; count < lightSourceCount; count += 1) {

        if (count == 0) {

            vec3 lightDifference = vertex - lightPositions[0];

            vec3 lightDirection = normalize(lightDifference);

            float difference = length(lightDifference);

            float diffuseLightIntensity = lightStrengths[0] / difference;

            fragColor += vec3(darkTextureColor * diffuseLightIntensity);

        } else if (count == 1) {

            vec3 lightDifference = vertex - lightPositions[1];

            vec3 lightDirection = normalize(lightDifference);

            float difference = length(lightDifference);

            float diffuseLightIntensity = lightStrengths[1] / difference;

            fragColor += vec3(darkTextureColor * diffuseLightIntensity);

        } else if (count == 2) {

             vec3 lightDifference = vertex - lightPositions[2];

             vec3 lightDirection = normalize(lightDifference);

             float difference = length(lightDifference);

             float diffuseLightIntensity = lightStrengths[2] / difference;

             fragColor += vec3(darkTextureColor * diffuseLightIntensity);

        } else if (count == 3) {

              vec3 lightDifference = vertex - lightPositions[3];

              vec3 lightDirection = normalize(lightDifference);

              float difference = length(lightDifference);

              float diffuseLightIntensity = lightStrengths[3] / difference;

              fragColor += vec3(darkTextureColor * diffuseLightIntensity);

        } else if (count == 4) {

             vec3 lightDifference = vertex - lightPositions[4];

             vec3 lightDirection = normalize(lightDifference);

             float difference = length(lightDifference);

             float diffuseLightIntensity = lightStrengths[4] / difference;

             fragColor += vec3(darkTextureColor * diffuseLightIntensity);

         } else if (count == 5) {

              vec3 lightDifference = vertex - lightPositions[5];

              vec3 lightDirection = normalize(lightDifference);

              float difference = length(lightDifference);

              float diffuseLightIntensity = lightStrengths[5] / difference;

              fragColor += vec3(darkTextureColor * diffuseLightIntensity);

         } else if (count == 6) {

               vec3 lightDifference = vertex - lightPositions[6];

               vec3 lightDirection = normalize(lightDifference);

               float difference = length(lightDifference);

               float diffuseLightIntensity = lightStrengths[6] / difference;

               fragColor += vec3(darkTextureColor * diffuseLightIntensity);

         } else if (count == 6) {

                 vec3 lightDifference = vertex - lightPositions[7];

                 vec3 lightDirection = normalize(lightDifference);

                 float difference = length(lightDifference);

                 float diffuseLightIntensity = lightStrengths[7] / difference;

                 fragColor += vec3(darkTextureColor * diffuseLightIntensity);

        }

    }

    fragColor += darkTextureColor;

    gl_FragColor = vec4(fragColor, 1);

}