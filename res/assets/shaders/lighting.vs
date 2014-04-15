varying vec3 color;

uniform vec3 lightPosition;

const vec3 standardColor = vec3(0.03, 0, 0);
const float lightStrength = 150;

void main(void) {

    vec3 vertex = vec3(gl_Vertex);
    vec3 normal = vec3(gl_Normal * gl_NormalMatrix);

    vec3 lightDirection = normalize(vertex - lightPosition);

    vec3 lightDifference = vertex - lightPosition;
    float difference = abs(sqrt(dot(lightDifference, lightDifference)));

    float diffuseLightIntesity = lightStrength / difference;

    color = vec3(standardColor * diffuseLightIntesity);

    color += standardColor;

    gl_Position = ftransform();

}