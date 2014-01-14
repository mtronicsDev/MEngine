package mEngine.interactive.gameObjects;

import mEngine.graphics.GraphicsController;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Camera extends GameObject{

    private GameObject sticksTo;

    public Camera(Vector3f pos, Vector3f rot) {

        this(new GameObjectInvisible(pos, rot));

    }

    public Camera(GameObject obj) {

        super(obj.position, obj.rotation);
        sticksTo = obj;

        initialize();

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

        position = sticksTo.position;
        rotation = sticksTo.rotation;

    }

}
