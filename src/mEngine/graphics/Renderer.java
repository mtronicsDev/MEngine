package mEngine.graphics;

import mEngine.util.resources.ResourceHelper;
import mEngine.util.threading.ThreadHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    public static void initializeShaders() {

        int shaderProgram = glCreateProgram();
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);

        StringBuilder vertexShaderSource = new StringBuilder();
        StringBuilder fragmentShaderSource = new StringBuilder();

        try {

            BufferedReader vertexReader = new BufferedReader(new FileReader(ResourceHelper.getResource("shader", ResourceHelper.RES_SHADER_V)));
            BufferedReader fragmentReader = new BufferedReader(new FileReader(ResourceHelper.getResource("shader", ResourceHelper.RES_SHADER_F)));
            String line;

            while((line = vertexReader.readLine()) != null) {

                vertexShaderSource.append(line + "\n");

            }

            vertexReader.close();

            while((line = fragmentReader.readLine()) != null) {

                fragmentShaderSource.append(line + "\n");

            }

            fragmentReader.close();

        } catch (IOException e) {

            e.printStackTrace();
            ThreadHelper.stopAllThreads();
            System.exit(1);

        }

        glShaderSource(vertexShader, vertexShaderSource);
        glCompileShader(vertexShader);

        glShaderSource(fragmentShader, fragmentShaderSource);
        glCompileShader(fragmentShader);

        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);

        glLinkProgram(shaderProgram);
        glValidateProgram(shaderProgram);
        glUseProgram(shaderProgram);

    }

    public static void renderObject3D(List<Vector3f> vertices, List<Vector2f> uvs, Texture texture, int mode) {

        FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertices.size() * 3);
        FloatBuffer textureData = BufferUtils.createFloatBuffer(uvs.size() * 2);

        for(Vector3f vertex : vertices) {

            vertexData.put(new float[] {vertex.x, vertex.y, vertex.z});

        }

        for(Vector2f uv : uvs) {

            textureData.put(new float[] {uv.x, uv.y});

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

        texture.bind();

        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0l);

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

        glUseProgram(0);

    }

    public static void renderObject3D(List<Vector3f> vertices, int mode) {

        FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertices.size() * 3);

        for(Vector3f vertex : vertices) {

            vertexData.put(new float[] {vertex.x, vertex.y, vertex.z});

        }

        vertexData.flip();

        int vboVertexHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0l);

        glEnableClientState(GL_VERTEX_ARRAY);

        glDrawArrays(mode, 0, vertices.size());

        glDisableClientState(GL_VERTEX_ARRAY);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glDeleteBuffers(vboVertexHandle);

    }

    public static void renderObject2D(List<Vector2f> vertices, List<Vector2f> uvs, Texture texture, int mode) {

        FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertices.size() * 2);
        FloatBuffer textureData = BufferUtils.createFloatBuffer(uvs.size() * 2);

        for(Vector2f vertex : vertices) {

            vertexData.put(new float[] {vertex.x, vertex.y});

        }

        for(Vector2f uv : uvs) {

            textureData.put(new float[] {uv.x, uv.y});

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

        texture.bind();

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

    }

    public static void renderObject2D(List<Vector2f> vertices, int mode) {

        FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertices.size() * 2);

        for(Vector2f vertex : vertices) {

            vertexData.put(new float[] {vertex.x, vertex.y});

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
