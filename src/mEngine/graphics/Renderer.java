package mEngine.graphics;

import mEngine.gameObjects.components.renderable.LightSource;
import mEngine.util.math.MathHelper;
import mEngine.util.rendering.ShaderHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import java.nio.FloatBuffer;
import java.util.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Renderer {

    //Legitimately copying all of OpenGL's render modes
    public static final int RENDER_POINTS = GL11.GL_POINTS;
    public static final int RENDER_LINES = GL11.GL_LINES;
    public static final int RENDER_LINE = GL11.GL_LINE;
    public static final int RENDER_LINE_STRIP = GL11.GL_LINE_STRIP;
    public static final int RENDER_LINE_LOOP = GL11.GL_LINE_LOOP;
    public static final int RENDER_TRIANGLES = GL11.GL_TRIANGLES;
    public static final int RENDER_TRIANGLE_STRIP = GL11.GL_TRIANGLE_STRIP;
    public static final int RENDER_TRIANGLE_FAN = GL11.GL_TRIANGLE_FAN;
    public static final int RENDER_QUADS = GL11.GL_QUADS;
    public static final int RENDER_QUAD_STRIP = GL11.GL_QUAD_STRIP;
    public static final int RENDER_POLYGON = GL11.GL_POLYGON;

    public static RenderQueue currentRenderQueue;

    public static List<Integer> displayLists = new ArrayList<Integer>();
    public static int displayListCounter = 0;

    public static void addDisplayList(List<Vector3f> vertices, List<Vector3f> normals, List<Vector2f> uvs, Texture texture, int mode) {

        int displayListHandle = glGenLists(1);

        glNewList(displayListHandle, GL_COMPILE_AND_EXECUTE);

        FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertices.size() * 3);
        FloatBuffer normalData = BufferUtils.createFloatBuffer(normals.size() * 3);
        FloatBuffer textureData = BufferUtils.createFloatBuffer(uvs.size() * 2);

        for (Vector3f vertex : vertices) {

            vertexData.put(new float[]{vertex.x, vertex.y, vertex.z});

        }

        for (Vector3f normal : normals) {

            normalData.put(new float[]{normal.x, normal.y, normal.z});

        }

        for (Vector2f uv : uvs) {

            textureData.put(new float[]{uv.x, uv.y});

        }

        vertexData.flip();
        normalData.flip();
        textureData.flip();

        int vboVertexHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        int vboNormalHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboNormalHandle);
        glBufferData(GL_ARRAY_BUFFER, normalData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        int vboTextureHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, textureData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindTexture(GL_TEXTURE_2D, texture.getTextureID());

        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0l);

        glBindBuffer(GL_ARRAY_BUFFER, vboNormalHandle);
        glNormalPointer(GL_FLOAT, 0, 0l);

        glBindBuffer(GL_ARRAY_BUFFER, vboTextureHandle);
        glTexCoordPointer(2, GL_FLOAT, 0, 0l);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_NORMAL_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        glDrawArrays(mode, 0, vertices.size());

        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        glDisableClientState(GL_NORMAL_ARRAY);
        glDisableClientState(GL_VERTEX_ARRAY);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        //texture.release();

        glDeleteBuffers(vboVertexHandle);
        glDeleteBuffers(vboNormalHandle);
        glDeleteBuffers(vboTextureHandle);

        glEndList();

        displayLists.add(displayListHandle);
        displayListCounter++;

    }

    public static void addDisplayList(List<Vector3f> vertices, List<Vector3f> normals, int mode) {

        int displayListHandle = glGenLists(1);

        glNewList(displayListHandle, GL_COMPILE_AND_EXECUTE);

        FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertices.size() * 3);
        FloatBuffer normalData = BufferUtils.createFloatBuffer(normals.size() * 3);

        for (Vector3f vertex : vertices) {

            vertexData.put(new float[]{vertex.x, vertex.y, vertex.z});

        }

        for (Vector3f normal : normals) {

            normalData.put(new float[]{normal.x, normal.y, normal.z});

        }

        vertexData.flip();
        normalData.flip();

        int vboVertexHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        int vboNormalHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboNormalHandle);
        glBufferData(GL_ARRAY_BUFFER, normalData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0l);

        glBindBuffer(GL_ARRAY_BUFFER, vboNormalHandle);
        glNormalPointer(GL_FLOAT, 0, 0l);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_NORMAL_ARRAY);

        glDrawArrays(mode, 0, vertices.size());

        glDisableClientState(GL_NORMAL_ARRAY);
        glDisableClientState(GL_VERTEX_ARRAY);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glDeleteBuffers(vboNormalHandle);
        glDeleteBuffers(vboVertexHandle);

        glEndList();

        displayLists.add(displayListHandle);
        displayListCounter++;

    }

    public static void renderObject3D(List<Vector3f> vertices, List<Vector3f> normals, List<Vector2f> uvs, Texture texture, int mode, float emissiveLightStrength) {

        ShaderHelper.useShader("lighting");

        if (GraphicsController.isBlackAndWhite) glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "color"), 1, 1, 1);
        glUniform1i(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightSourceCount"), currentRenderQueue.lightSources.size());
        emissiveLightStrength = (float) MathHelper.clamp(emissiveLightStrength, 0, 1);
        glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "emissiveLightStrength"), emissiveLightStrength);
        Vector3f cameraPosition = currentRenderQueue.camera.position;
        glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "cameraPosition"), cameraPosition.x, cameraPosition.y, cameraPosition.z);

        for (int count = 0; count < currentRenderQueue.lightSources.size(); count++) {

            LightSource lightSource = currentRenderQueue.lightSources.get(count);

            Vector3f lightPosition = lightSource.position;
            glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightPositions[" + count + "]"), lightPosition.x, lightPosition.y, lightPosition.z);

            if (GraphicsController.isBlackAndWhite) {

                glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightColors[" + count + "]"), 1, 1, 1);

            } else {

                Vector3f lightColor = new Vector3f(lightSource.color);
                glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightColors[" + count + "]"), lightColor.x, lightColor.y, lightColor.z);

            }

            Vector3f lightDirection = lightSource.direction;
            glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightDirections[" + count + "]"), lightDirection.x, lightDirection.y, lightDirection.z);

            glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightRadii[" + count + "]"), lightSource.radius);

            glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightStrengths[" + count + "]"), lightSource.strength);

        }

        FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertices.size() * 3);
        FloatBuffer normalData = BufferUtils.createFloatBuffer(normals.size() * 3);
        FloatBuffer textureData = BufferUtils.createFloatBuffer(uvs.size() * 2);

        for (Vector3f vertex : vertices) {

            vertexData.put(new float[]{vertex.x, vertex.y, vertex.z});

        }

        for (Vector3f normal : normals) {

            normalData.put(new float[]{normal.x, normal.y, normal.z});

        }

        for (Vector2f uv : uvs) {

            textureData.put(new float[]{uv.x, uv.y});

        }

        vertexData.flip();
        normalData.flip();
        textureData.flip();

        int vboVertexHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        int vboNormalHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboNormalHandle);
        glBufferData(GL_ARRAY_BUFFER, normalData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        int vboTextureHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, textureData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindTexture(GL_TEXTURE_2D, texture.getTextureID());

        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0l);

        glBindBuffer(GL_ARRAY_BUFFER, vboNormalHandle);
        glNormalPointer(GL_FLOAT, 0, 0l);

        glBindBuffer(GL_ARRAY_BUFFER, vboTextureHandle);
        glTexCoordPointer(2, GL_FLOAT, 0, 0l);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_NORMAL_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        glDrawArrays(mode, 0, vertices.size());

        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        glDisableClientState(GL_NORMAL_ARRAY);
        glDisableClientState(GL_VERTEX_ARRAY);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        //texture.release();

        glDeleteBuffers(vboVertexHandle);
        glDeleteBuffers(vboNormalHandle);
        glDeleteBuffers(vboTextureHandle);

        if (GraphicsController.isBlackAndWhite) glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "color"), 0, 0, 0);

        ShaderHelper.useNoShader();

    }

    public static void renderObject3D(int displayListIndex, boolean isTextureThere, float emissiveLightStrength) {

        ShaderHelper.useShader("lighting");

        if (GraphicsController.isBlackAndWhite || !isTextureThere) glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "color"), 1, 1, 1);
        glUniform1i(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightSourceCount"), currentRenderQueue.lightSources.size());
        emissiveLightStrength = (float) MathHelper.clamp(emissiveLightStrength, 0, 1);
        glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "emissiveLightStrength"), emissiveLightStrength);
        Vector3f cameraPosition = currentRenderQueue.camera.position;
        glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "cameraPosition"), cameraPosition.x, cameraPosition.y, cameraPosition.z);

        for (int count = 0; count < currentRenderQueue.lightSources.size(); count++) {

            LightSource lightSource = currentRenderQueue.lightSources.get(count);

            Vector3f lightPosition = lightSource.position;
            glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightPositions[" + count + "]"), lightPosition.x, lightPosition.y, lightPosition.z);

            if (GraphicsController.isBlackAndWhite) {

                glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightColors[" + count + "]"), 1, 1, 1);

            } else {

                Vector3f lightColor = new Vector3f(lightSource.color);
                glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightColors[" + count + "]"), lightColor.x, lightColor.y, lightColor.z);

            }

            Vector3f lightDirection = lightSource.direction;
            glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightDirections[" + count + "]"), lightDirection.x, lightDirection.y, lightDirection.z);

            glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightRadii[" + count + "]"), lightSource.radius);

            glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightStrengths[" + count + "]"), lightSource.strength);

        }

        int displayListHandle = displayLists.get(displayListIndex);

        glCallList(displayListHandle);

        if (GraphicsController.isBlackAndWhite || !isTextureThere) glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "color"), 0, 0, 0);

        ShaderHelper.useNoShader();

    }

    public static void renderObject3D(List<Vector3f> vertices, List<Vector3f> normals, int mode, float emissiveLightStrength) {

        ShaderHelper.useShader("lighting");

        glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "color"), 1, 1, 1);
        glUniform1i(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightSourceCount"), currentRenderQueue.lightSources.size());
        emissiveLightStrength = (float) MathHelper.clamp(emissiveLightStrength, 0, 1);
        glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "emissiveLightStrength"), emissiveLightStrength);
        Vector3f cameraPosition = currentRenderQueue.camera.position;
        glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "cameraPosition"), cameraPosition.x, cameraPosition.y, cameraPosition.z);

        for (int count = 0; count < currentRenderQueue.lightSources.size(); count++) {

            LightSource lightSource = currentRenderQueue.lightSources.get(count);

            Vector3f lightPosition = lightSource.position;
            glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightPositions[" + count + "]"), lightPosition.x, lightPosition.y, lightPosition.z);

            if (GraphicsController.isBlackAndWhite) {

                glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightColors[" + count + "]"), 1, 1, 1);

            } else {

                Vector3f lightColor = new Vector3f(lightSource.color);
                glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightColors[" + count + "]"), lightColor.x, lightColor.y, lightColor.z);

            }

            glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightRadii[" + count + "]"), lightSource.radius);

            glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightStrengths[" + count + "]"), lightSource.strength);

        }

        FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertices.size() * 3);
        FloatBuffer normalData = BufferUtils.createFloatBuffer(normals.size() * 3);

        for (Vector3f vertex : vertices) {

            vertexData.put(new float[]{vertex.x, vertex.y, vertex.z});

        }

        for (Vector3f normal : normals) {

            normalData.put(new float[]{normal.x, normal.y, normal.z});

        }

        vertexData.flip();
        normalData.flip();

        int vboVertexHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        int vboNormalHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboNormalHandle);
        glBufferData(GL_ARRAY_BUFFER, normalData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0l);

        glBindBuffer(GL_ARRAY_BUFFER, vboNormalHandle);
        glNormalPointer(GL_FLOAT, 0, 0l);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_NORMAL_ARRAY);

        glDrawArrays(mode, 0, vertices.size());

        glDisableClientState(GL_NORMAL_ARRAY);
        glDisableClientState(GL_VERTEX_ARRAY);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glDeleteBuffers(vboNormalHandle);
        glDeleteBuffers(vboVertexHandle);

        if (!GraphicsController.isBlackAndWhite) glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "color"), 0, 0, 0);

        ShaderHelper.useNoShader();

    }

    public static void renderObject2D(List<Vector2f> vertices, List<Vector2f> uvs, Texture texture, int mode) {

        FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertices.size() * 2);
        FloatBuffer textureData = BufferUtils.createFloatBuffer(uvs.size() * 2);

        for (Vector2f vertex : vertices) {

            vertexData.put(new float[]{vertex.x, vertex.y});

        }

        for (Vector2f uv : uvs) {

            textureData.put(new float[]{uv.x, uv.y});

        }

        vertexData.flip();
        textureData.flip();

        int vboVertexHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        int vboTextureHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, textureData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindTexture(GL_TEXTURE_2D, texture.getTextureID());

        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glVertexPointer(2, GL_FLOAT, 0, 0l);

        glBindBuffer(GL_ARRAY_BUFFER, vboTextureHandle);
        glTexCoordPointer(2, GL_FLOAT, 0, 0l);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        glDrawArrays(mode, 0, vertices.size());

        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        glDisableClientState(GL_VERTEX_ARRAY);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        //texture.release();

        glDeleteBuffers(vboVertexHandle);
        glDeleteBuffers(vboTextureHandle);

        ShaderHelper.useNoShader();

    }

    public static void renderObject2D(List<Vector2f> vertices, int mode) {

        FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertices.size() * 2);

        for (Vector2f vertex : vertices) {

            vertexData.put(new float[]{vertex.x, vertex.y});

        }

        vertexData.flip();

        int vboVertexHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glVertexPointer(2, GL_FLOAT, 0, 0l);

        glEnableClientState(GL_VERTEX_ARRAY);

        glDrawArrays(mode, 0, vertices.size());

        glDisableClientState(GL_VERTEX_ARRAY);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glDeleteBuffers(vboVertexHandle);

    }

}
