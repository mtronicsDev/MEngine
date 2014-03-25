package mEngine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

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

    public static void renderObject3D(List<Vector3f> vertices, List<Vector3f> normals, List<Vector2f> uvs, int mode) {

        GraphicsController.switchTo3D();

        glBegin(mode);
        for (int i = 0; i < vertices.size(); i++) {

            glNormal3f(normals.get(i).x, normals.get(i).y, normals.get(i).z);
            glTexCoord2f(uvs.get(i).x, uvs.get(i).y);
            glVertex3f(vertices.get(i).x, vertices.get(i).y, vertices.get(i).z);

        }
        glEnd();

    }

    public static void renderObject2D(List<Vector2f> vertices, List<Vector2f> uvs, int mode) {

        GraphicsController.switchTo2D();

        glBegin(mode);
        for (int i = 0; i < vertices.size(); i++) {

            glTexCoord2f(uvs.get(i).x, uvs.get(i).y);
            glVertex2f(vertices.get(i).x, vertices.get(i).y);

        }
        glEnd();

    }

}
