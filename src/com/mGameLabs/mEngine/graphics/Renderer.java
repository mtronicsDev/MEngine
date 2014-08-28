package com.mGameLabs.mEngine.graphics;

import com.mGameLabs.mEngine.gameObjects.components.renderable.light.DirectionalLightSource;
import com.mGameLabs.mEngine.gameObjects.components.renderable.light.LightSource;
import com.mGameLabs.mEngine.gameObjects.components.renderable.light.SpotLightSource;
import com.mGameLabs.mEngine.graphics.renderable.materials.Material2D;
import com.mGameLabs.mEngine.graphics.renderable.materials.Material3D;
import com.mGameLabs.mEngine.util.math.MathHelper;
import com.mGameLabs.mEngine.util.rendering.ShaderHelper;
import com.mGameLabs.mEngine.util.rendering.TextureHelper;
import com.mGameLabs.mEngine.util.resources.ResourceHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import java.io.File;
import java.nio.FloatBuffer;
import java.util.List;

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

    public static int displayListCounter = 0;

    public static void addDisplayList(List<Vector3f> vertices, List<Vector3f> normals, List<Vector2f> uvs, Material3D material, int mode) {

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

        material.bind();

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

        glDeleteBuffers(vboVertexHandle);
        glDeleteBuffers(vboNormalHandle);
        glDeleteBuffers(vboTextureHandle);

        org.newdawn.slick.opengl.TextureImpl.bindNone();

        glEndList();

        displayListCounter++;

    }

    public static void changeTexture(int displayListIndex, List<Vector3f> vertices, List<Vector3f> normals, List<Vector2f> uvs, String textureName, int mode) {

        int displayListHandle = displayListIndex + 1;

        File textureFile = ResourceHelper.getResource(textureName, ResourceHelper.RES_TEXTURE);

        if (!textureFile.exists()) {

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

            org.newdawn.slick.opengl.TextureImpl.bindNone();

            glEndList();

        } else {

            Texture texture = TextureHelper.getTexture(textureName).getTexture();

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

            glDeleteBuffers(vboVertexHandle);
            glDeleteBuffers(vboNormalHandle);
            glDeleteBuffers(vboTextureHandle);

            org.newdawn.slick.opengl.TextureImpl.bindNone();

            glEndList();

        }

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

        org.newdawn.slick.opengl.TextureImpl.bindNone();

        glEndList();

        displayListCounter++;

    }

    public static void renderObject3D(List<Vector3f> vertices, List<Vector3f> normals, List<Vector2f> uvs, Material3D material, int mode, float emissiveLightStrength) {

        ShaderHelper.useShader("lighting");

        if (GraphicsController.isBlackAndWhite)
            glUniform4f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "color"), 1, 1, 1, 1);
        glUniform1i(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightSourceCount"), currentRenderQueue.lightSources.size());
        emissiveLightStrength = (float) MathHelper.clamp(emissiveLightStrength, 0, 1);
        glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "emissiveLightStrength"), emissiveLightStrength);
        Vector3f cameraPosition = currentRenderQueue.camera.position;
        glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "cameraPosition"), cameraPosition.x, cameraPosition.y, cameraPosition.z);

        glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "materialShininess"), material.specularHighlightStrength);
        glUniform1i(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "materialType"), material.type);
        glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "materialTransparency"), material.color.a);

        Vector3f ambientReflectivity = material.ambientReflectivity;
        glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "reflectionAssets[0]"), ambientReflectivity.x, ambientReflectivity.y, ambientReflectivity.z);

        Vector3f diffuseReflectivity = material.diffuseReflectivity;
        glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "reflectionAssets[1]"), diffuseReflectivity.x, diffuseReflectivity.y, diffuseReflectivity.z);

        Vector3f specularReflectivity = material.specularReflectivity;
        glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "reflectionAssets[2]"), specularReflectivity.x, specularReflectivity.y, specularReflectivity.z);

        for (int count = 0; count < currentRenderQueue.lightSources.size(); count++) {

            LightSource lightSource = currentRenderQueue.lightSources.get(count);

            Vector3f lightPosition = lightSource.position;
            glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightPositions[" + count + "]"), lightPosition.x, lightPosition.y, lightPosition.z);

            Vector3f lightDirection = lightSource.direction;
            glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightDirections[" + count + "]"), lightDirection.x, lightDirection.y, lightDirection.z);

            glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightStrengths[" + count + "]"), lightSource.strength);

            glUniform1i(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "specularLighting[" + count + "]"), lightSource.specularLighting);

            glUniform1i(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "shadowThrowing[" + count + "]"), lightSource.shadowThrowing);

            if (GraphicsController.isBlackAndWhite) {

                glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightColors[" + count + "]"), 1, 1, 1);

            } else {

                Vector3f lightColor = new Vector3f(lightSource.color);
                glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightColors[" + count + "]"), lightColor.x, lightColor.y, lightColor.z);

            }

            if (lightSource instanceof SpotLightSource) {

                SpotLightSource spotLightSource = (SpotLightSource) lightSource;

                glUniform1i(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightSourceTypes[" + count + "]"), 0);

                glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightAngles[" + count + "]"), spotLightSource.angle);

                glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "transitions[" + count + "]"), spotLightSource.transition);

            } else if (lightSource instanceof DirectionalLightSource) {

                DirectionalLightSource directionalLightSource = (DirectionalLightSource) lightSource;

                glUniform1i(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightSourceTypes[" + count + "]"), 1);

                glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightRadii[" + count + "]"), directionalLightSource.radius);

            } else {

                glUniform1i(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightSourceTypes[" + count + "]"), 2);

            }

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

        material.bind();

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

        glDeleteBuffers(vboVertexHandle);
        glDeleteBuffers(vboNormalHandle);
        glDeleteBuffers(vboTextureHandle);

        if (GraphicsController.isBlackAndWhite)
            glUniform4f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "color"), 0, 0, 0, 0);

        org.newdawn.slick.opengl.TextureImpl.bindNone();

        ShaderHelper.useNoShader();

    }

    public static void renderObject3D(int displayListIndex, Vector3f modelPosition, Vector3f modelRotation, Material3D material, float emissiveLightStrength) {

        ShaderHelper.useShader("lighting");

        if (GraphicsController.isBlackAndWhite || !material.hasTexture())
            glUniform4f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "color"), 1, 1, 1, 1);
        glUniform1i(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightSourceCount"), currentRenderQueue.lightSources.size());
        emissiveLightStrength = (float) MathHelper.clamp(emissiveLightStrength, 0, 1);
        glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "emissiveLightStrength"), emissiveLightStrength);
        Vector3f cameraPosition = currentRenderQueue.camera.position;
        glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "cameraPosition"), cameraPosition.x, cameraPosition.y, cameraPosition.z);
        glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "modelPosition"), modelPosition.x, modelPosition.y, modelPosition.z);

        glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "materialShininess"), material.specularHighlightStrength);
        glUniform1i(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "materialType"), material.type);
        glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "materialTransparency"), material.color.a);

        Vector3f ambientReflectivity = material.ambientReflectivity;
        glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "reflectionAssets[0]"), ambientReflectivity.x, ambientReflectivity.y, ambientReflectivity.z);

        Vector3f diffuseReflectivity = material.diffuseReflectivity;
        glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "reflectionAssets[1]"), diffuseReflectivity.x, diffuseReflectivity.y, diffuseReflectivity.z);

        Vector3f specularReflectivity = material.specularReflectivity;
        glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "reflectionAssets[2]"), specularReflectivity.x, specularReflectivity.y, specularReflectivity.z);

        for (int count = 0; count < currentRenderQueue.lightSources.size(); count++) {

            LightSource lightSource = currentRenderQueue.lightSources.get(count);

            Vector3f lightPosition = lightSource.position;
            glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightPositions[" + count + "]"), lightPosition.x, lightPosition.y, lightPosition.z);

            Vector3f lightDirection = lightSource.direction;
            glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightDirections[" + count + "]"), lightDirection.x, lightDirection.y, lightDirection.z);

            glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightStrengths[" + count + "]"), lightSource.strength);

            glUniform1i(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "specularLighting[" + count + "]"), lightSource.specularLighting);

            glUniform1i(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "shadowThrowing[" + count + "]"), lightSource.shadowThrowing);

            if (GraphicsController.isBlackAndWhite) {

                glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightColors[" + count + "]"), 1, 1, 1);

            } else {

                Vector3f lightColor = new Vector3f(lightSource.color);
                glUniform3f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightColors[" + count + "]"), lightColor.x, lightColor.y, lightColor.z);

            }

            if (lightSource instanceof SpotLightSource) {

                SpotLightSource spotLightSource = (SpotLightSource) lightSource;

                glUniform1i(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightSourceTypes[" + count + "]"), 0);

                glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightAngles[" + count + "]"), spotLightSource.angle);

                glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "transitions[" + count + "]"), spotLightSource.transition);

            } else if (lightSource instanceof DirectionalLightSource) {

                DirectionalLightSource directionalLightSource = (DirectionalLightSource) lightSource;

                glUniform1i(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightSourceTypes[" + count + "]"), 1);

                glUniform1f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightRadii[" + count + "]"), directionalLightSource.radius);

            } else {

                glUniform1i(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "lightSourceTypes[" + count + "]"), 2);

            }

        }

        glPushMatrix();

        glTranslatef(modelPosition.x, modelPosition.y, modelPosition.z);

        /*glRotatef(modelRotation.x, 1, 0, 0);
        glRotatef(modelRotation.y, 0, 1, 0);
        glRotatef(modelRotation.z, 0, 0, 1);*/

        glCallList(displayListIndex + 1);

        glPopMatrix();

        if (GraphicsController.isBlackAndWhite || !material.hasTexture())
            glUniform4f(glGetUniformLocation(ShaderHelper.shaderPrograms.get("lighting"), "color"), 0, 0, 0, 0);

        org.newdawn.slick.opengl.TextureImpl.bindNone();

        ShaderHelper.useNoShader();

    }

    public static void renderObject2D(List<Vector2f> vertices, List<Vector2f> uvs, Material2D material, int mode) {

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

        material.bind();

        int vboVertexHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        int vboTextureHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, textureData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

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

        glDeleteBuffers(vboVertexHandle);
        glDeleteBuffers(vboTextureHandle);

        org.newdawn.slick.opengl.TextureImpl.bindNone();

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