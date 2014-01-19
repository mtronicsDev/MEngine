package mEngine.interactive.gameObjects;

import mEngine.interactive.controls.KeyboardMouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Camera extends GameObject{

    private GameObject sticksTo;

    public Camera(Vector3f pos, Vector3f rot) {

        this(new GameObjectInvisible(pos, rot, new KeyboardMouse()));

    }

    public Camera(GameObject obj) {

        super(obj.position, obj.rotation);
        sticksTo = obj;

        initialize();

    }

    private void initialize() {

        //Sets perspective
        glMatrixMode(GL_PROJECTION);
        gluPerspective(45, (float)Display.getWidth() / Display.getHeight(), 0.1f, 1000);
        glViewport(0, 0, Display.getWidth(), Display.getHeight());

        glMatrixMode(GL_MODELVIEW);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

    }

    public void update() {

        position = sticksTo.position;
        rotation = sticksTo.rotation;

        glLoadIdentity();

        glRotatef(rotation.x, 1, 0, 0);
        glRotatef(rotation.y, 0, 1, 0);
        glRotatef(rotation.z, 0, 0, 1);

        glTranslatef(-position.x, -position.y, -position.z);

    }

}