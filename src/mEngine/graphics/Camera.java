package mEngine.graphics;

import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Camera {

    Vector3f position;
    Vector3f rotation;

    public Camera(Vector3f pos, Vector3f rot) {

        position = pos;
        rotation = rot;

    }

    private void initialize() {

        //Sets perspective
        glMatrixMode(GL_PROJECTION);
            gluPerspective(45, GraphicsController.getAspectRatio(), 0.1f, 1000);
            glViewport(0, 0, GraphicsController.getWidth(), GraphicsController.getHeight());

        glMatrixMode(GL_MODELVIEW);

        glEnable(GL_DEPTH_TEST);

    }

}
