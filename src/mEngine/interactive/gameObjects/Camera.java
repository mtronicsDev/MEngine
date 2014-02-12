package mEngine.interactive.gameObjects;

import mEngine.util.Input;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Camera extends GameObject{

    private GameObject sticksTo;

    public int zoom = 0;

    public Camera(GameObject obj) {

        super(obj.position, obj.rotation, obj.collidable);
        sticksTo = obj;

        initialize();

    }

    private void initialize() {

        //Sets perspective
        glMatrixMode(GL_PROJECTION);
        gluPerspective(45, (float)Display.getWidth() / Display.getHeight(), 0.1f, 1000);
        glViewport(0, 0, Display.getWidth(), Display.getHeight());

        glMatrixMode(GL_MODELVIEW);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    }

    public void update() {

        if(Input.isKeyPressed(Keyboard.KEY_F)) zoom ++;
        else if(Input.isKeyPressed(Keyboard.KEY_G)) zoom --;

        position = sticksTo.position;
        rotation = sticksTo.rotation;

        glLoadIdentity();

        glRotatef(rotation.x, 1, 0, 0);
        glRotatef(rotation.y, 0, 1, 0);
        glRotatef(rotation.z, 0, 0, 1);

        glTranslatef(-position.x, -position.y - zoom, -position.z);

    }

}