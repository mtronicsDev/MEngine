package mEngine.interactive.gameObjects;

import mEngine.graphics.GraphicsController;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Camera {

    Vector3f position;
    Vector3f rotation;

    public static GameObject cameraSticksTo;

    public Camera(Vector3f pos, Vector3f rot, GameObject obj) {

        position = pos;
        rotation = rot;
        cameraSticksTo = obj;

    }

    private void initialize() {

        //Sets perspective
        glMatrixMode(GL_PROJECTION);
            gluPerspective(45, GraphicsController.getAspectRatio(), 0.1f, 1000);
            glViewport(0, 0, GraphicsController.getWidth(), GraphicsController.getHeight());

        glMatrixMode(GL_MODELVIEW);

        glEnable(GL_DEPTH_TEST);

    }

    public void update() {

        if(cameraSticksTo == null) { //Moving the camera free from any game object



        } else { //Moving the camera bound to the movement of a game object



        }

    }

}
